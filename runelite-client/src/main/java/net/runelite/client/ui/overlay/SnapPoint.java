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
import java.awt.image.BufferedImage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

@Data
@Slf4j
public class SnapPoint extends Overlay
{
	private static final Dimension SNAP_CORNER_SIZE = new Dimension(80, 80);
	private static final int PADDING = 2;

	enum Direction
	{
		UP,
		RIGHT,
		DOWN,
		LEFT;
	}

	@AllArgsConstructor
	@Getter
	enum Anchor
	{
//		TOP_LEFT(PADDING, PADDING, List.of(Direction.DOWN, Direction.RIGHT)),
//		TOP_MIDDLE(SNAP_CORNER_SIZE.width / 2, PADDING, List.of(Direction.DOWN)),
//		TOP_RIGHT(SNAP_CORNER_SIZE.width - PADDING, PADDING, List.of(Direction.DOWN, Direction.LEFT)),
//		MIDDLE_LEFT(PADDING, SNAP_CORNER_SIZE.height / 2),
//		MIDDLE(SNAP_CORNER_SIZE.width / 2, SNAP_CORNER_SIZE.height / 2),
//		MIDDLE_RIGHT(SNAP_CORNER_SIZE.width - PADDING, SNAP_CORNER_SIZE.height / 2),
//		BOTTOM_LEFT(PADDING, SNAP_CORNER_SIZE.height - PADDING, List.of(Direction.UP, Direction.RIGHT)),
//		BOTTOM_MIDDLE(SNAP_CORNER_SIZE.width / 2, SNAP_CORNER_SIZE.height - PADDING, List.of(Direction.UP)),
//		BOTTOM_RIGHT(SNAP_CORNER_SIZE.width - PADDING, SNAP_CORNER_SIZE.height - PADDING, List.of(Direction.UP, Direction.LEFT));


		LEFT(0, 0, List.of(Direction.DOWN, Direction.UP, Direction.RIGHT)),
		MIDDLE(0, 0, List.of(Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT)),
		RIGHT(0, 0, List.of(Direction.LEFT, Direction.UP, Direction.DOWN)),
		;

		private final int x;
		private final int y;
		private final List<Direction> directions;

		boolean validDirection(Direction d)
		{
			return directions.contains(d);
		}

		Direction nextDirection(Direction d)
		{
			int idx = directions.indexOf(d);
			return directions.get((idx + 1) % directions.size());
		}
	}

	private static final BufferedImage ARROW_RIGHT = ImageUtil.resizeImage(ImageUtil.loadImageResource(SnapPoint.class, "/util/arrow_right.png"), 36, 36);
	private static final BufferedImage ARROW_LEFT = ImageUtil.resizeImage(ImageUtil.flipImage(ImageUtil.loadImageResource(SnapPoint.class, "/util/arrow_right.png"), true, false), 36, 36);
	private static final BufferedImage ARROW_UP = ImageUtil.resizeImage(ImageUtil.rotateImage(ImageUtil.loadImageResource(SnapPoint.class, "/util/arrow_right.png"), 3 * Math.PI / 2), 36, 36);
	private static final BufferedImage ARROW_DOWN = ImageUtil.resizeImage(ImageUtil.rotateImage(ImageUtil.loadImageResource(SnapPoint.class, "/util/arrow_right.png"), Math.PI / 2), 36, 36);

	private final Client client;
	private final String name;
	private final Point location;

	private Rectangle shiftedBounds;

	private Direction direction = Direction.DOWN;
	private Anchor anchor = Anchor.LEFT;

	private boolean isManaging;

	public SnapPoint(Client client, String name, Point location)
	{
		this.setLayer(OverlayLayer.ABOVE_WIDGETS);
		this.setPosition(OverlayPosition.DYNAMIC);
		this.setMovable(true);

		this.name = name;
		this.location = location;
		this.client = client;

		this.setPriority(OverlayPriority.LOWEST);
		this.setBounds(new Rectangle(SNAP_CORNER_SIZE));

		addAnchorEntries();
		addDirectionEntries();
	}

	private void addDirectionEntries()
	{
		getMenuEntries().removeIf(e -> e.getParent() != null && e.getParent().getOption().equals("Set Direction"));
		removeMenuEntry(MenuAction.RUNELITE_SUBMENU, "Set Direction", "");

		var parent = addMenuEntry(MenuAction.RUNELITE_SUBMENU, "Set Direction", "");
		for (Direction d : anchor.directions)
		{
			addMenuEntry(MenuAction.RUNELITE_OVERLAY, d.toString(), direction == d ? "*" : "", e -> changeDirection(e.getOption())).setParent(parent);
		}
	}

