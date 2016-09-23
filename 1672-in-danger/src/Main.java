import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1672">1672 In Danger</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		int persons = parseXYeZ(in.readLine());
		while (persons > 0) {
			out.println(safePosition(persons));
			persons = parseXYeZ(in.readLine());
		}
	}

	private static int parseXYeZ(String value) {
		// de acordo com o enunciado, um número N é representado no formato XYeZ, onde
		// X: é a casa da dezena
		// Y: é a casa da unidade
		// e: é desconsiderado
		// Z: é a quantidade de zeros à direita do número, ou seja, a potência de 10
		char[] digits = value.toCharArray();
		int x = Character.digit(digits[0], 10) * 10; // valor da dezena
		int y = Character.digit(digits[1], 10); // valor da unidade
		int z = (int) Math.pow(10, Character.digit(digits[3], 10)); // valor da potência de 10
		return (x + y) * z;
	}

	private static int safePosition(int n) {
		// para resolver o problema de Josephus,
		// poderia ser utilizada uma fila circular
		// seria bem fácil, itera duas posições na fila e remove um elemento
		// bastaria ir repetindo esse laço até sobrar somente um elemento na fila
		// entretanto, as iterações iriam demorar bastante
		// teríamos problemas em um numero de elementos na magnitude de 8 digitos,
		// que ocorrem no enunciado do problema proposto
		// isso exigiria muita alocação de memória

		// outra alternativa seria utilizar uma função recursiva, conforme abaixo:
		// int f(int n, int k) { return (n == 1) ? 1 : (((f(n - 1, k) + k - 1) % n) + 1); }
		// porém, embora seja mais rápido do que iterar a fila circular,
		// a recursividade também iria exigir uma stack muito grande
		// a cada reentrada o endereço da ultima chamada é empilhado
		// novamente teríamos problemas em um numero de elementos na magnitude de 8 digitos,
		// que ocorrem no enunciado do problema proposto

		// existe um jeito mais rápido, com consumo de memória desprezível
		// mas com legibilidade de código péssima e, adivinhe só,
		// é exatamente essa forma que iremos utilizar
		// primeiro obtém-se valor l subtraindo de n a magnitude do maior bit ligado em n
		// int l = n - Integer.highestOneBit(n);
		// agora obtém-se a posição segura da seguinte forma:
		// int safePosition = 2 * l + 1;
		return 2 * (n - Integer.highestOneBit(n)) + 1;
	}
}
