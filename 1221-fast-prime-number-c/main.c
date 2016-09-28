/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1221">1221 Fast Prime Number</a>
 * 
 * @author ricardo.staroski
 */

#include <stdio.h>

int isPrime(int n);

int main() { 
	int testCases, number;
	char *message;
	scanf("%d", &testCases);
	int i;
	for (i = 0; i < testCases; i++) {
		scanf("%d", &number);
		message = isPrime(number) ? "Prime" : "Not Prime";
		printf("%s\n", message);
	}
    return 0;
}

int isPrime(int n) {
	// números menores que 2 não são primos, pelo menos não para o URI Online Judge
	// no mundo real, os números negativos são primos quando o seu oposto também é primo
	if (n < 2) {
		return 0;
	}
	// 2 e 3 são os primeiros primos
	if (n <= 3) {
		return 1;
	}
	// se for divisível por 2 ou 3, não é primo
	if (n % 2 == 0 || n % 3 == 0) {
		return 0;
	}
	// para verificar se n é primo não é necessário testar todos os números de 5 à n
	// basta testar se ele é divisível por algum número de 5 até a raíz quadrada de n
	// se até chegar à raíz ele não foi divisível por nenhum número, ele é primo
	// enquanto i * i for menor ou igual à n, significa que a iteração não alcançou a raíz de n
	int i;
	for (i = 5; i * i <= n; i += 2) {
		// se n dividido por i não tiver resto, significa que n é divisivel por i, então não é primo
		if (n % i == 0) {
			return 0;
		}
	}
	return 1;
}

