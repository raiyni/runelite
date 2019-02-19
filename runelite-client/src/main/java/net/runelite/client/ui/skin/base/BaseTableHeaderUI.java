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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import net.runelite.client.ui.skin.SkinUtil;

public class BaseTableHeaderUI extends BasicTableHeaderUI
{
	public static ComponentUI createUI(JComponent comp)
	{
		return new BaseTableHeaderUI();
	}

	@Override
	protected void installDefaults()
	{
		ColorScheme scheme = SkinUtil.getColorScheme();
		if (scheme == null)
		{
			super.installDefaults();
			return;
		}

		final TableCellRenderer tcrOs = header.getDefaultRenderer();
		header.setDefaultRenderer((table, value, isSelected, hasFocus, row, column) ->
		{
			JLabel lbl = (JLabel) tcrOs.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
			lbl.setBorder(scheme.getBorder());
			lbl.setHorizontalAlignment(SwingConstants.CENTER);

			lbl.setForeground(scheme.getLabelForeground());
			lbl.setBackground(scheme.getFlatComponentBackground());

			return lbl;
		});
	}
}
