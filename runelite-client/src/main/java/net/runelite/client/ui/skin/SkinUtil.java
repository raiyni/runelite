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

package net.runelite.client.ui.skin;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultEditorKit;
import net.runelite.client.ui.skin.base.ColorScheme;
import sun.swing.SwingUtilities2;

public class SkinUtil
{
	public static ColorScheme getColorScheme()
	{
		Object obj = UIManager.get("ColorScheme");
		if (!(obj instanceof ColorScheme))
		{
			return null;
		}

		return (ColorScheme) obj;
	}

	public static void disableAA(JComponent c)
	{
		c.putClientProperty(SwingUtilities2.AA_TEXT_PROPERTY_KEY, null);
	}

	public static final InputMap MULTILINE_INPUT_MAP = new InputMap();

	public static final Border NO_BORDER = new EmptyBorder(0, 0, 0, 0);

	static
	{
		addInput("ctrl C", DefaultEditorKit.copyAction);
		addInput("ctrl V", DefaultEditorKit.pasteAction);
		addInput("ctrl X", DefaultEditorKit.cutAction);
		addInput("COPY", DefaultEditorKit.copyAction);
		addInput("PASTE", DefaultEditorKit.pasteAction);
		addInput("CUT", DefaultEditorKit.cutAction);
		addInput("control INSERT", DefaultEditorKit.copyAction);
		addInput("shift INSERT", DefaultEditorKit.pasteAction);
		addInput("shift DELETE", DefaultEditorKit.cutAction);

		addInput("shift LEFT", DefaultEditorKit.selectionBackwardAction);
		addInput("shift KP_LEFT", DefaultEditorKit.selectionBackwardAction);
		addInput("shift RIGHT", DefaultEditorKit.selectionForwardAction);
		addInput("shift KP_RIGHT", DefaultEditorKit.selectionForwardAction);
		addInput("ctrl LEFT", DefaultEditorKit.previousWordAction);
		addInput("ctrl KP_LEFT", DefaultEditorKit.previousWordAction);
		addInput("ctrl RIGHT", DefaultEditorKit.nextWordAction);
		addInput("ctrl KP_RIGHT", DefaultEditorKit.nextWordAction);
		addInput("ctrl shift LEFT", DefaultEditorKit.selectionPreviousWordAction);
		addInput("ctrl shift KP_LEFT", DefaultEditorKit.selectionPreviousWordAction);
		addInput("ctrl shift RIGHT", DefaultEditorKit.selectionNextWordAction);
		addInput("ctrl shift KP_RIGHT", DefaultEditorKit.selectionNextWordAction);

		addInput("ctrl A", DefaultEditorKit.selectAllAction);
		addInput("HOME", DefaultEditorKit.beginLineAction);
		addInput("END", DefaultEditorKit.endLineAction);
		addInput("shift HOME", DefaultEditorKit.selectionBeginLineAction);
		addInput("shift END", DefaultEditorKit.selectionEndLineAction);

		addInput("UP", DefaultEditorKit.upAction);
		addInput("KP_UP", DefaultEditorKit.upAction);
		addInput("DOWN", DefaultEditorKit.downAction);
		addInput("KP_DOWN", DefaultEditorKit.downAction);
		addInput("PAGE_UP", DefaultEditorKit.pageUpAction);
		addInput("PAGE_DOWN", DefaultEditorKit.pageDownAction);

		addInput("shift PAGE_UP", TextComponentActions.SELECTION_PAGE_UP);
		addInput("shift PAGE_DOWN", TextComponentActions.SELECTION_PAGE_DOWN);
		addInput("ctrl shift PAGE_UP", TextComponentActions.SELECTION_PAGE_LEFT);
		addInput("ctrl shift PAGE_DOWN", TextComponentActions.SELECTION_PAGE_RIGHT);
		addInput("shift UP", DefaultEditorKit.selectionUpAction);
		addInput("shift KP_UP", DefaultEditorKit.selectionUpAction);
		addInput("shift DOWN", DefaultEditorKit.selectionDownAction);
		addInput("shift KP_DOWN", DefaultEditorKit.selectionDownAction);

		addInput("ctrl shift HOME", DefaultEditorKit.selectionBeginAction);
		addInput("ctrl shift END", DefaultEditorKit.selectionEndAction);

		addInput("ENTER", DefaultEditorKit.insertBreakAction);
		addInput("BACK_SPACE", DefaultEditorKit.deletePrevCharAction);
		addInput("shift BACK_SPACE", DefaultEditorKit.deletePrevCharAction);
		addInput("ctrl H", DefaultEditorKit.deletePrevCharAction);
		addInput("DELETE", DefaultEditorKit.deleteNextCharAction);
		addInput("ctrl DELETE", DefaultEditorKit.deleteNextWordAction);
		addInput("ctrl BACK_SPACE", DefaultEditorKit.deletePrevWordAction);

		addInput("RIGHT", DefaultEditorKit.forwardAction);
		addInput("KP_RIGHT", DefaultEditorKit.forwardAction);
		addInput("LEFT", DefaultEditorKit.backwardAction);
		addInput("KP_LEFT", DefaultEditorKit.backwardAction);
		addInput("TAB", DefaultEditorKit.insertTabAction);
		addInput("ctrl BACK_SLASH", TextComponentActions.UNSELECT);
		addInput("ctrl HOME", DefaultEditorKit.beginAction);
		addInput("ctrl END", DefaultEditorKit.endAction);

		addInput("ctrl T", TextComponentActions.NEXT_LINK);
		addInput("ctrl shift T", TextComponentActions.PREVIOUS_LINK);
		addInput("ctrl SPACE", TextComponentActions.ACTIVATE_LINK);
		addInput("control shift O", TextComponentActions.TOGGLE_COMPONENT_ORIENTATION);
	}

	private static void addInput(String key, Object value)
	{
		MULTILINE_INPUT_MAP.put(KeyStroke.getKeyStroke(key), value);
	}

	private static class TextComponentActions
	{
		public static final String SELECTION_PAGE_UP = "selection-page-up";
		public static final String SELECTION_PAGE_DOWN = "selection-page-down";
		public static final String SELECTION_PAGE_LEFT = "selection-page-left";
		public static final String SELECTION_PAGE_RIGHT = "selection-page-right";
		public static final String UNSELECT = "unselect";
		public static final String TOGGLE_COMPONENT_ORIENTATION = "toggle-componentOrientation";

		// from HTMLEditorKit
		public static final String NEXT_LINK = "next-link-action";
		public static final String PREVIOUS_LINK = "previous-link-action";
		public static final String ACTIVATE_LINK = "activate-link-action";

		// from JFormattedTextField.CancelAction
		public static final String RESET_FIELD_EDIT = "reset-field-edit";

		// from BasicSpinnerUI.loadActionMap
		public static final String INCREMENT = "increment";
		public static final String DECREMENT = "decrement";
	}
}
