import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1221">1221 Fast Prime Number</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		int testCases = Integer.parseInt(in.readLine());
		for (int i = 0; i < testCases; i++) {
			int number = Integer.parseInt(in.readLine());
			String message = isPrime(number) ? "Prime" : "Not Prime";
			out.printf("%s\n", message);
		}
	}

	private static boolean isPrime(int n) {
		// números menores que 2 não são primos, pelo menos não para o URI Online Judge
		// no mundo real, os números negativos são primos quando o seu oposto também é primo
		if (n < 2) {
			return false;
		}
		// 2 é o primeiro primo
		if (n == 2) {
			return true;
		}
		// se n é maior que 2 e é par, ele não é primo
		if (n % 2 == 0) {
			return false;
		}
		// para verificar se n é primo não é necessário testar todos os números de 3 à n
		// basta testar se ele é divisível por algum número de 3 até a raíz quadrada de n
		// se até chegar à raíz ele não foi divisível por nenhum número, ele é primo
		// enquanto i * i for menor ou igual à n, significa que a iteração não alcançou a raíz de n
		for (int i = 3; i * i <= n; i += 2) {
			// se n dividido por i não tiver resto, significa que n é divisivel por i, então não é primo
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