	private void addAnchorEntries()
	{
		getMenuEntries().removeIf(e -> e.getParent() != null && e.getParent().getOption().equals("Set Anchor"));
		removeMenuEntry(MenuAction.RUNELITE_SUBMENU, "Set Anchor", "");

		var parent = addMenuEntry(MenuAction.RUNELITE_SUBMENU, "Set Anchor", "");
		for (Anchor a : Anchor.values())
		{
			addMenuEntry(MenuAction.RUNELITE_OVERLAY, a.toString(), anchor == a ? "*" : "", e -> changeAnchor(e.getOption())).setParent(parent);
		}
	}

	private void changeAnchor(String value)
	{
		value = Text.removeTags(value);
		log.debug("{}: changing anchor to {}", getName(), value);
		anchor = Anchor.valueOf(value);

		addAnchorEntries();
		changeDirection(anchor.getDirections().get(0).toString());
	}

	private void changeDirection(String value)
	{
		value = Text.removeTags(value);
		log.debug("{}: changing direction to {}", getName(), value);
		direction = Direction.valueOf(value);

		addDirectionEntries();
	}


	public Dimension render(Graphics2D graphics, boolean manageMode)
	{
		return render(graphics);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (isManaging)
		{
			BufferedImage arrow = getArrow();
			int x = 0;
			int y = SNAP_CORNER_SIZE.height / 2 - arrow.getHeight() / 2;

			switch (anchor)
			{
				case LEFT:
					x = PADDING;
					break;
				case MIDDLE:
					x = SNAP_CORNER_SIZE.width / 2 - arrow.getWidth() / 2;
					break;
				case RIGHT:
					x = SNAP_CORNER_SIZE.width - arrow.getWidth() - PADDING;
					break;
			}

			graphics.drawImage(arrow, x, y, null);
		}

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

	public void shiftPoint(Rectangle bounds, int padding)
	{
		int sX = shiftedBounds.x, sY = shiftedBounds.y;

		switch (direction)
		{
			case DOWN:
				sY = Math.max(sY, bounds.y + bounds.height + padding);
				break;
			case UP:
				sY = Math.min(sY, sY - bounds.height - padding);
				break;
			case RIGHT:
				sX = Math.max(sX, bounds.x + bounds.width + padding);
				break;
			case LEFT:
				sX = Math.min(sX, bounds.x - SNAP_CORNER_SIZE.width - padding);
				break;
		}


		shiftedBounds.x = sX;
		shiftedBounds.y = sY;
	}

	public Point getTranslation(Dimension dimension)
	{
		final java.awt.Point result = new java.awt.Point();

		switch (anchor)
		{
			case LEFT:
				switch (direction)
				{
					case RIGHT:
					case DOWN:
						break;
					case UP:
						result.y = SNAP_CORNER_SIZE.height - PADDING;
						break;
				}
				break;
			case RIGHT:
				switch (direction)
				{
					case DOWN:
					case LEFT:
						result.x = SNAP_CORNER_SIZE.width - PADDING;
						break;
					case UP:
						result.x = SNAP_CORNER_SIZE.width - PADDING;
						result.y = SNAP_CORNER_SIZE.height - PADDING;
						break;
				}
				break;
			case MIDDLE:
				switch (direction)
				{
					case LEFT:
					case UP:
					case RIGHT:
					case DOWN:
						result.x = SNAP_CORNER_SIZE.width / 2;
						result.y = SNAP_CORNER_SIZE.height / 2;
						break;
				}
				break;
		}

//		switch(anchor)
//		{
//			case TOP_LEFT:
//				break;
//			case TOP_MIDDLE:
//				result.x = -dimension.width / 2 + SNAP_CORNER_SIZE.width / 2;
//				result.y = PADDING;
//				break;
//			case TOP_RIGHT:
//				result.x = SNAP_CORNER_SIZE.width - dimension.width;
//				result.y = PADDING;
//				break;
//			case BOTTOM_LEFT:
//				result.y = SNAP_CORNER_SIZE.height - dimension.height;
//				break;
//			case BOTTOM_MIDDLE:
//				result.x = -dimension.width / 2 + SNAP_CORNER_SIZE.width / 2;
//				result.y = SNAP_CORNER_SIZE.height - dimension.height;
//				break;
//			case BOTTOM_RIGHT:
//				result.x = SNAP_CORNER_SIZE.width - dimension.width;
//				result.y = SNAP_CORNER_SIZE.height - dimension.height;
//				break;
//
//			default:
//				break;
//		}

		return result;
	}

	private BufferedImage getArrow()
	{
		switch (direction)
		{
			case DOWN:
				return ARROW_DOWN;
			case RIGHT:
				return ARROW_RIGHT;
			case LEFT:
				return ARROW_LEFT;
			case UP:
				return ARROW_UP;
			default:
				throw new IllegalArgumentException();
		}
	}
}
