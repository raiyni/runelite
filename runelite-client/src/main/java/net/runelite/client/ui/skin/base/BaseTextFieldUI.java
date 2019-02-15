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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import net.runelite.client.ui.skin.SkinUtil;

public class BaseTextFieldUI extends BasicTextFieldUI
{
	JTextField field;

	public static ComponentUI createUI(JComponent comp)
	{
		return new BaseTextFieldUI(comp);
	}

	public BaseTextFieldUI(JComponent comp)
	{
		super();

		this.field = (JTextField) comp;
	}

	@Override
	protected void installDefaults()
	{
		super.installDefaults();

		ColorScheme scheme = SkinUtil.getColorScheme();
		if (scheme == null)
		{
			return;
		}

		field.putClientProperty(ColorScheme.KEY, scheme);
		field.setForeground(scheme.getEditorForeground());
		field.setCaretColor(scheme.getCaretColor());
		field.setSelectionColor(scheme.getSelectionBackground());
		field.setBackground(scheme.getFlatComponentBackground());
		field.setBorder(new EmptyBorder(5, 7, 5, 7));
		field.setEditable(true);
		field.setFocusable(true);
		field.setInputMap(JTextField.WHEN_FOCUSED, SkinUtil.MULTILINE_INPUT_MAP);
		field.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (field.isEditable())
				{
					field.setBackground(scheme.getFlatComponentFocus());
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				field.setBackground(scheme.getFlatComponentBackground());
			}
		});

//				field.addFocusListener(new FocusListener()
//		{
//			@Override
//			public void focusGained(FocusEvent e)
//			{
//				if (field.isEditable())
//				{
//					field.setBackground(scheme.getFlatComponentFocus());
//				}
//			}
//
//			@Override
//			public void focusLost(FocusEvent e)
//			{
//				field.setBackground(scheme.getFlatComponentBackground());
//			}
//		});
	}
}
