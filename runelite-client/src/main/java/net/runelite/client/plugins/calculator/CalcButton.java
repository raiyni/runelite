package net.runelite.client.plugins.calculator;

import java.awt.Insets;
import javax.swing.JButton;
import lombok.Getter;


public class CalcButton extends JButton
{
	@Getter
	private final ButtonType type;

	public CalcButton(String txt, ButtonType t, CalculatorPanel listener)
	{
		type = t;
		setText(txt);
		setMargin(new Insets(0, 0, 0, 0));
		setFocusPainted(false);

		addActionListener(listener);
	}
}
