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

	public static void main(String[] args) throws IOException {
		final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		final PrintStream out = System.out;
		for (String line = null; (line = in.readLine()) != null && !line.isEmpty(); out.println(parseUnionOrDifference(new StringBuilder(line)))) {}
	}

	// calcula a diferença (-) de dois conjuntos
	public static StringBuilder computeDifference(StringBuilder set1, StringBuilder set2) {
		int limit1 = set1.length() - 1;
		int limit2 = set2.length() - 1;
		SECOND: for (int i2 = 1; i2 < limit2; i2++) {
			char symbol = set2.charAt(i2);
			for (int i1 = 1; i1 < limit1; i1++) {
				if (set1.charAt(i1) == symbol) {
					set1.deleteCharAt(i1);
					limit1--;
					continue SECOND;
				}
			}
		}
		return set1;
	}

	// calcula a intersecção (*) de dois conjuntos
	public static StringBuilder computeIntersection(StringBuilder set1, StringBuilder set2) {
		int limit1 = set1.length();
		int limit2 = set2.length();
		FIRST: for (int i1 = 1; i1 < limit1; i1++) {
			char symbol = set1.charAt(i1);
			for (int i2 = 1; i2 < limit2; i2++) {
				if (set2.charAt(i2) == symbol) {
					continue FIRST;
				}
			}
			set1.deleteCharAt(i1--);
			limit1--;
		}
		return set1;
	}

	// calcula a união (+) de dois conjuntos
	public static StringBuilder computeUnion(StringBuilder set1, StringBuilder set2) {
		int limit1 = set1.length() - 1;
		int limit2 = set2.length() - 1;
		SECOND: for (int i2 = 1; i2 < limit2; i2++) {
			char symbol = set2.charAt(i2);
			for (int i1 = 1; i1 < limit1; i1++) {
				if (set1.charAt(i1) == symbol) {
					continue SECOND;
				}
			}
			insert(symbol, set1, limit1++);
		}
		return set1;
	}

	// insere um elemento de forma ordenada
	public static void insert(char symbol, StringBuilder set, int limit) {
		for (int i = 1; i < limit; i++) {
			if (symbol < set.charAt(i)) {
				set.insert(i, symbol);
				return;
			}
		}
		set.insert(limit, symbol);
	}

	// faz o parse de um bloco
	public static StringBuilder parseBlock(StringBuilder expression) {
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
	public static StringBuilder parseIntersection(StringBuilder expression) {
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
	public static StringBuilder parseSet(StringBuilder expression) {
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
	public static StringBuilder parseUnionOrDifference(StringBuilder expression) {
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
}
