/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
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
package net.runelite.client.ui.overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;

@Getter
@Setter
public abstract class Overlay implements LayoutableRenderableEntity
{
	private static final Color MOVING_OVERLAY_RESIZING_COLOR = new Color(255, 0, 255, 200);
	private static final Color MOVING_OVERLAY_ACTIVE_COLOR = new Color(255, 255, 0, 200);

	static final Color MOVING_OVERLAY_COLOR = new Color(255, 255, 0, 100);

	@Nullable
	private final Plugin plugin;
	private Point preferredLocation;
	private Dimension preferredSize;
	private OverlayPosition preferredPosition;
	private Rectangle bounds = new Rectangle();
	private OverlayPosition position = OverlayPosition.TOP_LEFT;
	private OverlayPriority priority = OverlayPriority.NONE;
	private OverlayLayer layer = OverlayLayer.UNDER_WIDGETS;
	private final List<OverlayMenuEntry> menuEntries = new ArrayList<>();
	private boolean resizable;
	private boolean resettable = true;

	@Setter(AccessLevel.PACKAGE)
	protected Overlay dragTarget;

	@Setter(AccessLevel.PROTECTED)
	protected boolean dragTargetable;

	protected Overlay()
	{
		plugin = null;
	}

	protected Overlay(Plugin plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * Overlay name, used for saving the overlay, needs to be unique
	 *
	 * @return overlay name
	 */
	public String getName()
	{
		return this.getClass().getSimpleName();
	}

	public void onMouseOver()
	{
	}

	public boolean onMouseReleased()
	{
		return false;
	}

	protected void renderBounds(Graphics2D graphics, Overlay managedOverlay, boolean inOverlayResizingMode, boolean inOverlayDraggingMode)
	{
		if (inOverlayResizingMode && managedOverlay == this)
		{
			graphics.setColor(MOVING_OVERLAY_RESIZING_COLOR);
		}
		else if (inOverlayDraggingMode && managedOverlay == this)
		{
			graphics.setColor(MOVING_OVERLAY_ACTIVE_COLOR);
		}
		else if (inOverlayDraggingMode && dragTargetable && managedOverlay.isDragTargetable()
			&& managedOverlay.getBounds().intersects(bounds))
		{
			graphics.setColor(Color.RED);
			managedOverlay.setDragTarget(this);
		}
		else
		{
			graphics.setColor(MOVING_OVERLAY_COLOR);
		}

		graphics.draw(bounds);
	}
}
