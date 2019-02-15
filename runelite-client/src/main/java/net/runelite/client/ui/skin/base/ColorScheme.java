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

import java.awt.Color;
import javax.swing.border.Border;

public abstract class ColorScheme
{
	private static final Color BRAND_ORANGE = new Color(220, 138, 0);
	public static final String KEY = "ColorScheme";
	public static final String BUTTON_COLOR = "ButtonColor";

	public final Color getBrandAccent()
	{
		return BRAND_ORANGE;
	}

	public abstract Border getBorder();

	public abstract Color getToolTipBackground();

	public abstract Color getToolTipForeground();

	public abstract Color getPanelBackground();

	public abstract Color getBorderOutline();

	public abstract Color getBorderAccent();

	public abstract Color getLabelForeground();

	public abstract Color getEditorForeground();

	public abstract Color getSelectionBackground();

	public abstract Color getCaretColor();

	public abstract Color getComponentBackground();

	public abstract Color getComponentFocus();

	public abstract Color getFlatComponentBackground();

	public abstract Color getFlatComponentFocus();

	public abstract Color getCheckBoxBackground();

	public abstract Color getCheckBoxSelected();

	public abstract Color getProgressErrorColor();
}
