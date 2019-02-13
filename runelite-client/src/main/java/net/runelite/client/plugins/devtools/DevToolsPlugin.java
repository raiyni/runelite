/*
 * Copyright (c) 2017, Kronos <https://github.com/KronosDesign>
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
package net.runelite.client.plugins.devtools;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.common.collect.ImmutableList;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import static java.lang.Math.min;
import java.util.List;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.events.ExperienceChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.kit.KitType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.components.ActiveButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;
import org.slf4j.LoggerFactory;

@PluginDescriptor(
	name = "Developer Tools",
	tags = {"panel"},
	developerPlugin = true
)
@Getter
public class DevToolsPlugin extends Plugin
{
	private static final List<MenuAction> EXAMINE_MENU_ACTIONS = ImmutableList.of(MenuAction.EXAMINE_ITEM,
			MenuAction.EXAMINE_ITEM_GROUND, MenuAction.EXAMINE_NPC, MenuAction.EXAMINE_OBJECT);

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private DevToolsOverlay overlay;

	@Inject
	private LocationOverlay locationOverlay;

	@Inject
	private SceneOverlay sceneOverlay;

	@Inject
	private CameraOverlay cameraOverlay;

	@Inject
	private WorldMapLocationOverlay worldMapLocationOverlay;

	@Inject
	private WorldMapRegionOverlay mapRegionOverlay;

	@Inject
	private EventBus eventBus;

	private ActiveButton players;
	private ActiveButton npcs;
	private ActiveButton groundItems;
	private ActiveButton groundObjects;
	private ActiveButton gameObjects;
	private ActiveButton graphicsObjects;
	private ActiveButton walls;
	private ActiveButton decorations;
	private ActiveButton inventory;
	private ActiveButton projectiles;
	private ActiveButton location;
	private ActiveButton chunkBorders;
	private ActiveButton mapSquares;
	private ActiveButton validMovement;
	private ActiveButton lineOfSight;
	private ActiveButton cameraPosition;
	private ActiveButton worldMapLocation;
	private ActiveButton tileLocation;
	private ActiveButton interacting;
	private ActiveButton examine;
	private ActiveButton detachedCamera;
	private ActiveButton widgetInspector;
	private ActiveButton varInspector;
	private NavigationButton navButton;

	@Provides
	DevToolsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DevToolsConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		players = new ActiveButton("Players");
		npcs = new ActiveButton("NPCs");

		groundItems = new ActiveButton("Ground Items");
		groundObjects = new ActiveButton("Ground Objects");
		gameObjects = new ActiveButton("Game Objects");
		graphicsObjects = new ActiveButton("Graphics Objects");
		walls = new ActiveButton("Walls");
		decorations = new ActiveButton("Decorations");

		inventory = new ActiveButton("Inventory");
		projectiles = new ActiveButton("Projectiles");

		location = new ActiveButton("Location");
		worldMapLocation = new ActiveButton("World Map Location");
		tileLocation = new ActiveButton("Tile Location");
		cameraPosition = new ActiveButton("Camera Position");

		chunkBorders = new ActiveButton("Chunk Borders");
		mapSquares = new ActiveButton("Map Squares");

		lineOfSight = new ActiveButton("Line Of Sight");
		validMovement = new ActiveButton("Valid Movement");
		interacting = new ActiveButton("Interacting");
		examine = new ActiveButton("Examine");

		detachedCamera = new ActiveButton("Detached Camera");
		widgetInspector = new ActiveButton("Widget Inspector");
		varInspector = new ActiveButton("Var Inspector");

		overlayManager.add(overlay);
		overlayManager.add(locationOverlay);
		overlayManager.add(sceneOverlay);
		overlayManager.add(cameraOverlay);
		overlayManager.add(worldMapLocationOverlay);
		overlayManager.add(mapRegionOverlay);

		final DevToolsPanel panel = injector.getInstance(DevToolsPanel.class);

		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "devtools_icon.png");

		navButton = NavigationButton.builder()
			.tooltip("Developer Tools")
			.icon(icon)
			.priority(1)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		overlayManager.remove(locationOverlay);
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(cameraOverlay);
		overlayManager.remove(worldMapLocationOverlay);
		overlayManager.remove(mapRegionOverlay);
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	public void onCommandExecuted(CommandExecuted commandExecuted)
	{
		String[] args = commandExecuted.getArguments();

		switch (commandExecuted.getCommand())
		{
			case "logger":
			{
				final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
				String message;
				Level currentLoggerLevel = logger.getLevel();

				if (args.length < 1)
				{
					message = "Logger level is currently set to " + currentLoggerLevel;
				}
				else
				{
					Level newLoggerLevel = Level.toLevel(args[0], currentLoggerLevel);
					logger.setLevel(newLoggerLevel);
					message = "Logger level has been set to " + newLoggerLevel;
				}

				client.addChatMessage(ChatMessageType.SERVER, "", message, null);
				break;
			}
			case "getvarp":
			{
				int varp = Integer.parseInt(args[0]);
				int value = client.getVarpValue(client.getVarps(), varp);
				client.addChatMessage(ChatMessageType.SERVER, "", "VarPlayer " + varp + ": " + value, null);
				break;
			}
			case "setvarp":
			{
				int varp = Integer.parseInt(args[0]);
				int value = Integer.parseInt(args[1]);
				client.setVarpValue(client.getVarps(), varp, value);
				client.addChatMessage(ChatMessageType.SERVER, "", "Set VarPlayer " + varp + " to " + value, null);
				eventBus.post(new VarbitChanged()); // fake event
				break;
			}
			case "getvarb":
			{
				int varbit = Integer.parseInt(args[0]);
				int value = client.getVarbitValue(client.getVarps(), varbit);
				client.addChatMessage(ChatMessageType.SERVER, "", "Varbit " + varbit + ": " + value, null);
				break;
			}
			case "setvarb":
			{
				int varbit = Integer.parseInt(args[0]);
				int value = Integer.parseInt(args[1]);
				client.setVarbitValue(client.getVarps(), varbit, value);
				client.addChatMessage(ChatMessageType.SERVER, "", "Set varbit " + varbit + " to " + value, null);
				eventBus.post(new VarbitChanged()); // fake event
				break;
			}
			case "addxp":
			{
				Skill skill = Skill.valueOf(args[0].toUpperCase());
				int xp = Integer.parseInt(args[1]);

				int totalXp = client.getSkillExperience(skill) + xp;
				int level = min(Experience.getLevelForXp(totalXp), 99);

				client.getBoostedSkillLevels()[skill.ordinal()] = level;
				client.getRealSkillLevels()[skill.ordinal()] = level;
				client.getSkillExperiences()[skill.ordinal()] = totalXp;

				client.queueChangedSkill(skill);

				ExperienceChanged experienceChanged = new ExperienceChanged();
				experienceChanged.setSkill(skill);
				eventBus.post(experienceChanged);
				break;
			}
			case "anim":
			{
				int id = Integer.parseInt(args[0]);
				Player localPlayer = client.getLocalPlayer();
				localPlayer.setAnimation(id);
				localPlayer.setActionFrame(0);
				break;
			}
			case "gfx":
			{
				int id = Integer.parseInt(args[0]);
				Player localPlayer = client.getLocalPlayer();
				localPlayer.setGraphic(id);
				localPlayer.setSpotAnimFrame(0);
				break;
			}
			case "transform":
			{
				int id = Integer.parseInt(args[0]);
				Player player = client.getLocalPlayer();
				player.getPlayerComposition().setTransformedNpcId(id);
				player.setIdlePoseAnimation(-1);
				player.setPoseAnimation(-1);
				break;
			}
			case "cape":
			{
				int id = Integer.parseInt(args[0]);
				Player player = client.getLocalPlayer();
				player.getPlayerComposition().getEquipmentIds()[KitType.CAPE.getIndex()] = id + 512;
				player.getPlayerComposition().setHash();
				break;
			}
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if (!examine.isSelected())
		{
			return;
		}

		MenuAction action = MenuAction.of(event.getType());

		if (EXAMINE_MENU_ACTIONS.contains(action))
		{
			MenuEntry[] entries = client.getMenuEntries();
			MenuEntry entry = entries[entries.length - 1];

			final int identifier = event.getIdentifier();
			String info = "ID: ";

			if (action == MenuAction.EXAMINE_NPC)
			{
				NPC npc = client.getCachedNPCs()[identifier];
				info += npc.getId();
			}
			else
			{
				info += identifier;

				if (action == MenuAction.EXAMINE_OBJECT)
				{
					WorldPoint point = WorldPoint.fromScene(client, entry.getParam0(), entry.getParam1(), client.getPlane());
					info += " X: " + point.getX() + " Y: " + point.getY();
				}
			}

			entry.setTarget(entry.getTarget() + " " + ColorUtil.prependColorTag("(" + info + ")", JagexColors.MENU_TARGET));
			client.setMenuEntries(entries);
		}
	}
}
