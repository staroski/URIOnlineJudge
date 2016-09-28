/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1672">1672 In Danger</a>
 * 
 * @author ricardo.staroski
 */

#include <stdio.h>
#include <math.h>

int parseXYeZ(char digits[]);
int safePosition(int n);
unsigned int highestOneBit(unsigned int i);

int main() { 
	int persons = 0;
	char xyez[4];
	scanf("%s", &xyez);
	while ((persons = parseXYeZ(xyez)) > 0) {
		printf("%d\n", safePosition(persons));
		scanf("%s", &xyez);
	}
    return 0;
}

int parseXYeZ(char digits[]) {
	// de acordo com o enunciado, um número N é representado no formato XYeZ, onde
	// X: é a casa da dezena
	// Y: é a casa da unidade
	// e: é desconsiderado
	// Z: é a quantidade de zeros à direita do número, ou seja, a potência de 10
	int asciiOffset = 48;
	int x = (digits[0] - asciiOffset) * 10; // valor da dezena
	int y = digits[1] - asciiOffset; // valor da unidade
	int z = (int) pow(10, (int) (digits[3] - asciiOffset)); // valor da potência de 10
	return (x + y) * z;
}

int safePosition(int n) {
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
	// int l = n - highestOneBit(n);
	// agora obtém-se a posição segura da seguinte forma:
	// int safePosition = 2 * l + 1;
	return 2 * (n - highestOneBit(n)) + 1;
}

unsigned int highestOneBit(unsigned int i) {
    i |= (i >>  1);
    i |= (i >>  2);
    i |= (i >>  4);
    i |= (i >>  8);
    i |= (i >> 16);
    return i - (i >> 1);
}

