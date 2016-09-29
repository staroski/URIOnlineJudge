import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1726">1726Friends</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		for (String line = null; (line = in.readLine()) != null && !line.isEmpty(); out.println(parseUnionOrDifference(new StringBuilder(line)))) {}
	}

	// calcula a diferença (-) de dois conjuntos
	private static StringBuilder computeDifference(StringBuilder value1, StringBuilder value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.removeAll(set2);
		return setToString(set1);
	}

	// calcula a intersecção (*) de dois conjuntos
	private static StringBuilder computeIntersection(StringBuilder value1, StringBuilder value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.retainAll(set2);
		return setToString(set1);
	}

	// calcula a união (+) de dois conjuntos
	private static StringBuilder computeUnion(StringBuilder value1, StringBuilder value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.addAll(set2);
		return setToString(set1);
	}

	// faz o parse de um bloco
	private static StringBuilder parseBlock(StringBuilder expression) {
		char symbol = expression.charAt(0);
		if (symbol != '(') {
			return new StringBuilder();
		}
		expression.deleteCharAt(0);
		StringBuilder result = parseUnionOrDifference(expression);
		symbol = expression.charAt(0);
		expression.deleteCharAt(0);
		return result;
	}

	// faz o parse da operação '*'
	private static StringBuilder parseIntersection(StringBuilder expression) {
		StringBuilder value1 = parseSet(expression);
		for (; expression.length() > 0;) {
			char symbol = expression.charAt(0);
			if (symbol != '*') {
				return value1;
			}
			expression.deleteCharAt(0);
			StringBuilder value2 = parseSet(expression);
			value1 = computeIntersection(value1, value2);
		}
		return value1;
	}

	// faz o parse de um conjunto
	private static StringBuilder parseSet(StringBuilder expression) {
		StringBuilder value = parseBlock(expression);
		StringBuilder result = new StringBuilder(value);
		if (expression.length() > 0) {
			char symbol = expression.charAt(0);
			if (symbol != '{') {
				return value;
			}
			do {
				symbol = expression.charAt(0);
				result.append(symbol);
				expression.deleteCharAt(0);
			} while (symbol != '}');
		}
		return result;
	}

	// faz o parse das operações '+' e '-'
	private static StringBuilder parseUnionOrDifference(StringBuilder expression) {
		StringBuilder value1 = parseIntersection(expression);
		for (; expression.length() > 0;) {
			char symbol = expression.charAt(0);
			if (symbol != '+' && symbol != '-') {
				return value1;
			}
			expression.deleteCharAt(0);
			StringBuilder value2 = parseIntersection(expression);
			if (symbol == '+') {
				value1 = computeUnion(value1, value2);
			} else {
				value1 = computeDifference(value1, value2);
			}
		}
		return value1;
	}

	// transforma um Set<Character> em uma String
	private static StringBuilder setToString(Set<Character> set) {
		StringBuilder text = new StringBuilder("{");
		for (Character symbol : set) {
			text.append(symbol);
		}
		text.append('}');
		return text;
	}

	// transforma uma String em um Set<Character>
	private static Set<Character> stringToSet(StringBuilder value) {
		TreeSet<Character> set = new TreeSet<>();
		if ("{}".equals(value)) {
			return set;
		}
		for (int i = 1, n = value.length() - 1; i < n; i++) {
			set.add(value.charAt(i));
		}
		return set;
	}
}
