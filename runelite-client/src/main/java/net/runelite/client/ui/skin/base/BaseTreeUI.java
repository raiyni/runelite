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
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;
import net.runelite.client.ui.skin.SkinUtil;

public class BaseTreeUI extends BasicTreeUI
{
	public static ComponentUI createUI(JComponent comp)
	{
		return new BaseTreeUI();
	}

	private static class NodeIcon implements Icon
	{
		private ColorScheme scheme;
		private boolean collapsed;
		private Border border;

		private NodeIcon(ColorScheme scheme, boolean collapsed)
		{
			this.scheme = scheme;
			this.collapsed = collapsed;
			this.border = new LineBorder(scheme.getBorderOutline());
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			g.setColor(scheme.getFlatComponentBackground());
			g.fillRect(x, y, getIconWidth(), getIconHeight());

			Border b = scheme.getBorder();
			b.paintBorder(c, g, x, y, getIconWidth(), getIconHeight());

			g.setColor(scheme.getLabelForeground());

			g.drawLine(x + 3, getIconHeight() / 2 + y, x + getIconWidth() - 4, getIconHeight() / 2 + y);

			if (collapsed)
			{
				g.drawLine(getIconWidth() / 2 + x, y + 3, getIconWidth() / 2 + x, getIconHeight() + y - 4);
			}
		}

		@Override
		public int getIconWidth()
		{
			return 15;
		}

		@Override
		public int getIconHeight()
		{
			return 15;
		}
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

		setCollapsedIcon(new NodeIcon(scheme, true));
		setExpandedIcon(new NodeIcon(scheme, false));
		tree.setRowHeight(19);
//		tree.setCellRenderer((TreeCellRenderer) (tree, value, selected, expanded, leaf, row, hasFocus) -> {
//			JLabel tcr = (JLabel) createDefaultCellRenderer();
//			tcr.setForeground(scheme.getLabelForeground());
//			tcr.setOpaque(false);
//			return tcr;
//		});
	}
}
