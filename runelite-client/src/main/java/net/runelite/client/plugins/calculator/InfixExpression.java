package net.runelite.client.plugins.calculator;

import java.util.Stack;
import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class InfixExpression extends Stack<String>
{
	private StringBuilder expression = new StringBuilder();

	public void clear()
	{
		expression.setLength(0);
	}

	public void append(String s)
	{
		String tmp = pop();

		if (tmp == null)
		{
			tmp = "";
		}

		tmp += s;

		push(tmp);
	}

	public Boolean isPeekNumeric()
	{
		return StringUtils.isNumeric(peek());
	}

	public String toString()
	{
		return Strings.join(this, " ");
	}

	private Stack<String> toPostfix()
	{

			log.debug("Infix string: {}", this);

		/* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down).
           This will give us: 0, 0, 1, 1, 2 */
			final String ops = "-+/*^";

			StringBuilder sb = new StringBuilder();
			Stack<Integer> s = new Stack<>();
			Stack<String> res = new Stack<>();


			for (String token : this)
			{
				if (token.isEmpty())
				{
					continue;
				}
				char c = token.charAt(0);
				int idx = ops.indexOf(c);

				// check for operator
				if (idx != -1)
				{
					if (s.isEmpty())
					{
						s.push(idx);
					}

					else
					{
						while (!s.isEmpty())
						{
							int prec2 = s.peek() / 2;
							int prec1 = idx / 2;
							if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
							{
//								sb.append(ops.charAt(s.pop())).append(' ');
								res.push(ops.charAt(s.pop()) + "");
							}
							else
							{
								break;
							}
						}
						s.push(idx);
					}
				}
				else if (c == '(')
				{
					s.push(-2); // -2 stands for '('
				}
				else if (c == ')')
				{
					// until '(' on stack, pop operators.
					while (s.peek() != -2)
					{
						res.push(ops.charAt(s.pop()) + "");
//						sb.append(ops.charAt(s.pop())).append(' ');
					}
					s.pop();
				}
				else
				{
					res.push(token );
//					sb.append(token).append(' ');
				}
			}
			while (!s.isEmpty())
			{
				res.push(ops.charAt(s.pop()) + "");
//				sb.append(ops.charAt(s.pop())).append(' ');
			}

			log.debug("Postfix string: {}", Strings.join(res, " "));

			return res;
		}

	public Double calculate()
	{
		Stack<Double> stack = new Stack<>();

		for (String token : toPostfix())
		{
			switch (token)
			{
				case "+":
					stack.push(stack.pop() + stack.pop());
					break;
				case "-":
					stack.push(-stack.pop() + stack.pop());
					break;
				case "*":
					stack.push(stack.pop() * stack.pop());
					break;
				case "/":
					double divisor = stack.pop();
					stack.push(stack.pop() / divisor);
					break;
				case "^":
					double exponent = stack.pop();
					stack.push(Math.pow(stack.pop(), exponent));
					break;
				default:
					stack.push(Double.parseDouble(token));
					break;
			}
		}

		return stack.pop();
	}
}
