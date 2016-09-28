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
	// n�meros menores que 2 n�o s�o primos, pelo menos n�o para o URI Online Judge
	// no mundo real, os n�meros negativos s�o primos quando o seu oposto tamb�m � primo
	if (n < 2) {
		return 0;
	}
	// 2 e 3 s�o os primeiros primos
	if (n <= 3) {
		return 1;
	}
	// se for divis�vel por 2 ou 3, n�o � primo
	if (n % 2 == 0 || n % 3 == 0) {
		return 0;
	}
	// para verificar se n � primo n�o � necess�rio testar todos os n�meros de 5 � n
	// basta testar se ele � divis�vel por algum n�mero de 5 at� a ra�z quadrada de n
	// se at� chegar � ra�z ele n�o foi divis�vel por nenhum n�mero, ele � primo
	// enquanto i * i for menor ou igual � n, significa que a itera��o n�o alcan�ou a ra�z de n
	int i;
	for (i = 5; i * i <= n; i += 2) {
		// se n dividido por i n�o tiver resto, significa que n � divisivel por i, ent�o n�o � primo
		if (n % i == 0) {
			return 0;
		}
	}
	return 1;
}

