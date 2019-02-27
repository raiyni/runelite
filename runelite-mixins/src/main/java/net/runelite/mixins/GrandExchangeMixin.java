/*
 * Copyright (c) 2019, DennisDeV <https://github.com/DevDennis>
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
package net.runelite.mixins;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.runelite.api.TagManager;
import net.runelite.api.WorldType;
import net.runelite.api.mixins.Copy;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Replace;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSItemComposition;

@Mixin(RSClient.class)
public abstract class GrandExchangeMixin implements RSClient
{
	private static final int MAX_RESULT_COUNT = 250;

	@Shadow("clientInstance")
	private static RSClient client;

	@Copy("searchGrandExchangeItems")
	static void rs$searchGrandExchangeItems(String input, boolean tradableOnly)
	{
		throw new RuntimeException();
	}

	@Replace("searchGrandExchangeItems")
	public static void rl$searchGrandExchangeItems(String input, boolean tradableOnly)
	{
		final TagManager tagManager = client.getTagManager();
		if (tagManager == null || !tagManager.isSearchStr(input))
		{
			rs$searchGrandExchangeItems(input, tradableOnly);
			return;
		}

		final boolean inMembersWorld = client.getWorldType().contains(WorldType.MEMBERS);

		String tag = tagManager.getSearchStr(input);

		Set<Integer> items = new TreeSet<Integer>();
		List<Integer> tags = tagManager.getItemsForTag(tag);

		for (Integer item : tags)
		{
			Collection<Integer> collection = tagManager.getVariations(Math.abs(item));
			for (Integer id : collection)
			{
				RSItemComposition comp = client.getItemDefinition(id);

				if (comp != null && (!tradableOnly || comp.isTradeable()) && (inMembersWorld || !comp.isMembers()))
				{
					items.add(comp.getId());
				}
			}
		}

		int resultCount = Math.min(items.size(), MAX_RESULT_COUNT);
		short[] results = new short[resultCount];
		Iterator<Integer> itr = items.iterator();
		for (int i = 0; itr.hasNext() && i < resultCount; i++)
		{
			results[i] = (short) (itr.next() & 0xFFFF);
		}

		client.setGeSearchResultIndex(0);
		client.setGeSearchResultCount(resultCount);
		client.setGeSearchResultIds(results);
	}
}
