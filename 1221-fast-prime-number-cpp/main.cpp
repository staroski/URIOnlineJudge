/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1221">1221 Fast Prime Number</a>
 * 
 * @author ricardo.staroski
 */

#include <stdio.h>

int isPrime(int n);

int main() { 
	int testCases, number;
	scanf("%d", &testCases);
	int i;
	for (i = 0; i < testCases; i++) {
		scanf("%d", &number);
		printf("%s\n", isPrime(number) ? "Prime" : "Not Prime");
	}
    return 0;
}

int isPrime(int n) {
	if (n < 2) {
		return 0;
	}
	if (n < 4) {
		return 1;
	}
	if (n % 2 == 0 || n % 3 == 0) {
		return 0;
	}
	int i;
	for (i = 5; i * i <= n; i += 6) {
		if (n % i == 0) {
			return 0;
		}
		if (n % (i + 2) == 0) {
			return 0;
		}
	}
	return 1;
}
