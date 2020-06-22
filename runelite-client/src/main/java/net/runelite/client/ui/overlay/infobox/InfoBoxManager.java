/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.ui.overlay.infobox;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.InfoBoxMenuClicked;
import net.runelite.client.events.SessionClose;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.AsyncBufferedImage;

@Singleton
@Slf4j
public class InfoBoxManager
{
	private static final String OVERLAY_KEY = "overlay";
	private static final String LAYER_PREFIX = "layer_";
	private static final String DEFAULT_LAYER = "Default";

	private final Multimap<String, InfoBox> infoBoxes = TreeMultimap.create(Comparator.naturalOrder(), (a, b) ->
	{
		int priority = a.getPriority().compareTo(b.getPriority());
		if (priority != 0)
		{
			return priority * -1;
		}

		System.out.println(priority);

		int i = a.getName().compareTo(b.getName());
		return i != 0 ? i : 1;
	});

	private final Map<String, InfoBoxOverlay> layers = new ConcurrentHashMap<String, InfoBoxOverlay>()
	{
		@Override
		public InfoBoxOverlay computeIfAbsent(String key, @Nonnull Function<? super String, ? extends InfoBoxOverlay> fn)
		{
			if (this.containsKey(key))
			{
				return this.get(key);
			}

			InfoBoxOverlay value = super.computeIfAbsent(key, fn);
			eventBus.register(value);
			overlayManager.add(value);
			return value;
		}

		@Override
		public InfoBoxOverlay remove(Object key)
		{
			InfoBoxOverlay value = super.remove(key);
			if (value != null)
			{
				eventBus.unregister(value);
				overlayManager.remove(value);
			}

			return value;
		}
	};

	private final RuneLiteConfig runeLiteConfig;
	private final TooltipManager tooltipManager;
	private final Client client;
	private final EventBus eventBus;
	private final OverlayManager overlayManager;
	private final ConfigManager configManager;

	@Inject
	private InfoBoxManager(
		final RuneLiteConfig runeLiteConfig,
		final TooltipManager tooltipManager,
		final Client client,
		final EventBus eventBus,
		final OverlayManager overlayManager,
		final ConfigManager configManager)
	{
		this.tooltipManager = tooltipManager;
		this.client = client;
		this.eventBus = eventBus;
		this.runeLiteConfig = runeLiteConfig;
		this.overlayManager = overlayManager;
		this.configManager = configManager;
	}

	@Subscribe
	public void onSessionOpen(SessionOpen event)
	{
		refreshLayers();
	}

