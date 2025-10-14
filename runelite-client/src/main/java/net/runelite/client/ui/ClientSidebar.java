/*
 * Copyright (c) 2025, Ron Young <https://github.com/raiyni>
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

package net.runelite.client.ui;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.BiConsumer;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JTabbedPane;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientSidebar extends JTabbedPane
{
	private static final int ANTI_DRAG = 250;

	private int dragIndex = -1;
	private int dragOverIndex = -1;
	private Point dragLocation;
	private long lastPress;

	@Setter
	private boolean reorderEnabled;

	private boolean shouldChange;

	@Setter
	private BiConsumer<Integer, Integer> onIconDrag;

	ClientSidebar()
	{
		super(JTabbedPane.RIGHT);

		setBackground(ColorScheme.DARKER_GRAY_COLOR);
		setOpaque(true);
		putClientProperty(FlatClientProperties.STYLE, "tabInsets: 2,5,2,5; variableSize: true; deselectable: true; tabHeight: 26");
		setSelectedIndex(-1);
		setModel(new DefaultSingleSelectionModel()
		{
			@Override
			public void setSelectedIndex(int index)
			{
				if (shouldChange || !reorderEnabled)
				{
					super.setSelectedIndex(index);
				}
			}
		});

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (reorderEnabled && e.getButton() == MouseEvent.BUTTON1)
				{
					dragIndex = indexAtLocation(e.getX(), e.getY());
					lastPress = e.getWhen();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (reorderEnabled && e.getButton() == MouseEvent.BUTTON1)
				{
					if (dragOverIndex == -1)
					{
						forceSelectedIndex(dragIndex != getSelectedIndex() ? dragIndex : -1);
					}
					else
					{
						if (dragIndex != dragOverIndex)
						{
							onIconDrag.accept(dragIndex, dragOverIndex);
						}
					}
					resetDrag();
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (dragIndex == -1 || !reorderEnabled)
				{
					return;
				}

				var when = e.getWhen();
				if (when - lastPress <= ANTI_DRAG)
				{
					return;
				}

				int targetIndex = indexAtLocation(e.getX(), e.getY());
				if (targetIndex != -1)
				{
					dragLocation = e.getPoint();
					dragOverIndex = targetIndex;
					repaint();
				}
			}
		});
	}

	public void forceSelectedIndex(int i)
	{
		shouldChange = true;
		super.setSelectedIndex(i);
		shouldChange = false;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (dragOverIndex > -1 && dragLocation != null)
		{
			var dragIcon = this.getIconAt(dragIndex);
			dragIcon.paintIcon(this, g, dragLocation.x, dragLocation.y);

			var dragBounds = this.getBoundsAt(dragOverIndex);

			var g2d = (Graphics2D) g;
			g2d.setColor(ColorScheme.BRAND_ORANGE_TRANSPARENT);
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(dragBounds.x, dragBounds.y, dragBounds.x + dragBounds.width, dragBounds.y);
		}
	}

	public void resetDrag()
	{
		dragIndex = -1;
		dragOverIndex = -1;
		dragLocation = null;
		this.repaint();
	}
}
