/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
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
package net.runelite.client.ui.overlay.components;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
public class ImageComponent implements LayoutableRenderableEntity
{
	private final BufferedImage image;
	private Point preferredLocation = new Point();
	private Dimension preferredSize = new Dimension(0, 0);

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (image == null)
		{
			return null;
		}

		if (preferredSize.width <= 0 || preferredSize.height <= 0 || (preferredSize.width == image.getWidth() && preferredSize.height == image.getHeight()))
		{
			graphics.drawImage(image, preferredLocation.x, preferredLocation.y, null);
			return new Dimension(image.getWidth(), image.getHeight());
		}
		else
		{
			BufferedImage img = new BufferedImage(preferredSize.width, preferredSize.height, image.getType());
			Graphics2D g = img.createGraphics();
			try
			{
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g.drawImage(image, 0, 0, preferredSize.width, preferredSize.height, null);
				graphics.drawImage(img, preferredLocation.x, preferredLocation.y, null);
			}
			finally
			{
				g.dispose();
			}
			return new Dimension(preferredSize.width, preferredSize.height);
		}
	}

	@Override
	public void setPreferredSize(Dimension dimension)
	{
		this.preferredSize = dimension;
	}
}