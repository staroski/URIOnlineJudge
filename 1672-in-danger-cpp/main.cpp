/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1672">1672 In Danger</a>
 * 
 * @author ricardo.staroski
 */

#include <iostream>

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
	int asciiOffset = 48;
	int x = (digits[0] - asciiOffset) * 10; // valor da dezena
	int y = digits[1] - asciiOffset; // valor da unidade
	int z = (int) pow(10, (int) (digits[3] - asciiOffset)); // valor da potência de 10
	return (x + y) * z;
}

int safePosition(int n) {
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
