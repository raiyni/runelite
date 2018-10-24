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

import lombok.Getter;
import lombok.Setter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
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

public class RaidsOverlay extends Overlay
{
	private static final int OLM_PLANE = 0;

	private Client client;
	private RaidsPlugin plugin;
	private RaidsConfig config;
	private final PanelComponent panelComponent = new PanelComponent();

	@Setter
	private boolean scoutOverlayShown = false;

	@Getter
	private int width;

	@Getter
	private int height;

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
		return render(graphics, false);
	}

	public Dimension render(Graphics2D graphics, boolean sharable)
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
		if (config.enhanceScouterTitle() || sharable)
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
			layout = (config.enhanceScouterTitle() ? "" + combatCount + "c " + puzzles + " " : "") + layout;
		}
		panelComponent.getChildren().add(TitleComponent.builder()
			.text(layout)
			.color(color)
			.build());
		color = Color.ORANGE;
		if (sharable)
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

					panelComponent.getChildren().add(LineComponent.builder()
						.left(room.getType().getName())
						.right(room.getPuzzle().getName())
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
		return panelDims;
	}

}
