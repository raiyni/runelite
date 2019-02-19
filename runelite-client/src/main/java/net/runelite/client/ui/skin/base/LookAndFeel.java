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

package net.runelite.client.ui.skin.base;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicLookAndFeel;
import net.runelite.client.ui.FontManager;
import static net.runelite.client.ui.skin.SkinUtil.MULTILINE_INPUT_MAP;

public class LookAndFeel extends BasicLookAndFeel
{
	private final ColorScheme scheme;

	public LookAndFeel(ColorScheme scheme)
	{
		this.scheme = scheme;
	}

	@Override
	protected void initClassDefaults(UIDefaults table)
	{
		super.initClassDefaults(table);

		UIManager.put("ColorScheme", this.scheme);
		UIManager.put("ToolTip.font", FontManager.getRunescapeFont());
		UIManager.put("ToolTip.background", scheme.getToolTipBackground());
		UIManager.put("ToolTip.foreground", scheme.getToolTipForeground());
		UIManager.put("ToolTip.border", scheme.getBorder());

		UIManager.put("Panel.AATextInfoPropertyKey", null);
		UIManager.put("Panel.background", scheme.getPanelBackground());
		UIManager.put("Panel.border", null);

		UIManager.put("Label.foreground", scheme.getLabelForeground());
		UIManager.put("Label.opaque", false);
		UIManager.put("Label.AATextInfoPropertyKey", null);

		UIManager.put("TextArea.background", scheme.getComponentBackground());
		UIManager.put("TextArea.foreground", scheme.getEditorForeground());
		UIManager.put("TextArea.selectionBackground", scheme.getSelectionBackground());
		UIManager.put("TextArea.caretForeground", scheme.getCaretColor());
		UIManager.put("TextArea.focusInputMap", MULTILINE_INPUT_MAP);

		UIManager.put("Tree.background", scheme.getPanelBackground());
		UIManager.put("Tree.border", null);
		UIManager.put("Tree.paintLines", false);

		UIManager.put("TableHeader.background", scheme.getFlatComponentBackground());
		UIManager.put("TableHeader.foreground", scheme.getLabelForeground());


		UIManager.put("Table.background", scheme.getPanelBackground());
		UIManager.put("Table.border", null);

		UIManager.put("Viewport.background", scheme.getPanelBackground());

		UIManager.put("SplitPane.background", scheme.getPanelBackground());
		UIManager.put("SplitPane.border", new EmptyBorder(0, 0, 0, 0));
		UIManager.put("SplitPane.dividerSize", 0);


//		UIManager.put("TextPane.foreground", scheme.getLabelForeground());
//		UIManager.put("TextPane.background", null);
//		UIManager.put("TextPane.font", FontManager.getRunescapeSmallFont());
//		UIManager.put("TextPane.opaque", false);
//		UIManager.put("TextPane.border", null);


		table.put("TextAreaUI", BaseTextAreaUI.class.getCanonicalName());
		table.put("ButtonUI", BaseButtonUI.class.getCanonicalName());
		table.put("ScrollBarUI", BaseScrollBarUI.class.getCanonicalName());
		table.put("CheckBoxUI", BaseCheckBoxUI.class.getCanonicalName());
		table.put("ComboBoxUI", BaseComboBoxUI.class.getCanonicalName());
		table.put("SpinnerUI", BaseSpinnerUI.class.getCanonicalName());
		table.put("ScrollPaneUI", BaseScrollPaneUI.class.getCanonicalName());
		table.put("TextFieldUI", BaseTextFieldUI.class.getCanonicalName());
		table.put("TableHeaderUI", BaseTableHeaderUI.class.getCanonicalName());
		table.put("TreeUI", BaseTreeUI.class.getCanonicalName());

		// This doesn't work, ok swing
		//UIManager.put("ScrollPane.border", null);
	}

	@Override
	public String getName()
	{
		return "Base LF";
	}

	@Override
	public String getID()
	{
		return "Base LF";
	}

	@Override
	public String getDescription()
	{
		return "Base LF";
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
}
