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
package net.runelite.client.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatNativeWindowBorder;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.applet.Applet;
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.desktop.QuitStrategy;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.TreeSet;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.HyperlinkEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.RuneLiteProperties;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.ExpandResizeType;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.config.WarningOnExit;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ClientShutdown;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseAdapter;
import net.runelite.client.input.MouseListener;
import net.runelite.client.input.MouseManager;
import net.runelite.client.ui.laf.RuneLiteLAF;
import net.runelite.client.util.HotkeyListener;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;
import net.runelite.client.util.OSType;
import net.runelite.client.util.OSXUtil;
import net.runelite.client.util.SwingUtil;
import net.runelite.client.util.WinUtil;

@Slf4j
@Singleton
public class ClientUI
{
	private static final String CONFIG_GROUP = "runelite";
	private static final String CONFIG_CLIENT_BOUNDS = "clientBounds";
	private static final String CONFIG_CLIENT_MAXIMIZED = "clientMaximized";
	private static final String CONFIG_CLIENT_SIDEBAR_CLOSED = "clientSidebarClosed";
	public static final BufferedImage ICON = ImageUtil.loadImageResource(ClientUI.class, "/runelite.png");

	@Getter
	private TrayIcon trayIcon;

	private final RuneLiteConfig config;
	private final KeyManager keyManager;
	private final MouseManager mouseManager;
	private final Applet client;
	private final ConfigManager configManager;
	private final Provider<ClientThread> clientThreadProvider;
	private final EventBus eventBus;
	private final boolean safeMode;
	private final String title;

	private final Rectangle sidebarButtonPosition = new Rectangle();
	private BufferedImage sidebarOpenIcon;
	private BufferedImage sidebarClosedIcon;

	private JTabbedPane sidebar;
	private final TreeSet<NavigationButton> sidebarEntries = new TreeSet<>(NavigationButton.COMPARATOR);
	private NavigationButton selectedTab;
	private NavigationButton lastSelectedTab;

	private ClientToolbarPanel toolbarPanel;
	private boolean withTitleBar;

	private ContainableFrame frame;
	private JPanel content;
	private Dimension lastClientSize;
	private Cursor defaultCursor;

	@Inject(optional = true)
	@Named("minMemoryLimit")
	private int minMemoryLimit = 400;

	@Inject(optional = true)
	@Named("recommendedMemoryLimit")
	private int recommendedMemoryLimit = 512;

	@Inject
	private ClientUI(
		RuneLiteConfig config,
		KeyManager keyManager,
		MouseManager mouseManager,
		@Nullable Applet client,
		ConfigManager configManager,
		Provider<ClientThread> clientThreadProvider,
		EventBus eventBus,
		@Named("safeMode") boolean safeMode,
		@Named("runelite.title") String title
	)
	{
		this.config = config;
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		this.client = client;
		this.configManager = configManager;
		this.clientThreadProvider = clientThreadProvider;
		this.eventBus = eventBus;
		this.safeMode = safeMode;
		this.title = title + (safeMode ? " (safe mode)" : "");
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals(CONFIG_GROUP) ||
			event.getKey().equals(CONFIG_CLIENT_MAXIMIZED) ||
			event.getKey().equals(CONFIG_CLIENT_BOUNDS))
		{
			return;
		}

