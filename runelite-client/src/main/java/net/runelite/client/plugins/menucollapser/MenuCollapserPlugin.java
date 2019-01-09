/*
 * Copyright (c) 2019, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.menucollapser;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Menu Collapser",
	description = "Collapses duplicate ground item menu entries",
	enabledByDefault = false
)
public class MenuCollapserPlugin extends Plugin
{
	private static final int TAKE_ITEM = MenuAction.GROUND_ITEM_THIRD_OPTION.getId();
	private static final int EXAMINE_ITEM = MenuAction.EXAMINE_ITEM_GROUND.getId();
	private static final int CANCEL = MenuAction.CANCEL.getId();

	@Inject
	private Client client;

	private final List<CollapsedEntry> collapsedEntries = new ArrayList<>();

	@Override
	protected void shutDown()
	{
		collapsedEntries.clear();
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded menuEntryAdded)
	{
		int type = menuEntryAdded.getType();

		if (type == CANCEL)
		{
			// cancel is always the first action added
			collapsedEntries.clear();
		}

		if (type != TAKE_ITEM && type != EXAMINE_ITEM)
		{
			CollapsedEntry collapsedEntry = new CollapsedEntry(menuEntryAdded, -1);
			collapsedEntries.add(collapsedEntry);
			return;
		}

		for (CollapsedEntry collapsedEntry : collapsedEntries)
		{
			if (collapsedEntry.entry.equals(menuEntryAdded))
			{
				collapsedEntry.count++;

				client.setMenuEntries(collapsedEntries.stream()
					.map(MenuCollapserPlugin::convert)
					.toArray(MenuEntry[]::new));
				return;
			}
		}

		CollapsedEntry collapsedEntry = new CollapsedEntry(menuEntryAdded, 1);
		collapsedEntries.add(collapsedEntry);
	}

	private static MenuEntry convert(CollapsedEntry collapsedEntry)
	{
		MenuEntry menuEntry = new MenuEntry();
		MenuEntryAdded entry = collapsedEntry.entry;
		int count = collapsedEntry.count;
		menuEntry.setOption(entry.getOption());
		if (count > 1)
		{
			menuEntry.setTarget(entry.getTarget() + " x " + count);
		}
		else
		{
			menuEntry.setTarget(entry.getTarget());
		}
		menuEntry.setIdentifier(entry.getIdentifier());
		menuEntry.setType(entry.getType());
		menuEntry.setParam0(entry.getActionParam0());
		menuEntry.setParam1(entry.getActionParam1());
		return menuEntry;
	}
}
