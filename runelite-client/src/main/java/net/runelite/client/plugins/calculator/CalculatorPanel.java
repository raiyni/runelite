/*
 * Copyright (c) 2018 Charlie Waters
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
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
package net.runelite.client.plugins.calculator;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.PluginPanel;

@Slf4j
class CalculatorPanel extends PluginPanel implements ActionListener
{
	private final JTextArea resultView = new JTextArea();
	private final JTextArea expressionView = new JTextArea();

	private final JPanel memoryRow = new JPanel();
	private final JPanel buttonsView = new JPanel();
	private final GridBagConstraints constraints = new GridBagConstraints();

	private final InfixExpression expression = new InfixExpression();
	private final StringBuilder result = new StringBuilder();
	private Double output = 0.0;

	private Double memory = 0.0;
	private Boolean nextNegative = false;

	private JButton makeOp(String txt)
	{
		return new CalcButton(txt, ButtonType.OPERATOR, this);
	}

	private JButton makeNum(String txt)
	{
		return new CalcButton(txt, ButtonType.NUMBER, this);
	}

	private JButton makeMem(String txt)
	{
		return new CalcButton(txt, ButtonType.MEMORY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		CalcButton btn = (CalcButton) e.getSource();
		switch (btn.getType())
		{
			case NUMBER:
				pushNumber(btn.getText());
				break;
			case OPERATOR:
				pushOp(btn.getText());
				break;
			case MEMORY:
				parseMemory(btn.getText());
				break;
		}
	}

	private void parseMemory(String s)
	{
		switch (s)
		{
			case "mc":
				memory = 0.0;
				break;
			case "mr":
				deleteRecent();

				expression.push(memory.toString());
				updateExpression();
				break;
			case "m+":

				break;
			case "m-":
				break;
			case "ms":
				break;
		}
	}

	private void deleteRecent()
	{
		while (expression.isPeekNumeric())
		{
			expression.pop();
		}
	}


	private void clear(boolean all)
	{
		if (all)
		{
			expression.clear();
			updateExpression();
			output = 0.0;
			updateResult();
			return;
		}

		deleteRecent();
		updateExpression();
	}

	private void backspace()
	{
		expression.pop();

		updateExpression();
	}

	private void pushOp(String s)
	{
		switch (s)
		{
			case "ce":
				clear(false);
				return;
			case "c":
				clear(true);
				return;
			case "←":
				backspace();
				return;
			case "±":

				return;
			case "=":
				fullUpdate();
				return;
		}

		if (!expression.isEmpty())
		{
			if (!expression.isPeekNumeric())
			{
				expression.pop();
			}

			expression.push(s);
		}
		else
		{
//			expression.append(s);
		}

		updateExpression();
	}

	private void pushNumber(String s)
	{
		if (expression.isPeekNumeric())
		{
			expression.append(s);
		}
		else
		{
			expression.push(s);
		}

		updateExpression();
	}

	private GridBagConstraints nextX()
	{
		constraints.gridx++;

		return constraints;
	}

	private GridBagConstraints nextY()
	{
		constraints.gridy++;
		constraints.gridx = 0;

		return constraints;
	}

	private void createMemoryRow()
	{
		memoryRow.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 0, 0, 0);
		c.gridx = 0;
		c.weightx = 0.5;
		c.gridy = 0;
		memoryRow.add(makeMem("mc"), c);
		c.gridx++;
		memoryRow.add(makeMem("mr"), c);
		c.gridx++;
		memoryRow.add(makeMem("m+"), c);
		c.gridx++;
		memoryRow.add(makeMem("m-"), c);
		c.gridx++;
		memoryRow.add(makeMem("ms"), c);
	}

	void init()
	{
		add(expressionView);
		resultView.setRows(2);
		resultView.setFont(new Font("Arial", Font.PLAIN, 24));
		add(resultView);


		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.gridy = 0;

		createMemoryRow();
		add(memoryRow);

		buttonsView.setLayout(new GridBagLayout());

		buttonsView.add(makeOp("ce"), constraints);
		buttonsView.add(makeOp("c"), nextX());
		buttonsView.add(makeOp("←"), nextX());
		buttonsView.add(makeOp("/"), nextX());


		buttonsView.add(makeNum("7"), nextY());
		buttonsView.add(makeNum("8"), nextX());
		buttonsView.add(makeNum("9"), nextX());
		buttonsView.add(makeOp("*"), nextX());

		buttonsView.add(makeNum("4"), nextY());
		buttonsView.add(makeNum("5"), nextX());
		buttonsView.add(makeNum("6"), nextX());
		buttonsView.add(makeOp("-"), nextX());

		buttonsView.add(makeNum("1"), nextY());
		buttonsView.add(makeNum("2"), nextX());
		buttonsView.add(makeNum("3"), nextX());
		buttonsView.add(makeOp("+"), nextX());

		buttonsView.add(makeNum("±"), nextY());
		buttonsView.add(makeNum("0"), nextX());
		buttonsView.add(makeNum("."), nextX());
		buttonsView.add(makeOp("="), nextX());

		constraints.gridwidth = 2;
		buttonsView.add(makeOp("("), nextY());

		constraints.gridx = 2;
		buttonsView.add(makeOp(")"), constraints);

		add(buttonsView);
	}

	private void fullUpdate()
	{
		updateExpression();
		updateResult();
	}

	private void updateExpression()
	{
		expressionView.setText(expression.toString());
	}

	private void updateResult()
	{
		if (expression.size() > 0)
		{
			Double num = expression.calculate();

			if (num != output)
			{
				output = num;
			}
		}
		result.setLength(0);
		result.append(output);

		int len = result.length();
		if (result.charAt(len - 1) == '0' && result.charAt(len - 2) == '.')
		{
			result.setLength(len - 2);
		}
		resultView.setText(result.toString());
	}
}
