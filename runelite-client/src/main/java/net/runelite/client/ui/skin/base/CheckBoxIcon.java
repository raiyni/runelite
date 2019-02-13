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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.border.Border;
import net.runelite.client.ui.skin.SkinUtil;

public class CheckBoxIcon implements Icon
{
	private static final BasicStroke STROKE = new BasicStroke(2);

	private JCheckBox cb;

	public CheckBoxIcon(JCheckBox cb)
	{
		this.cb = cb;

	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		ColorScheme scheme = SkinUtil.getColorScheme();
		if (scheme == null)
		{
			return;
		}

		Graphics2D g2 = (Graphics2D) g;
		Color color = scheme.getToolTipBackground();

		if (cb.getModel().isSelected())
		{
			color = color.brighter();
		}

		if (cb.getModel().isRollover())
		{
			color = color.brighter();
		}

		g2.setColor(color);
		g2.fillRect(x + 3, y + 3, getIconWidth() - 5, getIconHeight() - 5);

		Border b = scheme.getBorder();
		if (b != null)
		{
			b.paintBorder(c, g, x + 2, y + 2, getIconWidth() - 4, getIconWidth() - 4);
		}

		GeneralPath path = new GeneralPath();
		path.moveTo(x + 6, y + 10);
		path.lineTo(x + 9, y + 13);
		path.lineTo(x + 15, y + 4);

		if (cb.getModel().isSelected())
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(scheme.getLabelForeground());
			g2.setStroke(STROKE);
			g2.draw(path);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
	}

	@Override
	public int getIconWidth()
	{
		return 20;
	}

	@Override
	public int getIconHeight()
	{
		return 20;
	}
}
