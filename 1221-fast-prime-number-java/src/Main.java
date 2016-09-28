import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1221">1221
 * Fast Prime Number</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		int testCases = Integer.parseInt(in.readLine());
		for (int i = 0; i < testCases; i++) {
			out.printf("%s\n", isPrime(Integer.parseInt(in.readLine())) ? "Prime" : "Not Prime");
		}
	}

	private static boolean isPrime(int n) {
		if (n < 2) {
			return false;
		}
		if (n == 2 || n == 3 || n == 5) {
			return true;
		}
		if (n % 2 == 0 || n % 3 == 0 || n % 5 == 0) {
			return false;
		}
		for (int i = 7; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
