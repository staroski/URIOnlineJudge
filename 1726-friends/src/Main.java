import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1726">1726 Friends</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		String line = null;
		while ((line = in.readLine()) != null && !line.isEmpty()) {
			StringBuilder expression = new StringBuilder(line);
			out.println(evaluate(expression));
		}
	}

	static StringBuilder ParseAtom(StringBuilder expr) {
		// // Read the number from string
		// StringBuilder end_ptr;
		// StringBuilder res = strtod(expr, end_ptr);
		// // Advance the pointer and return the result
		// expr = end_ptr;
		// return res;

		String value = expr.toString();
		int start = offset;
		int end = value.indexOf('}', offset) + 1;
		offset += end;
		return new StringBuilder(value.substring(start, end));
	}

	// Parse multiplication and division
	static StringBuilder ParseFactors(StringBuilder expr) {
		StringBuilder value1 = ParseAtom(expr);
		for (;;) {
			// Save the operation
			char op = expr.charAt(offset);
			if (op != '/' && op != '*') {
				return value1;
			}
			offset++;
			StringBuilder value2 = ParseAtom(expr);
			// Perform the saved operation
			if (op == '/') {
				// TODO value1 /= value2;
			} else {
				// TODO value1 *= value2;
			}
		}
	}

	private static int offset;

	// Parse addition and subtraction
	static StringBuilder ParseSummands(StringBuilder expr) {
		StringBuilder value1 = ParseFactors(expr);
		for (;;) {
			char op = expr.charAt(offset);
			if (op != '-' && op != '+') {
				return value1;
			}
			offset++;
			StringBuilder value2 = ParseFactors(expr);
			if (op == '-') {
				// TODO value1 -= value2;
			} else {
				// TODO value1 += value2;
			}
		}
	}

	static String evaluate(StringBuilder expr) {
		offset = 0;
		return ParseSummands(expr).toString();
	};
}
