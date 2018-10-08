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
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class PrimaryColorSlider extends BasicSliderUI
{
	private static float[] fracs = {0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f};
	private BufferedImage gradient = new BufferedImage(9, 140, BufferedImage.TYPE_INT_RGB);


	public PrimaryColorSlider(JSlider b)
	{
		super(b);
		Graphics2D g2d = (Graphics2D) gradient.getGraphics();
		Point2D start = new Point2D.Float(0, 0);
		Point2D end = new Point2D.Float(9, 140);
		Color[] colors = {Color.magenta, Color.blue, Color.cyan,
			Color.green, Color.yellow, Color.red};
		g2d.setPaint(new LinearGradientPaint(start, end, fracs, colors));
		g2d.fillRect(0, 0, 9, 140);
		g2d.dispose();
	}

	public Color getColorForValue(int value)
	{
		return new Color(gradient.getRGB(4, 140 - value));
	}

	@Override
	public void paint(Graphics g, JComponent c)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g, c);
	}

	@Override
	protected Dimension getThumbSize()
	{
		return new Dimension(5, 10);
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
				int value = valueForYPosition((int) point.getY());
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
	public void paintTrack(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		Rectangle t = trackRect;
		g2d.drawImage(gradient, null, t.x, t.y);
	}

	@Override
	protected void calculateThumbLocation()
	{
		super.calculateThumbLocation();
		thumbRect.x = trackRect.x - 9;
	}

	@Override
	public void paintThumb(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		int x1 = thumbRect.x;
		int x2 = thumbRect.x + thumbRect.width;
		int width = thumbRect.width;
		int topY = thumbRect.y + thumbRect.height;

		Polygon p = new Polygon();
		p.addPoint(x1, topY);
		p.addPoint(x1, thumbRect.y);
		p.addPoint(x2, thumbRect.y + thumbRect.height / 2);
		g2d.setPaint(Color.white);
		g2d.fillPolygon(p);
	}
}
