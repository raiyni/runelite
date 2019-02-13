/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
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
package net.runelite.client.ui.skin.dark;

import javax.swing.UIDefaults;
import net.runelite.client.ui.skin.base.LookAndFeel;

public class DarkLookAndFeel extends LookAndFeel
{
	public DarkLookAndFeel()
	{
		super(new DarkColorScheme());
	}

	@Override
	public String getName()
	{
		return "RuneLite Dark";
	}

	@Override
	public String getID()
	{
		return "RuneLite Dark";
	}

	@Override
	public String getDescription()
	{
		return "RuneLite Dark";
	}

	@Override
	public boolean isNativeLookAndFeel()
	{
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel()
	{
		return true;
	}

	@Override
	protected void initClassDefaults(UIDefaults table)
	{
		super.initClassDefaults(table);

//		table.put("ButtonUI", DarkButtonUI.class.getCanonicalName());
//		table.put("LabelUI", DarkLabelUI.class.getCanonicalName());
//		table.put("PanelUI", DarkPanelUI.class.getCanonicalName());
//		table.put("ToolTipUI", DarkToolTipUI.class.getCanonicalName());
//		table.put("ScrollBarUI", DarkScrollBarUI.class.getCanonicalName());
////		table.put("ToggleButtonUI", DarkToggleButtonUI.class.getCanonicalName());
//		table.put("CheckBoxUI", DarkCheckBoxUI.class.getCanonicalName());
//		table.put("ComboBoxUI", DarkComboBoxUI.class.getCanonicalName());

	}
}