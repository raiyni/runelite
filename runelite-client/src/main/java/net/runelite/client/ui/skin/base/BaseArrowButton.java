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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import lombok.Getter;
import lombok.Setter;

public class BaseArrowButton extends JButton implements SwingConstants
{
	private Color background;
	private Color foreground;
	private Color focusBackground;
	private Color focusForeground;

	@Getter
	@Setter
	private int direction;

	private static final BasicStroke STROKE = new BasicStroke(2);

	public Dimension getPreferredSize()
	{
		return new Dimension(16, 11);
	}

	/**
	 * Returns the minimum size of the {@code BasicArrowButton}.
	 *
	 * @return the minimum size
	 */
	public Dimension getMinimumSize()
	{
		return new Dimension(10, 10);
	}

	/**
	 * Returns the maximum size of the {@code BasicArrowButton}.
	 *
	 * @return the maximum size
	 */
	public Dimension getMaximumSize()
	{
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public BaseArrowButton(int direction, Color background, Color foreground, Color focusBackground, Color focusForeground)
	{
		super();
		this.direction = direction;
		this.background = background;
		this.foreground = foreground;
		this.focusBackground = focusBackground;
		this.focusForeground = focusForeground;

		setRequestFocusEnabled(false);
		setBorder(null);
		setBackground(null);
		setOpaque(false);
		setFocusPainted(false);
		setSelected(false);

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				setSelected(true);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				setSelected(false);
			}
		});
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		boolean focus = isSelected();
		int w, h, size;

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		w = getSize().width;
		h = getSize().height;

		if (focus)
		{
			g2.setColor(this.focusBackground);
			g2.fillRect(0, 0, w, h);
		}

		paintTriangle(g2, w / 2, h / 2, w / 4, h / 3, 1,
			direction, focus);
	}

	public void paintTriangle(Graphics2D g2, int x, int y, int w, int h, int offset,
		int direction, boolean isEnabled)
	{
		if (isEnabled)
		{
			g2.setColor(this.focusForeground);
		}
		else
		{
			g2.setColor(this.foreground);
		}

		g2.setStroke(STROKE);
		switch (direction)
		{
			case NORTH:
				g2.draw(new Line2D.Float(x - w, y + h - offset, x, y - offset));
				g2.draw(new Line2D.Float(x, y - offset, x + w, y + h - offset));
				break;
			case SOUTH:
				g2.draw(new Line2D.Float(x - w, y - h + offset, x, y + offset));
				g2.draw(new Line2D.Float(x, y + offset, x + w, y - h + offset));
				break;
			case WEST:


				break;
			case EAST:

				break;
		}
	}
}