	@Subscribe
	public void onSessionClose(SessionClose event)
	{
		refreshLayers();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("runelite") && event.getKey().equals("infoBoxSize"))
		{
			infoBoxes.values().forEach(this::updateInfoBoxImage);
		}
	}

	@Subscribe
	public void onInfoBoxMenuClicked(InfoBoxMenuClicked event)
	{
		if (!"Split".equals(event.getEntry().getOption()))
		{
			return;
		}

		changeLayer(event.getInfoBox().getName() + "_" + System.nanoTime(), event.getInfoBox());
		refreshLayers();
	}

	public void addInfoBox(InfoBox infoBox)
	{
		Preconditions.checkNotNull(infoBox);
		log.debug("Adding InfoBox {}", infoBox);

		updateInfoBoxImage(infoBox);

		synchronized (this)
		{
			String layerName = getLayerName(infoBox);
			infoBoxes.put(layerName, infoBox);
			infoBox.getMenuEntries().add(new OverlayMenuEntry(MenuAction.RUNELITE_INFOBOX, "Split", ""));
			layers.computeIfAbsent(layerName, this::makeOverlay);
		}

		BufferedImage image = infoBox.getImage();

		if (image instanceof AsyncBufferedImage)
		{
			AsyncBufferedImage abi = (AsyncBufferedImage) image;
			abi.onLoaded(() -> updateInfoBoxImage(infoBox));
		}
	}

	public synchronized void removeInfoBox(InfoBox infoBox)
	{
		if (infoBox != null && infoBoxes.remove(getLayerName(infoBox), infoBox))
		{
			log.debug("Removed InfoBox {}", infoBox);
		}
	}

	public synchronized void removeIf(Predicate<InfoBox> filter)
	{
		if (infoBoxes.values().removeIf(filter))
		{
			log.debug("Removed InfoBoxes for filter {}", filter);
		}
	}

	public List<InfoBox> getInfoBoxes()
	{
		return ImmutableList.copyOf(infoBoxes.values());
	}

	public synchronized void cull()
	{
		infoBoxes.values().removeIf(InfoBox::cull);
	}

	public void updateInfoBoxImage(final InfoBox infoBox)
	{
		if (infoBox.getImage() == null)
		{
			return;
		}

		// Set scaled InfoBox image
		final BufferedImage image = infoBox.getImage();
		BufferedImage resultImage = image;
		final double width = image.getWidth(null);
		final double height = image.getHeight(null);
		final double size = Math.max(2, runeLiteConfig.infoBoxSize()); // Limit size to 2 as that is minimum size not causing breakage

		if (size < width || size < height)
		{
			final double scalex = size / width;
			final double scaley = size / height;

			if (scalex == 1 && scaley == 1)
			{
				return;
			}

			final double scale = Math.min(scalex, scaley);
			final int newWidth = (int) (width * scale);
			final int newHeight = (int) (height * scale);
			final BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			final Graphics g = scaledImage.createGraphics();
			g.drawImage(image, 0, 0, newWidth, newHeight, null);
			g.dispose();
			resultImage = scaledImage;
		}

		infoBox.setScaledImage(resultImage);
	}

	Collection<InfoBox> getInfoBoxes(String name)
	{
		return infoBoxes.get(name);
	}

	private InfoBoxOverlay makeOverlay(String name)
	{
		return new InfoBoxOverlay(
			this,
			tooltipManager,
			client,
			runeLiteConfig,
			eventBus,
			name);
	}

	private synchronized void changeLayer(String name, InfoBox infoBox)
	{
		Multimap<String, InfoBox> view = Multimaps.filterValues(infoBoxes, i -> i.getName().equals(infoBox.getName()));
		String oldName = view.keySet().stream().findFirst().get();
		Collection<InfoBox> boxes = view.removeAll(oldName);
		infoBoxes.putAll(name, boxes);

		setLayer(name, infoBox);
	}

	private synchronized void refreshLayers()
	{
		Set<String> layerNames = getLayerNames();
		Set<String> keys = layers.keySet();

		for (String key : keys)
		{
			if (!layerNames.contains(key))
			{
				layers.remove(key);
			}
		}

		for (String name : layerNames)
		{
			layers.computeIfAbsent(name, this::makeOverlay);
		}
	}

	public void mergeInfoBoxes(InfoBoxOverlay dragging, InfoBoxOverlay intersecting)
	{
		log.debug("Merging InfoBoxes from {} into {}", dragging.getName(), intersecting.getName());

		String destination = intersecting.getName();
		Collection<InfoBox> infoBoxesToMove = infoBoxes.removeAll(dragging.getName());

		infoBoxes.putAll(destination, infoBoxesToMove);
		for (InfoBox infoBox: infoBoxesToMove)
		{
			setLayer(destination, infoBox);
		}

		refreshLayers();
	}

	Set<String> getLayerNames()
	{
		Set<String> set = Sets.newHashSet(DEFAULT_LAYER);
		List<String> keys = configManager.getConfigurationKeys(OVERLAY_KEY + "." + LAYER_PREFIX);

		for (String key: keys)
		{
			set.add(configManager.getConfiguration(OVERLAY_KEY, key.substring(OVERLAY_KEY.length() + 1)));
		}
		return set;
	}

	String getLayerName(InfoBox infoBox)
	{
		String name = configManager.getConfiguration(OVERLAY_KEY, LAYER_PREFIX + infoBox.getName());
		if (Strings.isNullOrEmpty(name)) {
			return "RuneLite";
		}

		return name;
	}

	void setLayer(String layer, InfoBox infoBox)
	{
		configManager.setConfiguration(OVERLAY_KEY, LAYER_PREFIX + infoBox.getName(), layer);
	}
}
