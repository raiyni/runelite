/*
 * Copyright (c) 2020, Ron Young <https://github.com/raiyni>
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

package net.runelite.client.ui.overlay.infobox;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.client.config.ConfigManager;

@Singleton
class LayerManager
{
	private static final String OVERLAY_KEY = "overlay";
	private static final String LAYER_PREFIX = "layer_";
	private static final String DEFAULT_LAYER = "Default";

	private final ConfigManager configManager;

	@Inject
	private LayerManager(ConfigManager configManager)
	{
		this.configManager = configManager;

		configManager.unsetConfiguration(OVERLAY_KEY, "layers");
	}

	Set<String> getLayerNames()
	{
		Set<String> set = Sets.newHashSet(DEFAULT_LAYER);
		List<String> keys = configManager.getConfigurationKeys(OVERLAY_KEY + "." + LAYER_PREFIX);

		for (String key: keys)
		{
			set.add(configManager.getConfiguration(OVERLAY_KEY, key.substring(OVERLAY_KEY.length() + 1)));
		}
		return set;
	}

	String getLayerName(InfoBox infoBox)
	{
		return MoreObjects.firstNonNull(configManager.getConfiguration(OVERLAY_KEY, LAYER_PREFIX + infoBox.getName()), DEFAULT_LAYER);
	}

	void setLayer(String layer, InfoBox infoBox)
	{
		configManager.setConfiguration(OVERLAY_KEY, LAYER_PREFIX + infoBox.getName(), layer);
	}
}
