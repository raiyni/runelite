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

package net.runelite.client.plugins.config.colorchooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import net.runelite.client.plugins.config.colorchooser.slider.PrimaryColorSlider;
import net.runelite.client.plugins.config.colorchooser.slider.RGBaColorSlider;
import org.apache.commons.lang3.math.NumberUtils;
import org.pushingpixels.substance.api.SubstanceCortex;
import org.pushingpixels.substance.api.colorscheme.SubstanceColorScheme;
import org.pushingpixels.substance.api.icon.SubstanceIconUIResource;
import org.pushingpixels.substance.internal.contrib.randelshofer.quaqua.colorchooser.SubstanceColorChooserPanel;

public class RGBaColorChooser extends SubstanceColorChooserPanel
{
	private JSlider red;
	private JSlider green;
	private JSlider blue;
	private JSlider alpha;

	private JTextField redText;
	private JTextField greenText;
	private JTextField blueText;
	private JTextField alphaText;

	private final JLabel gradient = new JLabel();

	private final GridBagConstraints c1 = new GridBagConstraints();

	@Override
	public SubstanceIconUIResource getHiDpiAwareIcon(int size, SubstanceColorScheme colorScheme)
	{
		return SubstanceCortex.GlobalScope.getIconPack().getColorChooserColorSwatchesIcon(size,
			colorScheme);
	}

	@Override
	public void updateChooser()
	{

	}

	private JSlider createSlider(String name)
	{
		JSlider slider = new JSlider(0, 255);
		slider.setUI(new RGBaColorSlider(slider));
		slider.setBackground(new Color(52, 52, 52));
		slider.setBorder(null);
		slider.setFocusable(false);
		slider.setName(name);
		slider.setPreferredSize(new Dimension(260, 20));

		return slider;
	}

