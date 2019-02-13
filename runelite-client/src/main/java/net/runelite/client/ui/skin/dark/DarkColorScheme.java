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

package net.runelite.client.ui.skin.dark;

import java.awt.Color;
import javax.swing.border.Border;
import net.runelite.client.ui.skin.GradientBorder;
import net.runelite.client.ui.skin.base.ColorScheme;

public class DarkColorScheme extends ColorScheme
{
	private static final Color PANEL_BACKGROUND = new Color(40, 40, 40);

	private static final Color TOOLTIP_BACKGROUND = new Color(50, 50, 50);
	private static final Color TOOLTIP_FOREGROUND = new Color(198, 198, 198);

	private static final Color BORDER_OUTLINE = new Color(25, 25, 25);
	private static final Color BORDER_ACCENT = new Color(255, 255, 255, 25);

	private static final Color COMPONENT_BACKGROUND = new Color(35, 35, 35);
	private static final Color COMPONENT_FOCUS = new Color(60, 60, 60);

	private static final Color EDITOR_FOREGROUND = new Color(198, 198, 198);
	private static final Color SELECTION = new Color(220, 138, 0, 120);

	private static final Color CHECKBOX_BACKGROUND = new Color(100, 100, 100);
	private static final Color CHECKBOX_ACTIVE = new Color(122, 122, 122);

	@Override
	public Border getBorder()
	{
		return new GradientBorder(getBorderOutline(), getBorderAccent(),
			1, 1, 1, 1);
	}

	@Override
	public Color getToolTipBackground()
	{
		return TOOLTIP_BACKGROUND;
	}

	@Override
	public Color getToolTipForeground()
	{
		return TOOLTIP_FOREGROUND;
	}

	@Override
	public Color getPanelBackground()
	{
		return PANEL_BACKGROUND;
	}

	@Override
	public Color getBorderOutline()
	{
		return BORDER_OUTLINE;
	}

	@Override
	public Color getBorderAccent()
	{
		return BORDER_ACCENT;
	}

	@Override
	public Color getLabelForeground()
	{
		return Color.WHITE;
	}

	@Override
	public Color getEditorForeground()
	{
		return EDITOR_FOREGROUND;
	}

	@Override
	public Color getSelectionBackground()
	{
		return SELECTION;
	}

	@Override
	public Color getCaretColor()
	{
		return Color.WHITE;
	}

	@Override
	public Color getFlatComponentBackground()
	{
		return COMPONENT_BACKGROUND;
	}

	@Override
	public Color getFlatComponentFocus()
	{
		return COMPONENT_FOCUS;
	}

	@Override
	public Color getCheckBoxBackground()
	{
		return CHECKBOX_BACKGROUND;
	}

	@Override
	public Color getCheckBoxSelected()
	{
		return CHECKBOX_ACTIVE;
	}
}
