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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.skin.GradientBorder;
import net.runelite.client.ui.skin.SkinUtil;

public class BaseComboBoxUI extends BasicComboBoxUI
{
	private final ColorScheme scheme = SkinUtil.getColorScheme();

	private JComboBox comboBox;

	public BaseComboBoxUI(JComponent comp)
	{
		this.comboBox = (JComboBox) comp;
	}

	public static ComponentUI createUI(JComponent comp)
	{
		return new BaseComboBoxUI(comp);
	}

	@Override
	protected void installDefaults()
	{
		if (scheme == null)
		{
			super.installDefaults();
			return;
		}

		Border b = new GradientBorder(scheme.getBorderOutline(), scheme.getBorderAccent(),
			0, 0, 0, 0);

		Color background = scheme.getComponentBackground();

		comboBox.setOpaque(true);
		comboBox.setFont(FontManager.getRunescapeFont());
		comboBox.setBackground(background);
		comboBox.setBorder(b);

		comboBox.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				comboBox.setBackground(background.brighter());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				comboBox.setBackground(background);
			}
		});

		squareButton = true;
		padding = b.getBorderInsets(comboBox);
	}

	@Override
	protected JButton createArrowButton()
	{
		if (scheme == null)
		{
			return super.createArrowButton();
		}

		Icon icon = new ComboBoxIcon(scheme.getCaretColor());
		JButton button = new JButton(icon);
		button.setOpaque(false);
		button.setBackground(null);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.addMouseListener(comboBox.getMouseListeners()[0]);
		return button;
	}

	@Override
	protected ComboPopup createPopup()
	{
		if (scheme == null)
		{
			return super.createPopup();
		}

		BasicComboPopup popup = new BasicComboPopup(comboBox);
		popup.setBorder(new LineBorder(scheme.getBorderOutline()));
		popup.setBackground(scheme.getComponentFocus());
		return popup;
	}

	@Override
	public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus)
	{
		g.setColor(comboBox.getBackground());
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	@Override
	public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus)
	{
		if (scheme == null)
		{
			super.paintCurrentValue(g, bounds, hasFocus);
			return;
		}

		g.setColor(scheme.getToolTipForeground());
		Object selected = comboBox.getSelectedItem();
		if (selected != null)
		{
			g.drawString(selected.toString(), bounds.x + 5, bounds.height - bounds.y);
		}
	}
}
