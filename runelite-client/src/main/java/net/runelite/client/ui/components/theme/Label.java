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

package net.runelite.client.ui.components.theme;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import sun.swing.SwingUtilities2;

public class Label extends JLabel
{
	private JLabel holder = null;

	public Label(String text)
	{
		super(text);
		setOpaque(false);

		putClientProperty(SwingUtilities2.AA_TEXT_PROPERTY_KEY, null);

		if (text.startsWith("<html>"))
		{
			holder = new JLabel(text);
			holder.putClientProperty(SwingUtilities2.AA_TEXT_PROPERTY_KEY, null);
		}
	}

	public Dimension getPreferredSize()
	{
		if (holder != null)
		{
			return holder.getPreferredSize();
		}

		String text = getText();
		FontMetrics fm = this.getFontMetrics(getFont());

		int w = fm.stringWidth(text);
		int h = fm.getHeight();

		return new Dimension(w, h);
	}

	@Override
	public void setFont(Font font)
	{
		super.setFont(font);

		if (holder != null)
		{
			holder.setFont(font);
		}
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g.create();
		FontMetrics fm = this.getFontMetrics(getFont());

//		installDesktopHints(g2);
		g2.setFont(getFont());

		// Stroke normalization control key=Normalize strokes for consistent rendering,
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
//		g2.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 140);

		// Stroke normalization control key=Default stroke normalization}
		this.setSize(getPreferredSize());

		if (holder == null)
		{
			g2.setColor(getForeground());
			g2.drawString(getText(), 0, fm.getAscent());
			return;
		}

		holder.setSize(holder.getPreferredSize());
		final View v = (View) holder.getClientProperty(BasicHTML.propertyKey);
		if (v != null)
		{
			v.paint(g2, holder.getBounds());
		}
		g2.dispose();
	}

}
