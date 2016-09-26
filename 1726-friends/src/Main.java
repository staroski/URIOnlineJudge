import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1726">1726 Friends</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintStream out = System.out;

	public static String evaluate(StringBuilder expression) {
		// esta é a implementação de um parser recursivo descendente
		// que resolve a gramática do problema proposto pela URI
		// sua chamadas recursivas resolvem a precedência de operadores
		return parseUnionOrDifference(expression);
	}

	public static void main(String[] args) throws IOException {
		String line = null;
		while ((line = in.readLine()) != null && !line.isEmpty()) {
			StringBuilder expression = new StringBuilder(line);
			out.println(evaluate(expression));
		}
	}

	private static String computeDifference(String value1, String value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.removeAll(set2);
		return setToString(set1);
	}

	private static String computeIntersection(String value1, String value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.retainAll(set2);
		return setToString(set1);
	}

	private static String computeUnion(String value1, String value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.addAll(set2);
		return setToString(set1);
	}

	private static String parseBlocks(StringBuilder expression) {
		char symbol = expression.charAt(0);
		if (symbol != '(') {
			return "";
		}
		expression.deleteCharAt(0);
		String result = parseUnionOrDifference(expression);
		symbol = expression.charAt(0);
		if (symbol != ')') {
			throw new RuntimeException("syntax error: unclosed block");
		}
		expression.deleteCharAt(0);
		return result.toString();
	}

	private static String parseIntersection(StringBuilder expression) {
		String value1 = parseSet(expression);
		while (expression.length() > 0) {
			char op = expression.charAt(0);
			if (op != '*') {
				return value1;
			}
			expression.deleteCharAt(0);
			String value2 = parseSet(expression);
			value1 = computeIntersection(value1, value2);
		}
		return value1;
	}

	private static String parseSet(StringBuilder expression) {
		String value = parseBlocks(expression);
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
		return result.toString();
	}

	private static String parseUnionOrDifference(StringBuilder expression) {
		String value1 = parseIntersection(expression);
		while (expression.length() > 0) {
			char op = expression.charAt(0);
			if (op != '-' && op != '+') {
				return value1;
			}
			expression.deleteCharAt(0);
			String value2 = parseIntersection(expression);
			if (op == '+') {
				value1 = computeUnion(value1, value2);
			} else {
				value1 = computeDifference(value1, value2);
			}
		}
		return value1;
	}

	private static String setToString(Set<Character> set) {
		StringBuilder text = new StringBuilder("{");
		for (Character c : set) {
			text.append(c);
		}
		text.append("}");
		return text.toString();
	}

	private static Set<Character> stringToSet(String value) {
		TreeSet<Character> set = new TreeSet<>();
		if (!"{}".equals(value)) {
			value = value.substring(1, value.length() - 1); // remover { e }
			// se o URI rodasse em Java 8, poderia fazer assim:
			// set = value.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
			for (char symbol : value.toCharArray()) {
				set.add(symbol);
			}
		}
		return set;
	}
}
