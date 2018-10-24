/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * Copyright (c) 2018, Ron Young <https://github.com/raiyni>
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
package net.runelite.client.plugins.banktags;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ChargedItem;
import net.runelite.client.game.ItemManager;
import static net.runelite.client.plugins.banktags.BankTagsPlugin.CONFIG_GROUP;
import static net.runelite.client.plugins.banktags.BankTagsPlugin.JOINER;
import static net.runelite.client.plugins.banktags.BankTagsPlugin.SPLITTER;
import net.runelite.client.plugins.cluescrolls.ClueScrollService;
import net.runelite.client.plugins.cluescrolls.clues.ClueScroll;
import net.runelite.client.plugins.cluescrolls.clues.CoordinateClue;
import net.runelite.client.plugins.cluescrolls.clues.EmoteClue;
import net.runelite.client.plugins.cluescrolls.clues.FairyRingClue;
import net.runelite.client.plugins.cluescrolls.clues.HotColdClue;
import net.runelite.client.plugins.cluescrolls.clues.MapClue;
import net.runelite.client.plugins.cluescrolls.clues.emote.ItemRequirement;
import net.runelite.client.util.Text;

@Singleton
public class TagManager
{
	private static final String ITEM_KEY_PREFIX = "item_";
	private static final String VARIATION_KEY_PREFIX = "variation_";
	private final ConfigManager configManager;
	private final ItemManager itemManager;

	private final ClueScrollService clueScrollService;

	@Inject
	private TagManager(
		final ItemManager itemManager,
		final ConfigManager configManager,
		final ClueScrollService clueScrollService)
	{
		this.itemManager = itemManager;
		this.configManager = configManager;
		this.clueScrollService = clueScrollService;
	}

	String getTagString(int itemId)
	{
		itemId = itemManager.canonicalize(itemId);
		String config = configManager.getConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
		if (config == null)
		{
			return "";
		}

		return config;
	}

	private int uncharged(int id)
	{
		return ChargedItem.get(id) * -1;
	}

	String getVariationTagString(int itemId)
	{
		int id = uncharged(itemManager.canonicalize(itemId));
		String config = configManager.getConfiguration(CONFIG_GROUP, VARIATION_KEY_PREFIX + id);
		if (config == null)
		{
			return "";
		}

		return config;
	}

	Collection<String> getVariationTags(int itemId)
	{
		return new LinkedHashSet<>(SPLITTER.splitToList(getVariationTagString(itemId).toLowerCase()));
	}

	Collection<String> getTags(int itemId)
	{
		return new LinkedHashSet<>(SPLITTER.splitToList(getTagString(itemId).toLowerCase()));
	}

	void setVariationTagString(int itemId, String tags)
	{
		int id = uncharged(itemManager.canonicalize(itemId));
		if (Strings.isNullOrEmpty(tags))
		{
			configManager.unsetConfiguration(CONFIG_GROUP, VARIATION_KEY_PREFIX + id);
		}
		else
		{
			configManager.setConfiguration(CONFIG_GROUP, VARIATION_KEY_PREFIX + id, tags);
		}
	}

	void setTagString(int itemId, String tags)
	{
		itemId = itemManager.canonicalize(itemId);
		if (Strings.isNullOrEmpty(tags))
		{
			configManager.unsetConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
		}
		else
		{
			configManager.setConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId, tags);
		}
	}

	public void addTags(int itemId, final Collection<String> t)
	{
		final Collection<String> tags = getTags(itemId);
		if (tags.addAll(t))
		{
			setTags(itemId, tags);
		}
	}

	public void addTag(int itemId, String tag)
	{
		final Collection<String> tags = getTags(itemId);
		if (tags.add(Text.standardize(tag)))
		{
			setTags(itemId, tags);
		}
	}

	public void addVariationTag(int itemId, String tag)
	{
		final Collection<String> tags = getVariationTags(itemId);
		if (tags.add(Text.standardize(tag)))
		{
			setVariationTags(itemId, tags);
		}
	}

	private void setVariationTags(int itemId, Collection<String> tags)
	{
		setVariationTagString(itemId, JOINER.join(tags));
	}

	private void setTags(int itemId, Collection<String> tags)
	{
		setTagString(itemId, JOINER.join(tags));
	}

	boolean findTag(int itemId, String search)
	{
		if (search.equals("clue") && testClue(itemId))
		{
			return true;
		}

		Collection<String> tags = getTags(itemId);
		tags.addAll(getVariationTags(itemId));
		return tags.stream().anyMatch(tag -> tag.contains(Text.standardize(search)));
	}

	public List<Integer> getItemsForTag(String tag)
	{
		final String prefix = CONFIG_GROUP + "." + ITEM_KEY_PREFIX;
		return configManager.getConfigurationKeys(prefix).stream()
			.map(item -> Integer.parseInt(item.replace(prefix, "")))
			.filter(item -> getTags(item).contains(tag))
			.collect(Collectors.toList());
	}

	public void removeTag(String tag)
	{
		final String prefix = CONFIG_GROUP + "." + ITEM_KEY_PREFIX;
		configManager.getConfigurationKeys(prefix).forEach(item -> removeTag(Integer.parseInt(item.replace(prefix, "")), tag));

		final String chargePrefix = CONFIG_GROUP + "." + VARIATION_KEY_PREFIX;
		configManager.getConfigurationKeys(chargePrefix).forEach(item -> removeVariationTag(Integer.parseInt(item.replace(chargePrefix, "")), tag));
	}

	public void removeVariationTag(int itemId, String tag)
	{
		final Collection<String> tags = getVariationTags(itemId);
		if (tags.remove(Text.standardize(tag)))
		{
			setVariationTags(itemId, tags);
		}
	}

	public void removeTag(int itemId, String tag)
	{
		final Collection<String> tags = getTags(itemId);
		if (tags.remove(Text.standardize(tag)))
		{
			setTags(itemId, tags);
		}
	}

	private boolean testClue(int itemId)
	{
		ClueScroll c = clueScrollService.getClue();

		if (c == null)
		{
			return false;
		}

		if (c instanceof EmoteClue)
		{
			EmoteClue emote = (EmoteClue) c;

			for (ItemRequirement ir : emote.getItemRequirements())
			{
				if (ir.fulfilledBy(itemId))
				{
					return true;
				}
			}
		}
		else if (c instanceof CoordinateClue || c instanceof HotColdClue || c instanceof FairyRingClue)
		{
			return itemId == ItemID.SPADE;
		}
		else if (c instanceof MapClue)
		{
			MapClue mapClue = (MapClue) c;

			return mapClue.getObjectId() == -1 && itemId == ItemID.SPADE;
		}

		return false;
	}
}
