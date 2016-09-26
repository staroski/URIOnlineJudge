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

	public static void main(String[] args) throws IOException {
		String line = null;
		while ((line = in.readLine()) != null && !line.isEmpty()) {
			StringBuilder expression = new StringBuilder(line);
			out.println(evaluate(expression));
		}
	}

	public static String evaluate(StringBuilder expression) {
		// esta é a implementação de um parser descendente recursivo
		// que resolve a gramática do problema proposto pela URI Online Judge
		// sua chamadas recursivas resolvem a precedência de operadores
		return parseUnionOrDifference(expression);
	}

	private static final char INTERSECTION = '*';
	private static final char UNION = '+';
	private static final char DIFFERENCE = '-';

	private static final char OPEN_SET = '{';
	private static final char CLOSE_SET = '}';
	private static final char FIRST_SET_SYMBOL = 'A';
	private static final char LAST_SET_SYMBOL = 'Z';

	private static final char OPEN_BLOCK = '(';
	private static final char CLOSE_BLOCK = ')';
	private static final String EMPTY_SET = "{}";

	// calcula a diferença (-) de dois conjuntos
	private static String computeDifference(String value1, String value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.removeAll(set2);
		return setToString(set1);
	}

	// calcula a intersecção (*) de dois conjuntos
	private static String computeIntersection(String value1, String value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.retainAll(set2);
		return setToString(set1);
	}

	// calcula a união (+) de dois conjuntos
	private static String computeUnion(String value1, String value2) {
		Set<Character> set1 = stringToSet(value1);
		Set<Character> set2 = stringToSet(value2);
		set1.addAll(set2);
		return setToString(set1);
	}

	// verifica se o símbolo informado é um elemento válido para um conjunto
	private static boolean isSetSymbol(char symbol) {
		return symbol == OPEN_SET || symbol == CLOSE_SET || symbol >= FIRST_SET_SYMBOL && symbol <= LAST_SET_SYMBOL;
	}

	// faz o parse de um bloco
	private static String parseBlock(StringBuilder expression) {
		char symbol = expression.charAt(0);
		if (symbol != OPEN_BLOCK) {
			return "";
		}
		expression.deleteCharAt(0);
		String result = parseUnionOrDifference(expression);
		if (expression.length() < 1) {
			throw syntaxError(String.format("Unclosed block! Expected '%c'", CLOSE_SET));
		}
		symbol = expression.charAt(0);
		if (symbol != CLOSE_BLOCK) {
			throw syntaxError(String.format("Unclosed block! Expected '%c' found '%c'", CLOSE_BLOCK, symbol));
		}
		expression.deleteCharAt(0);
		return result.toString();
	}

	// faz o parse da operação '*'
	private static String parseIntersection(StringBuilder expression) {
		String value1 = parseSet(expression);
		while (expression.length() > 0) {
			char symbol = expression.charAt(0);
			if (symbol != INTERSECTION) {
				return value1;
			}
			expression.deleteCharAt(0);
			String value2 = parseSet(expression);
			if (value2.isEmpty()) {
				throw syntaxError(String.format("Expected second operator for operation '%c'", symbol));
			}
			value1 = computeIntersection(value1, value2);
		}
		return value1;
	}

	// faz o parse de um conjunto
	private static String parseSet(StringBuilder expression) {
		String value = parseBlock(expression);
		StringBuilder result = new StringBuilder(value);
		if (expression.length() > 0) {
			char symbol = expression.charAt(0);
			if (symbol != OPEN_SET) {
				return value;
			}
			do {
				if (expression.length() < 1) {
					throw syntaxError(String.format("Unclosed set! Expected '%c'", CLOSE_SET));
				}
				symbol = expression.charAt(0);
				if (!isSetSymbol(symbol)) {
					throw syntaxError(String.format("Invalid symbol! Expected '[%c-%c]' found '%c'", FIRST_SET_SYMBOL, LAST_SET_SYMBOL, symbol));
				}
				result.append(symbol);
				expression.deleteCharAt(0);
			} while (symbol != CLOSE_SET);
		}
		return result.toString();
	}

	// faz o parse das operações '+' e '-'
	private static String parseUnionOrDifference(StringBuilder expression) {
		String value1 = parseIntersection(expression);
		while (expression.length() > 0) {
			char symbol = expression.charAt(0);
			if (symbol != UNION && symbol != DIFFERENCE) {
				return value1;
			}
			expression.deleteCharAt(0);
			String value2 = parseIntersection(expression);
			if (value2.isEmpty()) {
				throw syntaxError(String.format("Expected second operator for operation '%c'", symbol));
			}
			if (symbol == UNION) {
				value1 = computeUnion(value1, value2);
			} else {
				value1 = computeDifference(value1, value2);
			}
		}
		return value1;
	}

	// transforma um Set<Character> em uma String
	private static String setToString(Set<Character> set) {
		StringBuilder text = new StringBuilder().append(OPEN_SET);
		for (Character symbol : set) {
			text.append(symbol);
		}
		text.append(CLOSE_SET);
		return text.toString();
	}

	// transforma uma String em um Set<Character>
	private static Set<Character> stringToSet(String value) {
		TreeSet<Character> set = new TreeSet<>();
		if (!EMPTY_SET.equals(value)) {
			value = value.substring(1, value.length() - 1); // remover { e }
			for (char symbol : value.toCharArray()) {
				set.add(symbol);
			}
		}
		return set;
	}

	// utilizado para lançar erros de sintaxe
	private static RuntimeException syntaxError(String message) {
		return new RuntimeException("syntax error: " + message);
	}
}
