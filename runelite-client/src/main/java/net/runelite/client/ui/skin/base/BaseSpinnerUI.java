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

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSpinnerUI;
import net.runelite.client.ui.skin.SkinUtil;

public class BaseSpinnerUI extends BasicSpinnerUI
{
	JSpinner spinner;

	public static ComponentUI createUI(JComponent comp)
	{
		return new BaseSpinnerUI(comp);
	}

	public BaseSpinnerUI(JComponent comp)
	{
		this.spinner = (JSpinner) comp;
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

		spinner.setBorder(BorderFactory.createLineBorder(scheme.getBorderOutline()));
		spinner.setBackground(scheme.getFlatComponentBackground());
		spinner.setForeground(scheme.getEditorForeground());

	}

	@Override
	protected Component createNextButton()
	{
		Component c = createArrowButton(SwingConstants.NORTH);
		installNextButtonListeners(c);
		return c;
	}

	@Override
	protected Component createPreviousButton()
	{
		Component c = createArrowButton(SwingConstants.SOUTH);
		installPreviousButtonListeners(c);
		return c;
	}


	private Component createArrowButton(int direction)
	{
		ColorScheme scheme = SkinUtil.getColorScheme();
		if (scheme == null)
		{
			return new BasicArrowButton(direction);
		}

		JButton b = new BaseArrowButton(direction,
			scheme.getFlatComponentBackground(), scheme.getEditorForeground(),
			scheme.getFlatComponentFocus(), scheme.getBorderOutline());
		b.setInheritsPopupMenu(true);
		return b;
	}

	@Override
	protected JComponent createEditor()
	{
		JComponent editor = spinner.getEditor();

		ColorScheme scheme = SkinUtil.getColorScheme();
		if (scheme == null)
		{
			return editor;
		}

		JTextField text = ((JSpinner.DefaultEditor) editor).getTextField();
//		text.setHorizontalAlignment(SwingConstants.RIGHT);
		text.setForeground(spinner.getForeground());
		text.setBackground(spinner.getBackground());
		text.setBorder(new EmptyBorder(3, 5, 2, 5));
		text.setCaretColor(scheme.getCaretColor());
		text.setSelectionColor(scheme.getSelectionBackground());
		text.setOpaque(true);

		text.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				spinner.setBackground(scheme.getFlatComponentFocus());
				text.setBackground(scheme.getFlatComponentFocus());
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				spinner.setBackground(scheme.getFlatComponentBackground());
				text.setBackground(scheme.getFlatComponentBackground());
			}
		});

		text.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				spinner.setBackground(scheme.getFlatComponentFocus());
				text.setBackground(scheme.getFlatComponentFocus());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if (!text.isFocusOwner())
				{
					spinner.setBackground(scheme.getFlatComponentBackground());
					text.setBackground(scheme.getFlatComponentBackground());
				}
			}
		});

		return editor;
	}
}