		SwingUtilities.invokeLater(() -> updateFrameConfig(event.getKey().equals("lockWindowSize")));
	}

	void addNavigation(NavigationButton navBtn)
	{
		if (navBtn.getPanel() == null)
		{
			toolbarPanel.add(navBtn);
			return;
		}

		if (sidebarEntries.contains(navBtn))
		{
			return;
		}

		if (!sidebarEntries.add(navBtn))
		{
			return;
		}

		final int TAB_SIZE = 16;
		Icon icon = new ImageIcon(ImageUtil.resizeImage(navBtn.getIcon(), TAB_SIZE, TAB_SIZE));

		sidebar.insertTab(null, icon, navBtn.getPanel().getWrappedPanel(), navBtn.getTooltip(),
			sidebarEntries.headSet(navBtn).size());
		sidebar.getModel().clearSelection();
	}

	void removeNavigation(NavigationButton navBtn)
	{
		if (navBtn.getPanel() == null)
		{
			toolbarPanel.remove(navBtn);
		}

		sidebarEntries.remove(navBtn);
		sidebar.remove(navBtn.getPanel());
	}

	@Subscribe
	private void onGameStateChanged(final GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN || !(client instanceof Client) || !config.usernameInTitle())
		{
			return;
		}

		final Client client = (Client) this.client;
		final ClientThread clientThread = clientThreadProvider.get();

		// Keep scheduling event until we get our name
		clientThread.invokeLater(() ->
		{
			if (client.getGameState() != GameState.LOGGED_IN)
			{
				return true;
			}

			final Player player = client.getLocalPlayer();

			if (player == null)
			{
				return false;
			}

			final String name = player.getName();

			if (Strings.isNullOrEmpty(name))
			{
				return false;
			}

			frame.setTitle(title + " - " + name);
			return true;
		});
	}

	/**
	 * Initialize UI.
	 */
	public void init() throws Exception
	{
		SwingUtilities.invokeAndWait(() ->
		{
			// Set some sensible swing defaults
			setupDefaults();

			RuneLiteLAF.setup();

			// Create main window
			frame = new ContainableFrame();

			// Try to enable fullscreen on OSX
			OSXUtil.tryEnableFullscreen(frame);

			frame.setTitle(title);
			frame.setIconImage(ICON);
			frame.setLocationRelativeTo(frame.getOwner());
			frame.setResizable(true);

			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			if (OSType.getOSType() == OSType.MacOS)
			{
				// Change the default quit strategy to CLOSE_ALL_WINDOWS so that ctrl+q
				// triggers the listener below instead of exiting.
				Desktop.getDesktop()
					.setQuitStrategy(QuitStrategy.CLOSE_ALL_WINDOWS);
			}
			frame.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent event)
				{
					int result = JOptionPane.OK_OPTION;

					if (showWarningOnExit())
					{
						try
						{
							result = JOptionPane.showConfirmDialog(
								frame,
								"Are you sure you want to exit?", "Exit",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						}
						catch (Exception e)
						{
							log.warn("Unexpected exception occurred while check for confirm required", e);
						}
					}

					if (result == JOptionPane.OK_OPTION)
					{
						shutdownClient();
					}
				}
			});

			content = new JPanel();
			Layout layout = new Layout();
			content.setLayout(layout);
			content.add(new ClientPanel(client));

			sidebar = new JTabbedPane(JTabbedPane.RIGHT);
			sidebar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			sidebar.setOpaque(true);
			sidebar.putClientProperty(FlatClientProperties.STYLE, "tabInsets: 2,5,2,5; variableSize: true; deselectable: true");
			sidebar.setSelectedIndex(-1);
			sidebar.addChangeListener(ev ->
			{
				NavigationButton oldSelectedTab = selectedTab;
				NavigationButton newSelectedTab;

				int index = sidebar.getSelectedIndex();
				if (index < 0)
				{
					newSelectedTab = null;
				}
				else
				{
					// maybe just include a map component -> navbtn?
					lastSelectedTab = newSelectedTab = Iterables.get(sidebarEntries, index);
				}

				if (oldSelectedTab == newSelectedTab)
				{
					return;
				}

				selectedTab = newSelectedTab;

				if (sidebar.isVisible())
				{
					if (oldSelectedTab != null)
					{
						SwingUtil.deactivate(oldSelectedTab.getPanel());
					}
					if (newSelectedTab != null)
					{
						SwingUtil.activate(newSelectedTab.getPanel());
					}
				}
			});

			content.add(sidebar);

			toolbarPanel = new ClientToolbarPanel();
			frame.setContentPane(content);

			// Add key listener
			final HotkeyListener sidebarListener = new HotkeyListener(config::sidebarToggleKey)
			{
				@Override
				public void hotkeyPressed()
				{
					toggleSidebar();
				}
			};
			sidebarListener.setEnabledOnLoginScreen(true);
			keyManager.registerKeyListener(sidebarListener);

			final HotkeyListener pluginPanelListener = new HotkeyListener(config::panelToggleKey)
			{
				@Override
				public void hotkeyPressed()
				{
					togglePluginPanel();
				}
			};
			pluginPanelListener.setEnabledOnLoginScreen(true);
			keyManager.registerKeyListener(pluginPanelListener);

			// Add mouse listener
			final MouseListener mouseListener = new MouseAdapter()
			{
				@Override
				public MouseEvent mousePressed(MouseEvent mouseEvent)
				{
					if (SwingUtilities.isLeftMouseButton(mouseEvent) && sidebarButtonPosition.contains(mouseEvent.getPoint()))
					{
						SwingUtilities.invokeLater(ClientUI.this::toggleSidebar);
						mouseEvent.consume();
					}

					return mouseEvent;
				}
			};
			mouseManager.registerMouseListener(mouseListener);

			// Decorate window with custom chrome and titlebar if needed
			withTitleBar = config.enableCustomChrome();
			toolbarPanel.setIsInTitleBar(withTitleBar);

			if (withTitleBar)
			{
				JMenuBar menuBar = new JMenuBar();
				menuBar.add(Box.createGlue());
				menuBar.add(toolbarPanel);
				frame.setJMenuBar(menuBar);

				JRootPane rp = frame.getRootPane();
				if (FlatNativeWindowBorder.isSupported())
				{
					rp.putClientProperty(FlatClientProperties.USE_WINDOW_DECORATIONS, true);
				}
				/*else if (OSType.getOSType() == OSType.MacOS && SystemInfo.isMacFullWindowContentSupported)
				{
					rp.putClientProperty("apple.awt.fullWindowContent", true);
					rp.putClientProperty("apple.awt.transparentTitleBar", true);
					menuBar.setBorder(new EmptyBorder(3, 70, 3, 10));
				}*/
				else
				{
					frame.setUndecorated(true);
					rp.setWindowDecorationStyle(JRootPane.FRAME);
				}
			}
			else
			{
				sidebar.putClientProperty(
					FlatClientProperties.TABBED_PANE_TRAILING_COMPONENT,
					toolbarPanel.createSidebarPanel());
			}

			// Update config
			updateFrameConfig(false);

			// Create hide sidebar button

			sidebarOpenIcon = ImageUtil.loadImageResource(ClientUI.class, withTitleBar ? "open.png" : "open_rs.png");
			sidebarClosedIcon = ImageUtil.flipImage(sidebarOpenIcon, true, false);

			//toolbarPanel.addComponent(sidebarNavigationButton, sidebarNavigationJButton);

			// Open sidebar if the config closed state is unset
			if (configManager.getConfiguration(CONFIG_GROUP, CONFIG_CLIENT_SIDEBAR_CLOSED, Boolean.class) == Boolean.TRUE)
			{
				closeSidebar();
			}
		});
	}

	public void show()
	{
		logGraphicsEnvironment();

		SwingUtilities.invokeLater(() ->
		{
			// Layout frame
			frame.pack();

			// Create tray icon (needs to be created after frame is packed)
			if (config.enableTrayIcon())
			{
				trayIcon = createTrayIcon(ICON, title, frame);
			}

			// Move frame around (needs to be done after frame is packed)
			if (config.rememberScreenBounds() && !safeMode)
			{
				Rectangle clientBounds = configManager.getConfiguration(
					CONFIG_GROUP, CONFIG_CLIENT_BOUNDS, Rectangle.class);
				if (clientBounds != null)
				{
					frame.setBounds(clientBounds);

					// Check that the bounds are contained inside a valid display
					GraphicsConfiguration gc = findDisplayFromBounds(clientBounds);
					if (gc == null)
					{
						log.info("Reset client position. Client bounds: {}x{}x{}x{}",
							clientBounds.x, clientBounds.y, clientBounds.width, clientBounds.height);
						// Reset the position, but not the size
						frame.setLocationRelativeTo(frame.getOwner());
					}
				}
				else
				{
					frame.setLocationRelativeTo(frame.getOwner());
				}

				if (configManager.getConfiguration(CONFIG_GROUP, CONFIG_CLIENT_MAXIMIZED) != null)
				{
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
			}
			else
			{
				frame.setLocationRelativeTo(frame.getOwner());
			}

			// Show frame
			frame.setVisible(true);
			// On macos setResizable needs to be called after setVisible
			frame.setResizable(!config.lockWindowSize());
			frame.toFront();
			requestFocus();
			log.debug("Showing frame {}", frame);
			frame.revalidateMinimumSize();
		});

		// Show out of date dialog if needed
		if (client != null && !(client instanceof Client))
		{
			SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame,
				"RuneLite has not yet been updated to work with the latest\n"
					+ "game update, it will work with reduced functionality until then.",
				"RuneLite is outdated", INFORMATION_MESSAGE));
		}

		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024L / 1024L);
		if (maxMemory < minMemoryLimit)
		{
			SwingUtilities.invokeLater(() ->
			{
				JEditorPane ep = new JEditorPane("text/html",
					"Your Java memory limit is " + maxMemory + "mb, which is lower than the recommended " + recommendedMemoryLimit + "mb.<br>" +
						"This can cause instability, and it is recommended you remove or increase this limit.<br>" +
						"Join <a href=\"" + RuneLiteProperties.getDiscordInvite() + "\">Discord</a> for assistance."
				);
				ep.addHyperlinkListener(e ->
				{
					if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					{
						LinkBrowser.browse(e.getURL().toString());
					}
				});
				ep.setEditable(false);
				ep.setOpaque(false);
				JOptionPane.showMessageDialog(frame,
					ep, "Max memory limit low", JOptionPane.WARNING_MESSAGE);
			});
		}
	}

	private void logGraphicsEnvironment()
	{
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (GraphicsDevice graphicsDevice : graphicsEnvironment.getScreenDevices())
		{
			GraphicsConfiguration configuration = graphicsDevice.getDefaultConfiguration();
			log.debug("Graphics device {}: bounds {} transform: {}", graphicsDevice, configuration.getBounds(), configuration.getDefaultTransform());
		}
	}

	private GraphicsConfiguration findDisplayFromBounds(final Rectangle bounds)
	{
		GraphicsDevice[] gds = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

		for (GraphicsDevice gd : gds)
		{
			GraphicsConfiguration gc = gd.getDefaultConfiguration();

			final Rectangle displayBounds = gc.getBounds();
			if (displayBounds.contains(bounds))
			{
				return gc;
			}
		}

		return null;
	}

	private boolean showWarningOnExit()
	{
		if (config.warningOnExit() == WarningOnExit.ALWAYS)
		{
			return true;
		}

		if (config.warningOnExit() == WarningOnExit.LOGGED_IN && client instanceof Client)
		{
			return ((Client) client).getGameState() != GameState.LOGIN_SCREEN;
		}

		return false;
	}

	private void shutdownClient()
	{
		saveClientBoundsConfig();
		ClientShutdown csev = new ClientShutdown();
		eventBus.post(csev);
		new Thread(() ->
		{
			csev.waitForAllConsumers(Duration.ofSeconds(10));

			if (client != null)
			{
				// The client can call System.exit when it's done shutting down
				// if it doesn't though, we want to exit anyway, so race it
				int clientShutdownWaitMS;
				if (client instanceof Client)
				{
					((Client) client).stopNow();
					clientShutdownWaitMS = 1000;
				}
				else
				{
					// it will continue rendering for about 4 seconds before attempting shutdown if its vanilla
					client.stop();
					frame.setVisible(false);
					clientShutdownWaitMS = 6000;
				}

				try
				{
					Thread.sleep(clientShutdownWaitMS);
				}
				catch (InterruptedException ignored)
				{
				}
			}
			System.exit(0);
		}, "RuneLite Shutdown").start();
	}

	/**
	 * Paint this component to target graphics
	 * @param graphics the graphics
	 */
	public void paint(final Graphics graphics)
	{
		assert SwingUtilities.isEventDispatchThread() : "paint must be called on EDT";
		frame.paint(graphics);
	}

	/**
	 * Gets component width.
	 * @return the width
	 */
	public int getWidth()
	{
		return frame.getWidth();
	}

	/**
	 * Gets component height.
	 * @return the height
	 */
	public int getHeight()
	{
		return frame.getHeight();
	}

	/**
	 * Returns true if this component has focus.
	 * @return true if component has focus
	 */
	public boolean isFocused()
	{
		return frame.isFocused();
	}

	/**
	 * Request focus on this component and then on client component
	 */
	public void requestFocus()
	{
		switch (OSType.getOSType())
		{
			case MacOS:
				// On OSX Component::requestFocus has no visible effect, so we use our OSX-specific
				// requestUserAttention()
				OSXUtil.requestUserAttention();
				break;
			default:
				frame.requestFocus();
		}

		giveClientFocus();
	}

	/**
	 * Attempt to forcibly bring the client frame to front
	 */
	public void forceFocus()
	{
		switch (OSType.getOSType())
		{
			case MacOS:
				OSXUtil.requestForeground();
				break;
			case Windows:
				WinUtil.requestForeground(frame);
				break;
			default:
				frame.requestFocus();
				break;
		}

		giveClientFocus();
	}

	/**
	 * Returns current cursor set on game container
	 * @return awt cursor
	 */
	public Cursor getCurrentCursor()
	{
		return content.getCursor();
	}

	/**
	 * Returns current custom cursor or default system cursor if cursor is not set
	 * @return awt cursor
	 */
	public Cursor getDefaultCursor()
	{
		return defaultCursor != null ? defaultCursor : Cursor.getDefaultCursor();
	}

	/**
	 * Changes cursor for client window. Requires ${@link ClientUI#init()} to be called first.
	 * FIXME: This is working properly only on Windows, Linux and Mac are displaying cursor incorrectly
	 * @param image cursor image
	 * @param name cursor name
	 */
	public void setCursor(final BufferedImage image, final String name)
	{
		if (content == null)
		{
			return;
		}

		final java.awt.Point hotspot = new java.awt.Point(0, 0);
		final Cursor cursorAwt = Toolkit.getDefaultToolkit().createCustomCursor(image, hotspot, name);
		defaultCursor = cursorAwt;
		setCursor(cursorAwt);
	}

	/**
	 * Changes cursor for client window. Requires ${@link ClientUI#init()} to be called first.
	 * @param cursor awt cursor
	 */
	public void setCursor(final Cursor cursor)
	{
		content.setCursor(cursor);
	}

	/**
	 * Resets client window cursor to default one.
	 * @see ClientUI#setCursor(BufferedImage, String)
	 */
	public void resetCursor()
	{
		if (content == null)
		{
			return;
		}

		defaultCursor = null;
		content.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Get offset of game canvas in game window
	 * @return game canvas offset
	 */
	public Point getCanvasOffset()
	{
		if (client instanceof Client)
		{
			final Canvas canvas = ((Client) client).getCanvas();
			if (canvas != null)
			{
				final java.awt.Point point = SwingUtilities.convertPoint(canvas, 0, 0, frame);
				return new Point(point.x, point.y);
			}
		}

		return new Point(0, 0);
	}

	/**
	 * Paint UI related overlays to target graphics
	 * @param graphics target graphics
	 */
	public void paintOverlays(final Graphics2D graphics)
	{
		if (!(client instanceof Client) || withTitleBar)
		{
			return;
		}

		final Client client = (Client) this.client;
		final int x = client.getRealDimensions().width - sidebarOpenIcon.getWidth() - 5;

		// Offset sidebar button if resizable mode logout is visible
		final Widget logoutButton = client.getWidget(WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_LOGOUT_BUTTON);
		final int y = logoutButton != null && !logoutButton.isHidden() && logoutButton.getParent() != null
			? logoutButton.getHeight() + logoutButton.getRelativeY()
			: 5;

		final BufferedImage image = sidebar.isVisible() ? sidebarClosedIcon : sidebarOpenIcon;

		final Rectangle sidebarButtonRange = new Rectangle(x - 15, 0, image.getWidth() + 25, client.getRealDimensions().height);
		final Point mousePosition = new Point(
			client.getMouseCanvasPosition().getX() + client.getViewportXOffset(),
			client.getMouseCanvasPosition().getY() + client.getViewportYOffset());

		if (sidebarButtonRange.contains(mousePosition.getX(), mousePosition.getY()))
		{
			graphics.drawImage(image, x, y, null);
		}

		// Update button dimensions
		sidebarButtonPosition.setBounds(x, y, image.getWidth(), image.getHeight());
	}

	public GraphicsConfiguration getGraphicsConfiguration()
	{
		return frame.getGraphicsConfiguration();
	}

	private void openSidebar()
	{
		if (sidebar.isVisible())
		{
			return;
		}

		configManager.unsetConfiguration(CONFIG_GROUP, CONFIG_CLIENT_SIDEBAR_CLOSED);
		sidebar.setVisible(true);
		content.revalidate();

		if (selectedTab != null)
		{
			SwingUtil.activate(selectedTab.getPanel());
		}
	}

	private void closeSidebar()
	{
		if (!sidebar.isVisible())
		{
			return;
		}

		configManager.setConfiguration(CONFIG_GROUP, CONFIG_CLIENT_SIDEBAR_CLOSED, true);
		sidebar.setVisible(false);

		if (selectedTab != null)
		{
			SwingUtil.deactivate(selectedTab.getPanel());
		}
	}

	void openPanel(NavigationButton navBtn)
	{
		if (navBtn != null && !sidebarEntries.contains(navBtn))
		{
			return;
		}

		openSidebar();

		int index = navBtn == null ? -1 : sidebarEntries.headSet(navBtn).size();
		sidebar.setSelectedIndex(index);
	}

	private void toggleSidebar()
	{
		if (sidebar.isVisible())
		{
			closeSidebar();
		}
		else
		{
			openSidebar();
		}
	}

	private void togglePluginPanel()
	{
		if (sidebar.getSelectedIndex() < 0)
		{
			openPanel(lastSelectedTab);
		}
		else
		{
			sidebar.setSelectedIndex(-1);
		}
	}

	private void giveClientFocus()
	{
		if (client instanceof Client)
		{
			final Canvas c = ((Client) client).getCanvas();
			if (c != null)
			{
				c.requestFocusInWindow();
			}
		}
		else if (client != null)
		{
			client.requestFocusInWindow();
		}
	}

	private void updateFrameConfig(boolean updateResizable)
	{
		if (frame == null)
		{
			return;
		}

		// Update window opacity if the frame is undecorated, translucency capable and not fullscreen
		if (frame.isUndecorated() &&
			frame.getGraphicsConfiguration().isTranslucencyCapable() &&
			frame.getGraphicsConfiguration().getDevice().getFullScreenWindow() == null)
		{
			frame.setOpacity(((float) config.windowOpacity()) / 100.0f);
		}

		if (config.usernameInTitle() && (client instanceof Client))
		{
			final Player player = ((Client) client).getLocalPlayer();

			if (player != null && player.getName() != null)
			{
				frame.setTitle(title + " - " + player.getName());
			}
		}
		else
		{
			frame.setTitle(title);
		}

		if (frame.isAlwaysOnTopSupported())
		{
			frame.setAlwaysOnTop(config.gameAlwaysOnTop());
		}

		if (updateResizable)
		{
			frame.setResizable(!config.lockWindowSize());
		}

		frame.setExpandResizeType(config.automaticResizeType());

		ContainableFrame.Mode containMode = config.containInScreen();
		if (containMode == ContainableFrame.Mode.ALWAYS && !withTitleBar)
		{
			// When native window decorations are enabled we don't have a way to receive window move events
			// so we can't contain to screen always.
			containMode = ContainableFrame.Mode.RESIZING;
		}
		frame.setContainedInScreen(containMode);

		if (!config.rememberScreenBounds())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, CONFIG_CLIENT_MAXIMIZED);
			configManager.unsetConfiguration(CONFIG_GROUP, CONFIG_CLIENT_BOUNDS);
		}

		if (client == null)
		{
			return;
		}

		// The upper bounds are defined by the applet's max size
		// The lower bounds are defined by the client's fixed size
		int width = Math.max(Math.min(config.gameSize().width, 7680), Constants.GAME_FIXED_WIDTH);
		int height = Math.max(Math.min(config.gameSize().height, 2160), Constants.GAME_FIXED_HEIGHT);
		final Dimension size = new Dimension(width, height);

		if (!size.equals(lastClientSize))
		{
			lastClientSize = size;
			client.setSize(size);
			client.setPreferredSize(size);
			client.getParent().setPreferredSize(size);
			client.getParent().setSize(size);

			if (frame.isVisible())
			{
				frame.pack();
			}
		}
	}

	private void saveClientBoundsConfig()
	{
		final Rectangle bounds = frame.getBounds();
		if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0)
		{
			configManager.setConfiguration(CONFIG_GROUP, CONFIG_CLIENT_BOUNDS, bounds);
			configManager.setConfiguration(CONFIG_GROUP, CONFIG_CLIENT_MAXIMIZED, true);
		}
		else
		{
			if (config.automaticResizeType() == ExpandResizeType.KEEP_GAME_SIZE)
			{
				if (sidebar.isVisible() && sidebar.getSelectedComponent() != null)
				{
					// Try to contract plugin panel
					bounds.width -= sidebar.getSelectedComponent().getWidth();
				}
			}

			configManager.unsetConfiguration(CONFIG_GROUP, CONFIG_CLIENT_MAXIMIZED);
			configManager.setConfiguration(CONFIG_GROUP, CONFIG_CLIENT_BOUNDS, bounds);
		}
	}

	private static void setupDefaults()
	{
		// Force heavy-weight popups/tooltips.
		// Prevents them from being obscured by the game applet.
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setInitialDelay(300);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		// Do not fill in background on repaint. Reduces flickering when
		// the applet is resized.
		System.setProperty("sun.awt.noerasebackground", "true");
	}

	@Nullable
	private static TrayIcon createTrayIcon(@Nonnull final Image icon, @Nonnull final String title, @Nonnull final Frame frame)
	{
		if (!SystemTray.isSupported())
		{
			return null;
		}

		final SystemTray systemTray = SystemTray.getSystemTray();
		final TrayIcon trayIcon = new TrayIcon(icon, title);
		trayIcon.setImageAutoSize(true);

		try
		{
			systemTray.add(trayIcon);
		}
		catch (AWTException ex)
		{
			log.debug("Unable to add system tray icon", ex);
			return trayIcon;
		}

		// Bring to front when tray icon is clicked
		trayIcon.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (OSType.getOSType() == OSType.MacOS && !frame.isFocused())
				{
					// On macOS, frame.setVisible(true) only restores focus when the visibility was previously false.
					// The frame's visibility is not set to false when the window loses focus, so we set it manually.
					// Additionally, in order to bring the window to the foreground,
					// frame.setVisible(true) calls CPlatformWindow::nativePushNSWindowToFront.
					// However, this native method is not called with activateIgnoringOtherApps:YES,
					// so any other active window will prevent our window from being brought to the front.
					// To work around this, we use our macOS-specific requestForeground().
					frame.setVisible(false);
					OSXUtil.requestForeground();
				}
				frame.setVisible(true);
				frame.setState(Frame.NORMAL); // Restore
			}
		});

		return trayIcon;
	}

	class Layout implements LayoutManager2
	{
		private static final int INDEX_CLIENT = 0;
		private Dimension previousParentSize = null;

		@Override
		public void addLayoutComponent(String name, Component comp)
		{
		}

		@Override
		public void removeLayoutComponent(Component comp)
		{
		}

		@Override
		public void addLayoutComponent(Component comp, Object constraints)
		{
		}

		@Override
		public Dimension preferredLayoutSize(Container content)
		{
			synchronized (content.getTreeLock())
			{
				return size(content, Component::getPreferredSize);
			}
		}

		@Override
		public Dimension minimumLayoutSize(Container content)
		{
			synchronized (content.getTreeLock())
			{
				return size(content, Component::getMinimumSize);
			}
		}

		private Dimension max(Dimension a, Dimension b)
		{
			return new Dimension(
				Math.max(a.width, b.width),
				Math.max(a.height, b.height));
		}

		@Override
		public void layoutContainer(Container content)
		{
			// calculate frame size delta
			// if dellta != 0 adjust it out of client
			// min/max all bounds
			// if keep game size
			//    adjust minmax delta out of client
			//    minmax again
			// apply frame size via contract/expand

			Dimension minimumSize = minimumLayoutSize(content);
			frame.setMinimumSize(minimumSize);

			Dimension windowSize = max(minimumSize, content.getSize());

			Component client = content.getComponent(INDEX_CLIENT);

			if ((frame.getExtendedState() & Frame.MAXIMIZED_HORIZ) == 0
				&& config.automaticResizeType() == ExpandResizeType.KEEP_GAME_SIZE)
			{
				if (previousParentSize != null)
				{
					client.setSize(client.getWidth() + (content.getWidth() - previousParentSize.width), windowSize.height);
				}

				int x = 0;
				for (int i = 0; i < content.getComponentCount(); i++)
				{
					Component child = content.getComponent(i);
					int wantedWidth = wantedWidth(i, child);
					child.setBounds(x, 0, wantedWidth, windowSize.height);
					x += wantedWidth;
				}

				applyOwnSize(content, new Dimension(x, windowSize.height));
			}
			else
			{
				int x = windowSize.width;
				for (int i = content.getComponentCount() - 1; i >= 1; i--)
				{
					Component child = content.getComponent(i);
					int wantedWidth = wantedWidth(i, child);
					child.setBounds(x -= wantedWidth, 0, wantedWidth, windowSize.height);
				}

				client.setBounds(0, 0, x, windowSize.height);

				applyOwnSize(content, windowSize);
			}
		}

		private int wantedWidth(int index, Component child)
		{
			if (!child.isVisible())
			{
				return 0;
			}

			if (index == INDEX_CLIENT)
			{
				return Math.max(child.getWidth(), child.getMinimumSize().width);
			}

			return child.getPreferredSize().width;
		}

		private void applyOwnSize(Container content, Dimension size)
		{
			log.debug("{}", size);
			content.setSize(size);
			previousParentSize = size;
			frame.setBoundsContained(size);
		}

		private Dimension size(Container content, Function<Component, Dimension> sizer)
		{
			Dimension out = new Dimension(0, 0);
			for (int i = 0; i < content.getComponentCount(); i++)
			{
				Component child = content.getComponent(i);
				if (child.isVisible())
				{
					Dimension dim = sizer.apply(child);
					out.width += dim.width;
					out.height = Math.max(out.height, dim.height);
				}
			}

			Insets is = content.getInsets();
			out.width += is.left + is.right;
			out.height += is.top + is.bottom;

			return out;
		}

		@Override
		public Dimension maximumLayoutSize(Container content)
		{
			return size(content, Component::getMaximumSize);
		}

		@Override
		public float getLayoutAlignmentX(Container target)
		{
			return 0;
		}

		@Override
		public float getLayoutAlignmentY(Container target)
		{
			return 0;
		}

		@Override
		public void invalidateLayout(Container target)
		{
		}
	}
}
