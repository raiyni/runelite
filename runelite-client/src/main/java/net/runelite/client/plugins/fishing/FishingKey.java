/*
 * Copyright (c) 2019, Ron Young <https://github.com/raiyni>
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
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

package net.runelite.client.plugins.fishing;

import javax.annotation.Nonnull;
import lombok.Value;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

@Value
public class FishingKey implements Comparable
{
	private final NPC npc;
	private final FishingSpot spot;
	private final Client client;

	private boolean npcEquals(@Nonnull NPC npc2)
	{
		WorldPoint p1 = npc.getWorldLocation();
		WorldPoint p2 = npc2.getWorldLocation();
		return p1.getX() == p2.getX() && p1.getY() == p2.getY();
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof FishingKey))
		{
			return false;
		}

		if (o == this)
		{
			return true;
		}

		FishingKey key2 = (FishingKey) o;

		return npcEquals(key2.getNpc()) && spot == key2.getSpot();
	}

	@Override
	public int compareTo(Object o)
	{
		System.out.println(true);
		if (!(o instanceof FishingKey))
		{
			return -1;
		}

		FishingKey key2 = (FishingKey) o;
		if (o == this || npcEquals(key2.getNpc()) && spot == key2.getSpot())
		{
			return 0;
		}

		final LocalPoint cameraPoint = new LocalPoint(client.getCameraX(), client.getCameraY());
		int d1 = -1 * npc.getLocalLocation().distanceTo(cameraPoint);
		int d2 = -1 * key2.getNpc().getLocalLocation().distanceTo(cameraPoint);
		return d2 - d1;
	}
}
