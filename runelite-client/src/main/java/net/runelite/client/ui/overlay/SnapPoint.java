/*
 * Copyright (c) 2023, Ron Young <https://github.com/raiyni>
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

package net.runelite.client.ui.overlay;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SnapPoint extends Overlay
{
	private static final Dimension SNAP_CORNER_SIZE = new Dimension(80, 80);

	@Getter
	private String name;
	private Point location;

	private Rectangle shiftedBounds;

	public SnapPoint(String name, Point location)
	{
		this.setLayer(OverlayLayer.UNDER_WIDGETS);
		this.setPosition(OverlayPosition.DYNAMIC);
		this.setMovable(true);

		this.name = name;
		this.location = location;

		this.setPriority(OverlayPriority.HIGHEST);
		this.setBounds(new Rectangle(SNAP_CORNER_SIZE));
	}

	public Dimension render(Graphics2D graphics, boolean manageMode)
	{
		return render(graphics);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		return SNAP_CORNER_SIZE;
	}

	@Override
	public boolean onDrag(Overlay overlay)
	{
		log.debug("Attaching {} to {}", overlay.getName(), this.getName());
		return false;
	}

	public void reset()
	{
		shiftedBounds = new Rectangle(getBounds());
	}

	public void shiftPoint(Rectangle bounds)
	{
		int sX = shiftedBounds.x, sY = shiftedBounds.y;
		sY = Math.max(sY, bounds.y + bounds.height + 2);

		shiftedBounds.x = sX;
		shiftedBounds.y = sY;
	}
}
