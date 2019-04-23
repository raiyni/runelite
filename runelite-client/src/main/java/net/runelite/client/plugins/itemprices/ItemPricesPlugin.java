/*
 * Copyright (c) 2018 Charlie Waters
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
package net.runelite.client.plugins.itemprices;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.StackFormatter;

@PluginDescriptor(
	name = "Item Prices",
	description = "Show prices on hover for items in your inventory and bank",
	tags = {"bank", "inventory", "overlay", "high", "alchemy", "grand", "exchange", "tooltips"},
	enabledByDefault = false
)
public class ItemPricesPlugin extends Plugin
{
	private final float HIGH_ALCH_CONSTANT = 0.6f;
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ItemPricesOverlay overlay;

	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

	@Provides
	ItemPricesConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ItemPricesConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onBeforeRender(final BeforeRender event)
	{
		Widget parent = client.getWidget(WidgetInfo.GUIDE_PRICES_ITEMS_CONTAINER);
		if (parent != null && !parent.isHidden() && parent.getDynamicChildren().length > 0)
		{
			long gePrice = 0;
			long alchPrice = 0;

			for (int i = 0; i < (parent.getDynamicChildren().length - 28); i++)
			{
				Widget widget = parent.getDynamicChildren()[i];

				long localGePrice = (long) itemManager.getItemPrice(widget.getItemId()) * (long) widget.getItemQuantity();

				ItemComposition composition = itemManager.getItemComposition(widget.getItemId());
				long localAlchPrice = (long) (HIGH_ALCH_CONSTANT * ((long) composition.getPrice() * widget.getItemQuantity()));

				Widget priceWidget = parent.getDynamicChildren()[i + 28];
				priceWidget.setText("GE: " + displayPrice(localGePrice) + "<br>HA: " + displayPrice(localAlchPrice));

				gePrice += localGePrice;
				alchPrice += localAlchPrice;
			}

			Widget totalPrice = client.getWidget(464, 12);
			totalPrice.setText("Total GE price: <col=ffffff>" + displayPrice(gePrice) + "</col><br>" +
				"Total HA price: <col=ffffff>" + displayPrice(alchPrice) + "</col>");
		}
	}

	private String displayPrice(long num)
	{
		return StackFormatter.formatNumber(num);
	}
}
