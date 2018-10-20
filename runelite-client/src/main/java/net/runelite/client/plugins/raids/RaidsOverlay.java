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

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.SpriteID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.raids.solver.Room;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.util.Text;

public class RaidsOverlay extends Overlay
{
	private static final int OLM_PLANE = 0;
	private static final int BORDER_OFFSET = 2;
	//might need to edit these if they are not standard
	private static final int TITLE_COMPONENT_HEIGHT = 20;
	private static final int LINE_COMPONENT_HEIGHT = 16;
	private static final int ICON_SIZE = 32;

	private Client client;
	private RaidsPlugin plugin;
	private RaidsConfig config;
	private final PanelComponent panelComponent = new PanelComponent();
	private final ItemManager itemManager;
	private final SpriteManager spriteManager;

	@Getter
	private int width;

	@Getter
	private int height;

	@Setter
	private boolean scoutOverlayShown = false;

	@Inject
	private RaidsOverlay(Client client, RaidsPlugin plugin, RaidsConfig config, ItemManager itemManager, SpriteManager spriteManager)
	{
		setPosition(OverlayPosition.TOP_LEFT);
		setPriority(OverlayPriority.LOW);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		this.itemManager = itemManager;
		this.spriteManager = spriteManager;
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

		Set<Integer> itemIds = new HashSet<>();

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

					String bossName = room.getBoss().getName();
					if (config.showRecommendedItems())
					{
						switch (RaidRoom.Boss.fromString(bossName))
						{
							case GUARDIANS:
								itemIds.add(11920);
								break;
							case MUTTADILES:
								itemIds.add(-1);
								itemIds.add(11808);
								break;
							case MYSTICS:
								itemIds.add(12018);
								break;
							case SHAMANS:
								itemIds.add(10925);
								break;
							case VANGUARDS:
								itemIds.add(-1);
								break;
							case VASA:
								itemIds.add(13265);
								break;
							case VESPULA:
								itemIds.add(2434);
								itemIds.add(5952);
								break;
							case UNKNOWN:
								break;
							default:
								break;
						}
					}
					panelComponent.getChildren().add(LineComponent.builder()
						.left(room.getType().getName())
						.right(bossName)
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
					if (config.addRaidQualityBorder() || config.showRecommendedItems())
					{
						switch (RaidRoom.Puzzle.fromString(roomName))
						{
							case CRABS:
								crabs = true;
								break;
							case THIEVING:
								itemIds.add(1523);
								break;
							case TIGHTROPE:
								tightrope = true;
								break;
							default:
								break;
						}
					}

					panelComponent.getChildren().add(LineComponent.builder()
						.left(room.getType().getName())
						.right(roomName)
						.rightColor(color)
						.build());
					break;
				case FARMING:
					if (config.showScavsFarms())
					{
						panelComponent.getChildren().add(LineComponent.builder()
							.left("")
							.right(room.getType().getName())
							.rightColor(new Color(181, 230, 29)) //yellow green
							.build());
					}
					break;
				case SCAVENGERS:
					if (config.showScavsFarms())
					{
						panelComponent.getChildren().add(LineComponent.builder()
							.left("")
							.right("Scavs")
							.rightColor(new Color(181, 230, 29)) //yellow green
							.build());
					}
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
		if (config.showRecommendedItems() && itemIds.size() > 0)
		{
			Integer[] itemIdsArray = itemIds.toArray(new Integer[0]);
			Rectangle rectangle = new Rectangle(new Dimension(width, height));
			int xOffset = rectangle.x + BORDER_OFFSET;
			int yOffset = rectangle.y + BORDER_OFFSET + TITLE_COMPONENT_HEIGHT + (config.insertCCAndWorld() ? LINE_COMPONENT_HEIGHT : 0);
			int rectWidth = (rectangle.width - BORDER_OFFSET) / 2;
			int rectHeight = rectangle.height - 2 * BORDER_OFFSET - TITLE_COMPONENT_HEIGHT -
				(config.insertCCAndWorld() ? LINE_COMPONENT_HEIGHT : 0);
			final Rectangle itemArea = new Rectangle();
			itemArea.setSize(rectWidth, rectHeight);
			graphics.setColor(new Color(43, 37, 31)); //standard BG color

			itemArea.setLocation(xOffset, yOffset);
			graphics.fill(itemArea);

			if ((rectHeight * 2 / ICON_SIZE) >= itemIdsArray.length )
			{
				for (int i = 0; i < itemIdsArray.length; i++)
				{
					int xExtra = (i % 2) * ICON_SIZE;
					int yExtra = (i / 2) * ICON_SIZE;
					if (itemIdsArray[i] > 0)
						graphics.drawImage(itemManager.getImage(itemIdsArray[i]), null, xOffset + xExtra, yOffset + yExtra);
					else
						graphics.drawImage(spriteManager.getSprite(SpriteID.SPELL_ICE_BARRAGE, 0), null, xOffset + xExtra + 6, yOffset + yExtra + 6);
				}
			}
			else
			{
				for (int i = 0; i < itemIdsArray.length; i++)
				{
					float resize = (float) (ICON_SIZE * 2) / 3;
					float xExtra = (i % 3) * resize;
					int temp = i / 3;
					float yExtra = temp * resize;
					if (itemIdsArray[i] > 0)
						graphics.drawImage(resize(itemManager.getImage(itemIdsArray[i]), (int) resize, (int) resize), null, xOffset + (int) xExtra, yOffset + (int) yExtra);
					else
						graphics.drawImage(spriteManager.getSprite(SpriteID.SPELL_ICE_BARRAGE, 0), null, xOffset + (int) xExtra, yOffset + (int) yExtra + 1);
				}
			}
		}

		return panelDims;
	}

	private BufferedImage resize(BufferedImage source, int width, int height)
	{
		return commonResize(source, width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //VALUE_INTERPOLATION_BILINEAR
	}

	private static BufferedImage commonResize(BufferedImage source, int width, int height, Object hint)
	{
		BufferedImage img = new BufferedImage(width, height, source.getType());
		Graphics2D g = img.createGraphics();
		try
		{
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g.drawImage(source, 0, 0, width, height, null);
		}
		finally
		{
			g.dispose();
		}
		return img;
	}
}
