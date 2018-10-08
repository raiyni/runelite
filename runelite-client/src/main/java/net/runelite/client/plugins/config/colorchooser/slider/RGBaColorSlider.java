/*
 * Copyright (c) 2018, Ron Young <https://github.com/raiyni>
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

package net.runelite.client.plugins.config.colorchooser.slider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class RGBaColorSlider extends BasicSliderUI
{
	public RGBaColorSlider(JSlider b)
	{
		super(b);
	}

	@Override
	public void calculateTrackRect()
	{
		super.calculateTrackRect();

		trackRect.height = 5;
	}

	@Override
	protected Dimension getThumbSize()
	{
		return new Dimension(4, 9);
	}

	@Override
	protected TrackListener createTrackListener(final JSlider s)
	{
		return this.new TrackListener()
		{
			@Override
			public void mouseClicked(final MouseEvent e)
			{
				Point point = e.getPoint();
				int value = valueForXPosition((int) point.getX());
				s.setValue(value);
			}


			// disable check that will invoke scrollDueToClickInTrack
			@Override
			public boolean shouldScroll(int dir)
			{
				return false;
			}
		};
	}

	@Override
	public void paintThumb(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		int x1 = thumbRect.x;
		int x2 = thumbRect.x + thumbRect.width;
		int topy = thumbRect.y;

		Polygon p = new Polygon();
		p.addPoint(x1, topy);
		p.addPoint(x2, topy);
		p.addPoint(x2, topy + thumbRect.height);
		p.addPoint(x1, topy + (thumbRect.height));

		g2d.setPaint(Color.white);
		g2d.fillPolygon(p);
	}

	@Override
	protected void calculateThumbLocation()
	{
		super.calculateThumbLocation();
		thumbRect.y = trackRect.y;
	}

	@Override
	public void paintTrack(Graphics g)
	{
		Rectangle r = trackRect;
		g.setColor(Color.black);
		g.fillRect(r.x, 10 - trackRect.height / 2, r.width, r.height);
	}
}
