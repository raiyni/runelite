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

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Area;
import javax.swing.border.Border;

public class GradientBorder implements Border
{
	private final Insets margin;
	private final Color innerColor;
	private final Color fadeColor;
	private final Color outterColor;

	public GradientBorder(Color outerColor, Color innerColor, int top, int left, int bottom, int right)
	{
		super();
		this.outterColor = outerColor;
		this.fadeColor = new Color(innerColor.getRed(), innerColor.getGreen(), innerColor.getBlue(), 0);
		this.innerColor = innerColor;
		margin = new Insets(top + 2, left + 2, bottom + 2, right + 2);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		g.setColor(outterColor);
		g.drawRect(x, y, width - 1, height - 1);


		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(x + 1, y + 1, innerColor, x + 1, y + height - 1, fadeColor));

		Area border = new Area(new Rectangle(x + 1, y + 1, width - 2, height - 2));
		border.subtract(new Area(new Rectangle(x + 2, y + 2,
			width - 4, height - 4)));

		g2d.fill(border);
	}

	public Insets getBorderInsets(Component c)
	{
		return margin;
	}

	public boolean isBorderOpaque()
	{
		return true;
	}
}
