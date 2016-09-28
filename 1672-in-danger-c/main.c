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
	// de acordo com o enunciado, um n�mero N � representado no formato XYeZ, onde
	// X: � a casa da dezena
	// Y: � a casa da unidade
	// e: � desconsiderado
	// Z: � a quantidade de zeros � direita do n�mero, ou seja, a pot�ncia de 10
	int asciiOffset = 48;
	int x = (digits[0] - asciiOffset) * 10; // valor da dezena
	int y = digits[1] - asciiOffset; // valor da unidade
	int z = (int) pow(10, (int) (digits[3] - asciiOffset)); // valor da pot�ncia de 10
	return (x + y) * z;
}

int safePosition(int n) {
	// para resolver o problema de Josephus,
	// poderia ser utilizada uma fila circular
	// seria bem f�cil, itera duas posi��es na fila e remove um elemento
	// bastaria ir repetindo esse la�o at� sobrar somente um elemento na fila
	// entretanto, as itera��es iriam demorar bastante
	// ter�amos problemas em um numero de elementos na magnitude de 8 digitos,
	// que ocorrem no enunciado do problema proposto
	// isso exigiria muita aloca��o de mem�ria

	// outra alternativa seria utilizar uma fun��o recursiva, conforme abaixo:
	// int f(int n, int k) { return (n == 1) ? 1 : (((f(n - 1, k) + k - 1) % n) + 1); }
	// por�m, embora seja mais r�pido do que iterar a fila circular,
	// a recursividade tamb�m iria exigir uma stack muito grande
	// a cada reentrada o endere�o da ultima chamada � empilhado
	// novamente ter�amos problemas em um numero de elementos na magnitude de 8 digitos,
	// que ocorrem no enunciado do problema proposto

	// existe um jeito mais r�pido, com consumo de mem�ria desprez�vel
	// mas com legibilidade de c�digo p�ssima e, adivinhe s�,
	// � exatamente essa forma que iremos utilizar
	// primeiro obt�m-se valor l subtraindo de n a magnitude do maior bit ligado em n
	// int l = n - highestOneBit(n);
	// agora obt�m-se a posi��o segura da seguinte forma:
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

