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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.runelite.client.ui.ColorScheme;

public class SiderbarButton extends JButton
{
	private static final Color BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
	private static final Color ACTIVE_COLOR = ColorScheme.DARKER_GRAY_HOVER_COLOR;
	private static final Color HOVER_COLOR = ColorScheme.MEDIUM_GRAY_COLOR;

	private static final int HEIGHT = 25;
	private static final int WIDTH = 29;
	private static final int TRANSITION_TIME = 250;

	private boolean hovered;

	private long transition;

	private final BufferedImage img;

	public SiderbarButton(final BufferedImage scaledImage)
	{
		setFocusable(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setOpaque(true);

		setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(ColorScheme.SCROLL_TRACK_COLOR, 1),
			BorderFactory.createEmptyBorder(4, 5, 4, 6)
		));
		setSize(scaledImage.getWidth(), scaledImage.getHeight());

		setMargin(new Insets(0, 0, 0, 0));

		setIcon(new ImageIcon(scaledImage));
		img = scaledImage;
		setBackground(null);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (getModel().isRollover() || isSelected())
		{
			g.setColor(getModel().isRollover() ? HOVER_COLOR : ACTIVE_COLOR);
			g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);

			g.setColor(ColorScheme.SCROLL_TRACK_COLOR);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}

		g.drawImage(img, (getWidth() - img.getWidth()) / 2, (getHeight() - img.getHeight()) / 2, null);
	}
}
