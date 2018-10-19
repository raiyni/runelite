/*
 * Copyright (c) 2018, Kamiel
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
package net.runelite.client.plugins.raids;

import com.sun.istack.internal.NotNull;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.raids.solver.Room;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.util.Text;
import lombok.extern.slf4j.Slf4j;
//TODO remove logger

@Slf4j
public class RaidsOverlay extends Overlay
{
	private static final int OLM_PLANE = 0;
	private static final int BORDER_OFFSET = 2;

	private Client client;
	private RaidsPlugin plugin;
	private RaidsConfig config;
	private final PanelComponent panelComponent = new PanelComponent();
	private int width;
	private int height;

	@Setter
	private boolean scoutOverlayShown = false;

	@Inject
	private RaidsOverlay(Client client, RaidsPlugin plugin, RaidsConfig config)
	{
		setPosition(OverlayPosition.TOP_LEFT);
		setPriority(OverlayPriority.LOW);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.scoutOverlay() || !scoutOverlayShown || plugin.isInRaidChambers() && client.getPlane() == OLM_PLANE)
		{
			return null;
		}

		panelComponent.getChildren().clear();

		if (plugin.getRaid() == null || plugin.getRaid().getLayout() == null)
		{
			panelComponent.getChildren().add(TitleComponent.builder()
				.text("Unable to scout this raid!")
				.color(Color.RED)
				.build());

			return panelComponent.render(graphics);
		}

		Color color = Color.WHITE;
		String layout = plugin.getRaid().getLayout().toCode().replaceAll("¤", "");
		if (config.keepLayoutFloorBreak())
		{
			layout = layout.substring(1);
		}
		else
		{
			layout = layout.replaceAll("#", "");
		}

		if (config.enableLayoutWhitelist() && !plugin.getLayoutWhitelist().contains(layout.toLowerCase().replaceAll("#", "")))
		{
			color = Color.RED;
		}

		panelComponent.getChildren().add(TitleComponent.builder()
			.text(layout)
			.color(color)
			.build());
		color = Color.ORANGE;
		if (config.insertCCAndWorld())
		{
			String clanOwner = Text.removeTags(client.getWidget(WidgetInfo.CLAN_CHAT_OWNER).getText());
			if (clanOwner.equals("None"))
			{
				clanOwner = "Open CC tab...";
				color = Color.RED;
			}
			panelComponent.getChildren().add(LineComponent.builder()
				.left("W" + client.getWorld())
				.right("" + clanOwner)
				.leftColor(Color.ORANGE)
				.rightColor(color)
				.build());
		}

		int bossMatches = 0;
		int bossCount = 0;

		if (config.enableRotationWhitelist())
		{
			bossMatches = plugin.getRotationMatches();
		}

		boolean crabs = false;
		boolean tightrope = false;
		for (Room layoutRoom : plugin.getRaid().getLayout().getRooms())
		{
			int position = layoutRoom.getPosition();
			RaidRoom room = plugin.getRaid().getRoom(position);

			if (room == null)
			{
				continue;
			}

			color = Color.WHITE;

			switch (room.getType())
			{
				case COMBAT:
					bossCount++;
					if (plugin.getRoomWhitelist().contains(room.getBoss().getName().toLowerCase()))
					{
						color = Color.GREEN;
					}
					else if (plugin.getRoomBlacklist().contains(room.getBoss().getName().toLowerCase())
							|| config.enableRotationWhitelist() && bossCount > bossMatches)
					{
						color = Color.RED;
					}

					panelComponent.getChildren().add(LineComponent.builder()
						.left(room.getType().getName())
						.right(room.getBoss().getName())
						.rightColor(color)
						.build());

					break;

				case PUZZLE:
					if (plugin.getRoomWhitelist().contains(room.getPuzzle().getName().toLowerCase()))
					{
						color = Color.GREEN;
					}
					else if (plugin.getRoomBlacklist().contains(room.getPuzzle().getName().toLowerCase()))
					{
						color = Color.RED;
					}

					String roomName = room.getPuzzle().getName();
					if (config.addRaidQualityBorder())
					{
						if (roomName.equalsIgnoreCase(RaidRoom.Puzzle.CRABS.toString()))
							crabs = true;
						if (roomName.equalsIgnoreCase(RaidRoom.Puzzle.TIGHTROPE.toString()))
							tightrope = true;
					}

					panelComponent.getChildren().add(LineComponent.builder()
						.left(room.getType().getName())
						.right(roomName)
						.rightColor(color)
						.build());
					break;
			}
		}

		Dimension panelDims = panelComponent.render(graphics);
		width = (int) panelDims.getWidth();
		height = (int) panelDims.getHeight();
		//add colored border
		if (config.addRaidQualityBorder())
		{
			if (tightrope)
			{
				if (crabs)
					color = Color.GREEN;
				else
					color = new Color(181, 230, 29); //yellow green
			}
			else
			{
				color = Color.RED;
			}
			Rectangle rectangle = new Rectangle(new Dimension(width, height));
			final Rectangle insideStroke = new Rectangle();
			insideStroke.setLocation(rectangle.x + BORDER_OFFSET / 2, rectangle.y + BORDER_OFFSET / 2);
			insideStroke.setSize(rectangle.width - BORDER_OFFSET - BORDER_OFFSET / 2,
				rectangle.height - BORDER_OFFSET - BORDER_OFFSET / 2);
			graphics.setColor(color);
			graphics.draw(insideStroke);
		}

		//add recommended items
		//TODO
		if (config.showRecommendedItems())
		{

		}

		return panelDims;
	}

	//this needs to be protected, NOT private, so that the RaidsPlugin can access it
	protected void initiateCopyImage()
	{
		if (!config.copyToClipboard())
			return;
		BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bim.createGraphics();
		render(g);
		CopyImageToClipBoard ci = new CopyImageToClipBoard();
		ci.copyImage(bim);
		g.dispose();
	}

	public class CopyImageToClipBoard implements ClipboardOwner
	{
		private void copyImage(BufferedImage bi)
		{
			TransferableImage trans = new TransferableImage(bi);
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			try
			{
				c.setContents(trans, this);
			}
			catch (IllegalStateException e)
			{
				//some systems are unable to modify the clipboard if it is already in use
				//log.info("Caught exception where clipboard is in use.");
			}
		}

		public void lostOwnership( Clipboard clip, Transferable trans )
		{
			//Must implement this method
			//log.info("Lost ownership of clipboard.");
		}

		public class TransferableImage implements Transferable
		{

			Image i;

			private TransferableImage(Image i)
			{
				this.i = i;
			}

			@Override
			public Object getTransferData(@NotNull DataFlavor flavor)
				throws UnsupportedFlavorException
			{
				if (flavor.equals(DataFlavor.imageFlavor) && i != null)
				{
					return i;
				}
				else
				{
					throw new UnsupportedFlavorException(flavor);
				}
			}

			public DataFlavor[] getTransferDataFlavors()
			{
				DataFlavor[] flavors = new DataFlavor[1];
				flavors[0] = DataFlavor.imageFlavor;
				return flavors;
			}

			public boolean isDataFlavorSupported(DataFlavor flavor)
			{
				DataFlavor[] flavors = getTransferDataFlavors();
				for (DataFlavor i : flavors)
				{
					if (flavor.equals(i))
					{
						return true;
					}
				}
				return false;
			}
		}
	}
}