	private JTextField createText()
	{
		JTextField field = new JTextField();
		field.setFont(new Font("Arial", Font.PLAIN, 10));
		field.setPreferredSize(new Dimension(30, 18));
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setText("0");
		field.setFocusable(false);
		field.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();

				if (!((c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)
					|| (c == KeyEvent.VK_ENTER) || (c == KeyEvent.VK_TAB)
					|| (Character.isDigit(c))) || field.getText().length() >= 3)
				{
					e.consume();
				}

			}
		});

		((PlainDocument) field.getDocument()).setDocumentFilter(new DocumentFilter()
		{
			@Override
			public void insertString(FilterBypass fb, int offset, String string,
									 AttributeSet attr) throws BadLocationException
			{

				Document doc = fb.getDocument();
				StringBuilder sb = new StringBuilder();
				sb.append(doc.getText(0, doc.getLength()));
				sb.insert(offset, string);

				if (test(sb.toString()))
				{
					super.insertString(fb, offset, string, attr);
				}
				else
				{
					// warn the user and don't allow the insert
				}
			}

			private boolean test(String text)
			{
				try
				{
					Integer.parseInt(text);
					return true;
				}
				catch (NumberFormatException e)
				{
					return false;
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text,
								AttributeSet attrs) throws BadLocationException
			{

				Document doc = fb.getDocument();
				StringBuilder sb = new StringBuilder();
				sb.append(doc.getText(0, doc.getLength()));
				sb.replace(offset, offset + length, text);

				if (test(sb.toString()))
				{
					super.replace(fb, offset, length, text, attrs);
				}
				else
				{
					// warn the user and don't allow the insert
				}

			}

			@Override
			public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException
			{
				Document doc = fb.getDocument();
				StringBuilder sb = new StringBuilder();
				sb.append(doc.getText(0, doc.getLength()));
				sb.delete(offset, offset + length);

				if (test(sb.toString()))
				{
					super.remove(fb, offset, length);
				}
				else
				{
					// warn the user and don't allow the insert
				}

			}

			private void check()
			{
				int value = NumberUtils.toInt(field.getText(), -1);
				if (value < 0)
				{
					field.setText("0");
				}
				else if (value > 255)
				{
					field.setText("255");
				}
			}
		});
		return field;
	}

	private void createSliders()
	{
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridBagLayout());

		GridBagConstraints cst = new GridBagConstraints();
		cst.insets = new Insets(0, 0, 5, 0);

		red = createSlider("Red");
		cst.fill = GridBagConstraints.HORIZONTAL;
		cst.gridx = 0;
		cst.gridy = 0;
		bottom.add(red, cst);

		redText = createText();
		cst.fill = GridBagConstraints.BELOW_BASELINE;
		cst.gridx = 1;
		bottom.add(redText, cst);


		green = createSlider("Green");
		cst.fill = GridBagConstraints.HORIZONTAL;
		cst.gridx = 0;
		cst.gridy += 1;
		bottom.add(green, cst);

		greenText = createText();
		cst.fill = GridBagConstraints.NONE;
		cst.gridx = 1;
		bottom.add(greenText, cst);

		blue = createSlider("Blue");
		cst.fill = GridBagConstraints.HORIZONTAL;
		cst.gridx = 0;
		cst.gridy += 1;
		bottom.add(blue, cst);

		blueText = createText();
		cst.fill = GridBagConstraints.NONE;
		cst.gridx = 1;
		bottom.add(blueText, cst);

		alpha = createSlider("Alpha");
		cst.fill = GridBagConstraints.HORIZONTAL;
		cst.gridx = 0;
		cst.gridy += 1;
		bottom.add(alpha, cst);

		alphaText = createText();
		cst.fill = GridBagConstraints.NONE;
		cst.gridx = 1;
		bottom.add(alphaText, cst);

		c1.gridy = 1;
		c1.fill = GridBagConstraints.SOUTH;
		add(bottom, c1);
	}

	public void createTop()
	{
		JPanel top = new JPanel();
		top.setLayout(new GridBagLayout());
		GridBagConstraints cst = new GridBagConstraints();

		updateGradient(Color.RED);

		JSlider slider = new JSlider(JSlider.VERTICAL, 1, 140, 1);
		slider.setPreferredSize(new Dimension(50, 150));
		slider.setUI(new PrimaryColorSlider(slider));
		slider.setBackground(new Color(52, 52, 52));
		slider.setBorder(null);
		slider.setFocusable(false);
		slider.addChangeListener(e ->
		{
			PrimaryColorSlider ui = (PrimaryColorSlider) slider.getUI();

			updateGradient(ui.getColorForValue(slider.getValue()));
		});

		cst.gridx = 0;
		cst.gridy = 0;
		top.add(slider, cst);

		cst.gridx = 1;
		cst.fill = GridBagConstraints.CENTER;
		top.add(gradient, cst);


		c1.gridx = 0;
		c1.gridy = 0;
		c1.fill = GridBagConstraints.NORTH;
		add(top, c1);
	}

	private void updateGradient(Color c)
	{
		gradient.setIcon(new ImageIcon(drawGradient(c)));
	}

	private BufferedImage drawGradient(Color primaryRight)
	{
		int size = 150;
		Color primaryLeft = Color.white;
		Color shadeColor = Color.black;

		BufferedImage image = new BufferedImage(
			size, size, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = image.createGraphics();
		GradientPaint primary = new GradientPaint(
			0f, 0f, primaryLeft, size, 0f, primaryRight);
		int rC = shadeColor.getRed();
		int gC = shadeColor.getGreen();
		int bC = shadeColor.getBlue();
		GradientPaint shade = new GradientPaint(
			0f, 0f, new Color(rC, gC, bC, 0),
			0f, size, shadeColor);
		g.setPaint(primary);
		g.fillRect(0, 0, size, size);
		g.setPaint(shade);
		g.fillRect(0, 0, size, size);

		g.dispose();
		return image;
	}

	@Override
	protected void buildChooser()
	{
		setLayout(new GridBagLayout());
		createTop();
		createSliders();
	}

	@Override
	public String getDisplayName()
	{
		return "";
	}
}
