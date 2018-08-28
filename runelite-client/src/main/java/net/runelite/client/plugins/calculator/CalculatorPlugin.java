package net.runelite.client.plugins.calculator;


import com.google.common.eventbus.Subscribe;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@PluginDescriptor(
	name = "Calculator",
	description = "Calculator",
	tags = {"calculator"}
)
@Slf4j
public class CalculatorPlugin extends Plugin
{
	@Inject
	private ClientToolbar clientToolbar;

	private CalculatorPanel panel;
	private NavigationButton navButton;

	@Override
	protected void startUp()
	{
		panel = injector.getInstance(CalculatorPanel.class);
		panel.init();

		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "info_icon.png");

		navButton = NavigationButton.builder()
			.tooltip("Notes")
			.icon(icon)
			.priority(7)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
	}
}
