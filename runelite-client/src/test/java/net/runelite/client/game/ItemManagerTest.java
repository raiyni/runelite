/*
 * Copyright (c) 2018, Ron Young <https://github.com/raiyni>
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

package net.runelite.client.game;

import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemManagerTest
{
	@Mock
	@Bind
	Client client;

	@Mock
	@Bind
	ScheduledExecutorService scheduledExecutorService;

	@Mock
	@Bind
	ClientThread clientThread;

	@Inject
	ItemManager itemManager;

	@Before
	public void before()
	{
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
	}

	private static final Map<String, String> ITEMS_MAP = new LinkedHashMap<String, String>()
	{{
		put("'perfect' necklace", "'perfect' necklace");
		put("'perfect' ring", "'perfect' ring");
		put("1/2 anchovy pizza", "anchovy pizza");
		put("1/2 meat pizza", "meat pizza");
		put("1/2 pineapple pizza", "pineapple pizza");
		put("1/2 plain pizza", "plain pizza");
		put("2/3 cake", "cake");
		put("2/3 chocolate cake", "chocolate cake");

		put("pot lid", "pot lid");
		put("pot of cornflour", "pot of cornflour");
		put("pot of cream", "pot of cream");
		put("pot of flour", "pot of flour");
		put("pot of quicklime", "pot of quicklime");
		put("pot of tea (1)", "pot of tea");
		put("pot of tea (2)", "pot of tea");
		put("pot of tea (3)", "pot of tea");
		put("pot of tea (4)", "pot of tea");
		put("pot of vinegar", "pot of vinegar");
		put("pot of weeds", "pot of weeds");

		put("super ranging (4)", "super ranging");
		put("super restore mix(1)", "super restore mix");
		put("super restore mix(2)", "super restore mix");
		put("super restore(1)", "super restore");
		put("super restore(2)", "super restore");
		put("super restore(3)", "super restore");
		put("super restore(4)", "super restore");
		put("super str. mix(1)", "super str. mix");
		put("super str. mix(2)", "super str. mix");
		put("super strength(1)", "super strength");
		put("super strength(2)", "super strength");
		put("super strength(3)", "super strength");
		put("super strength(4)", "super strength");
		put("superantipoison(1)", "superantipoison");
		put("superantipoison(2)", "superantipoison");
		put("superantipoison(3)", "superantipoison");
		put("superantipoison(4)", "superantipoison");

		put("ahrim's robeskirt", "ahrim's robeskirt");
		put("ahrim's robetop 0", "ahrim's robetop");
		put("ahrim's robetop 100", "ahrim's robetop");
		put("ahrim's robetop 25", "ahrim's robetop");
		put("ahrim's robetop 50", "ahrim's robetop");
		put("ahrim's robetop 75", "ahrim's robetop");
		put("ahrim's robetop", "ahrim's robetop");
		put("ahrim's staff 0", "ahrim's staff");
		put("ahrim's staff 100", "ahrim's staff");
		put("ahrim's staff 25", "ahrim's staff");
		put("ahrim's staff 50", "ahrim's staff");
		put("ahrim's staff 75", "ahrim's staff");

		put("amulet of fury", "amulet of fury");
		put("amulet of glory (t)", "amulet of glory");
		put("amulet of glory (t1)", "amulet of glory");
		put("amulet of glory (t2)", "amulet of glory");
		put("amulet of glory (t3)", "amulet of glory");
		put("amulet of glory (t4)", "amulet of glory");
		put("amulet of glory (t5)", "amulet of glory");
		put("amulet of glory (t6)", "amulet of glory");
		put("amulet of glory", "amulet of glory");
		put("amulet of glory(1)", "amulet of glory");
		put("amulet of glory(2)", "amulet of glory");
		put("amulet of glory(3)", "amulet of glory");
		put("amulet of glory(4)", "amulet of glory");
		put("amulet of glory(5)", "amulet of glory");
		put("amulet of glory(6)", "amulet of glory");
		put("amulet of holthion", "amulet of holthion");
		put("amulet of magic (t)", "amulet of magic");

		put("bastion potion(1)", "bastion potion");
		put("bastion potion(2)", "bastion potion");
		put("bastion potion(3)", "bastion potion");
		put("bastion potion(4)", "bastion potion");

		put("amulet of the damned (full)", "amulet of the damned");
		put("amulet of the damned", "amulet of the damned");

		put("clue nest (easy)", "clue nest");
		put("clue nest (elite)", "clue nest");
		put("clue nest (hard)", "clue nest");
		put("clue nest (medium)", "clue nest");
		put("clue scroll (easy)", "clue scroll");
		put("clue scroll (elite)", "clue scroll");
		put("clue scroll (hard)", "clue scroll");
		put("clue scroll (master)", "clue scroll");
		put("clue scroll (medium)", "clue scroll");

		put("crystal bow 1/10 (i)", "crystal bow");
		put("crystal bow 1/10", "crystal bow");
		put("crystal bow 2/10 (i)", "crystal bow");
		put("crystal bow 2/10", "crystal bow");
		put("crystal bow 3/10 (i)", "crystal bow");
		put("crystal bow 3/10", "crystal bow");
		put("crystal bow 4/10 (i)", "crystal bow");
		put("crystal bow 4/10", "crystal bow");
		put("crystal bow 5/10 (i)", "crystal bow");
		put("crystal bow 5/10", "crystal bow");
		put("crystal bow 6/10 (i)", "crystal bow");
		put("crystal bow 6/10", "crystal bow");
		put("crystal bow 7/10 (i)", "crystal bow");
		put("crystal bow 7/10", "crystal bow");
		put("crystal bow 8/10 (i)", "crystal bow");
		put("crystal bow 8/10", "crystal bow");
		put("crystal bow 9/10 (i)", "crystal bow");
		put("crystal bow 9/10", "crystal bow");
		put("crystal bow full (i)", "crystal bow");
		put("crystal bow full", "crystal bow");

		put("mind bomb(1)", "mind bomb");
		put("mind bomb(2)", "mind bomb");
		put("mind bomb(3)", "mind bomb");
		put("mind bomb(4)", "mind bomb");
		put("mind bomb(m1)", "mind bomb");
		put("mind bomb(m2)", "mind bomb");
		put("mind bomb(m3)", "mind bomb");
		put("mind bomb(m4)", "mind bomb");

		put("trident of the seas (e)", "trident of the seas");
		put("trident of the seas (full)", "trident of the seas");
		put("trident of the seas", "trident of the seas");
		put("trident of the swamp (e)", "trident of the swamp");
		put("trident of the swamp", "trident of the swamp");
	}};

	@Test
	public void unchargeName()
	{
		ITEMS_MAP.forEach((key, value) -> {
			assertEquals(value, itemManager.uncharged(key));
		});
	}
}
