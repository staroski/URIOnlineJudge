/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1726">1726 Friends</a>
 * 
 * @author ricardo.staroski
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// tipo booleano
typedef enum { false, true } bool;

// funçoes pra manipular strings
char * string_new();
void string_insert_char(char * string, char symbol, int pos);
void string_remove_char(char * string, int pos);

// funções do parser descendente recursivo
char * parseUnionOrDifference(char * expression);
char * computeDifference(char * set1, char * set2);
char * computeIntersection(char * set1, char * set2);
char * computeUnion(char * set1, char * set2);
void insert(char symbol, char * set, int limit);
char * parseBlock(char * expression);
char * parseIntersection(char * expression);
char * parseSet(char * expression);

// ponto de entrada do programa
int main() { 
	char * line = string_new();
	fgets(line, sizeof(line), stdin);
	while (line != NULL) {
		printf("%s\n", parseUnionOrDifference(line));
		fgets(line, sizeof(line), stdin);
	}
    return 0;
}

// cria uma String vazia
char * string_new() {
	char * buffer = malloc(sizeof(char) * 256);
	memset(buffer, 0, sizeof(buffer));
    return buffer;
}

// insere um caractere na posição informada
void string_insert_char(char * string, char symbol, int pos) {
	memmove(string + pos + 1, string + pos, strlen(string) - pos + 1);
	string[pos] = symbol;
}

// remove um caractere na posição informada
void string_remove_char(char * string, int pos) {
	memmove(string + pos, string + pos + 1, strlen(string) - pos);
}


// faz o parse das operações '+' e '-'
char * parseUnionOrDifference(char * expression) {
    char * value1 = parseIntersection(expression);
    while (strlen(expression) > 0) {
        char symbol = expression[0];
        if (symbol != '+' && symbol != '-') {
            return value1;
        }
        string_remove_char(expression, 0);
        char * value2 = parseIntersection(expression);
        if (symbol == '+') {
            value1 = computeUnion(value1, value2);
        } else {
            value1 = computeDifference(value1, value2);
        }
    }
    return value1;
}

// faz o parse da operação '*'
char * parseIntersection(char * expression) {
    char * value1 = parseSet(expression);
    while (strlen(expression) > 0) {
        char symbol = expression[0];
        if (symbol != '*') {
            return value1;
        }
        string_remove_char(expression, 0);
        char * value2 = parseSet(expression);
        value1 = computeIntersection(value1, value2);
    }
    return value1;
}

// faz o parse de um conjunto
char * parseSet(char * expression) {
    char * value = parseBlock(expression);
    char * result = string_new();
    strcat(result, value);
    if (strlen(expression) > 0) {
        char symbol = expression[0];
        if (symbol != '{') {
            return value;
        }
        do {
            symbol = expression[0];
            string_insert_char(result, symbol, strlen(result));
            string_remove_char(expression, 0);
        } while (symbol != '}');
    }
    return result;
}

// calcula a união (+) de dois conjuntos
char * computeUnion(char * set1, char * set2) {
    int limit1 = strlen(set1) - 1;
    int limit2 = strlen(set2) - 1;
    int i2 = 1;
    while (i2 < limit2) {
        bool second = false;
        char symbol = set2[i2];
        int i1 = 1;
        while (!second && i1 < limit1) {
            if (set1[i1] == symbol) {
                second = true;
            }
            i1++;
        }
        if (!second) {
            insert(symbol, set1, limit1);
            limit1++;
        }
        i2++;
    }
    return set1;
}

// insere um elemento de forma ordenada
void insert(char symbol, char * set, int limit) {
	int i;
    for (i = 1; i < limit; i++) {
        if (symbol < set[i]) {
        	string_insert_char(set, symbol, i);
            return;
        }
    }
    string_insert_char(set, symbol, limit);
}

// calcula a diferença (-) de dois conjuntos
char * computeDifference(char * set1, char * set2) {
    int limit1 = strlen(set1) - 1;
    int limit2 = strlen(set2) - 1;
    int i2 = 1;
    while (i2 < limit2) {
        bool second = false;
        char symbol = set2[i2];
        int i1 = 1;
        while (!second && i1 < limit1) {
            if (set1[i1] == symbol) {
                string_remove_char(set1, i1);
                limit1 -= 1;
                second = true;
            }
            i1 += 1;
        }
        i2 += 1;
    }
    return set1;
}

// calcula a intersecção (*) de dois conjuntos
char * computeIntersection(char * set1, char * set2) {
    int limit1 = strlen(set1) - 1;
    int limit2 = strlen(set2) - 1;
    int i1 = 1;
    while (i1 < limit1) {
        bool first = false;
        char symbol = set1[i1];
        int i2 = 1;
        while (!first && i2 < limit2) {
            if (set2[i2] == symbol) {
                first = true;
            }
            i2++;
        }
        if (!first) {
        	string_remove_char(set1, i1);
            i1--;
            limit1--;
        }
        i1++;
    }
    return set1;
}

// faz o parse de um bloco
char * parseBlock(char * expression) {
    char symbol = expression[0];
    if (symbol != '(') {
        return string_new();
    }
    string_remove_char(expression, 0);
    char * result = parseUnionOrDifference(expression);
    symbol = expression[0];
    string_remove_char(expression, 0);
    return result;
}
