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

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.SpriteID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.raids.solver.Room;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

public class RaidsOverlay extends Overlay
{
	private static final int OLM_PLANE = 0;
	private static final int BORDER_OFFSET = 2;
	private static final int ICON_SIZE = 32;
	private static final int SMALL_ICON_SIZE = 21;

	//might need to edit these if they are not standard
	private static final int TITLE_COMPONENT_HEIGHT = 20;
	private static final int LINE_COMPONENT_HEIGHT = 16;

	private Client client;
	private RaidsPlugin plugin;
	private RaidsConfig config;
	private final PanelComponent panelComponent = new PanelComponent();
	private final ItemManager itemManager;
	private final SpriteManager spriteManager;
	private final PanelComponent panelImages = new PanelComponent();

	@Setter
	private boolean scoutOverlayShown = false;

	@Getter
	private int width;

	@Getter
	private int height;

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
		String layout = plugin.getRaid().getLayout().toCode().replaceAll("#", "").replaceAll("¤", "");

		if (config.enableLayoutWhitelist() && !plugin.getLayoutWhitelist().contains(layout.toLowerCase()))
		{
			color = Color.RED;
		}

		int combatCount = 0;
		boolean crabs = false;
		boolean iceDemon = false;
		boolean tightrope = false;
		String puzzles = "";
		if (config.enableSharableImage())
		{
			for (Room layoutRoom : plugin.getRaid().getLayout().getRooms())
			{
				int position = layoutRoom.getPosition();
				RaidRoom room = plugin.getRaid().getRoom(position);

				if (room == null)
				{
					continue;
				}

				switch (room.getType())
				{
					case COMBAT:
						combatCount++;
						break;
					case PUZZLE:
						String roomName = room.getPuzzle().getName();
						switch (RaidRoom.Puzzle.fromString(roomName))
						{
							case CRABS:
								crabs = true;
								break;
							case ICE_DEMON:
								iceDemon = true;
								break;
							case TIGHTROPE:
								tightrope = true;
								break;
						}
						break;
				}
			}
			if (tightrope)
				puzzles = crabs ? "cr" : iceDemon ? "ri" : "";
			layout = (config.enableSharableImage() ? "" + combatCount + "c " + puzzles + " " : "") + layout;
		}
		panelComponent.getChildren().add(TitleComponent.builder()
			.text(layout)
			.color(color)
			.build());
		color = Color.ORANGE;
		if (config.enableSharableImage())
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

		Set<Integer> imageIds = new HashSet<>();

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
								imageIds.add(ItemID.DRAGON_PICKAXE);
								break;
							case MUTTADILES:
								imageIds.add(SpriteID.SPELL_ICE_BARRAGE);
								imageIds.add(ItemID.ZAMORAK_GODSWORD);
								break;
							case MYSTICS:
								imageIds.add(ItemID.SALVE_AMULETEI);
								break;
							case SHAMANS:
								imageIds.add(ItemID.SANFEW_SERUM4);
								break;
							case VANGUARDS:
								imageIds.add(SpriteID.SPELL_ICE_BARRAGE);
								break;
							case VASA:
								imageIds.add(ItemID.ABYSSAL_DAGGER);
								break;
							case VESPULA:
								imageIds.add(ItemID.PRAYER_POTION4);
								imageIds.add(ItemID.SANFEW_SERUM4);
								break;
						}
					}
					panelComponent.getChildren().add(LineComponent.builder()
						.left(config.showRecommendedItems() ? "" : room.getType().getName())
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
					if (config.showRecommendedItems() && RaidRoom.Puzzle.fromString(roomName).equals(RaidRoom.Puzzle.THIEVING))
						imageIds.add(ItemID.LOCKPICK);

					panelComponent.getChildren().add(LineComponent.builder()
						.left(config.showRecommendedItems() ? "" : room.getType().getName())
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

		//add recommended items
		if (config.showRecommendedItems() && imageIds.size() > 0)
		{
			panelImages.getChildren().clear();

			Integer[] idArray = imageIds.toArray(new Integer[0]);
			int imagesVerticalOffset = TITLE_COMPONENT_HEIGHT + LINE_COMPONENT_HEIGHT - BORDER_OFFSET;
			int imagesMaxHeight = height - 2 * BORDER_OFFSET - TITLE_COMPONENT_HEIGHT - LINE_COMPONENT_HEIGHT;
			boolean smallImages = false;

			if (2 * (imagesMaxHeight / ICON_SIZE) >= idArray.length )
			{
				panelImages.setWrapping(2);
			}
			else
			{
				panelImages.setWrapping(3);
				smallImages = true;
			}

			panelImages.setPreferredLocation(new Point(0, imagesVerticalOffset));
			panelImages.setBackgroundColor(null);
			panelImages.setOrientation(PanelComponent.Orientation.HORIZONTAL);

			for (Integer e : idArray)
			{
				final BufferedImage image = getImage(e, smallImages);

				if (image != null)
				{
					panelImages.getChildren().add(new ImageComponent(image));
				}
			}

			panelImages.render(graphics);
		}
		return panelDims;
	}

	private BufferedImage getImage(int id, boolean small)
	{
		BufferedImage bim;
		if (id != SpriteID.SPELL_ICE_BARRAGE)
			bim = itemManager.getImage(id);
		else
			bim = spriteManager.getSprite(id, 0);

		if (bim == null)
			return null;
		if (!small)
			return ImageUtil.resizeCanvas(bim, ICON_SIZE, ICON_SIZE);
		if (id != SpriteID.SPELL_ICE_BARRAGE)
			return ImageUtil.resizeImage(bim, SMALL_ICON_SIZE, SMALL_ICON_SIZE);
		return ImageUtil.resizeCanvas(bim, SMALL_ICON_SIZE, SMALL_ICON_SIZE);
	}

}
