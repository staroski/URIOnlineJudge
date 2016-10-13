import scala.io.StdIn

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1726">1726 Friends</a>
 *
 * @author ricardo.staroski
 */
object Main {

    def main(args: Array[String]) {
        var line: String = StdIn.readLine();
        while (line != null) {
            println(parseUnionOrDifference(StringBuilder.newBuilder.append(line)));
            line = StdIn.readLine();
        }
    }

    // calcula a diferença (-) de dois conjuntos
    def computeDifference(set1: StringBuilder, set2: StringBuilder): StringBuilder = {
        var limit1: Int = set1.length - 1;
        var limit2: Int = set2.length - 1;
        var i2: Int = 1;
        while (i2 < limit2) {
            var second: Boolean = false;
            var symbol: Char = set2.charAt(i2);
            var i1: Int = 1;
            while (!second && i1 < limit1) {
                if (set1.charAt(i1) == symbol) {
                    set1.deleteCharAt(i1);
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
    def computeIntersection(set1: StringBuilder, set2: StringBuilder): StringBuilder = {
        var limit1: Int = set1.length - 1;
        var limit2: Int = set2.length - 1;
        var i1: Int = 1;
        while (i1 < limit1) {
            var first: Boolean = false;
            var symbol: Char = set1.charAt(i1);
            var i2: Int = 1;
            while (!first && i2 < limit2) {
                if (set2.charAt(i2) == symbol) {
                    first = true;
                }
                i2 += 1;
            }
            if (!first) {
                set1.deleteCharAt(i1);
                i1 -= 1;
                limit1 -= 1;
            }
            i1 += 1;
        }
        return set1;
    }

    // calcula a união (+) de dois conjuntos
    def computeUnion(set1: StringBuilder, set2: StringBuilder): StringBuilder = {
        var limit1: Int = set1.length - 1;
        var limit2: Int = set2.length - 1;
        var i2: Int = 1;
        while (i2 < limit2) {
            var second: Boolean = false;
            var symbol: Char = set2.charAt(i2);
            var i1: Int = 1;
            while (!second && i1 < limit1) {
                if (set1.charAt(i1) == symbol) {
                    second = true;
                }
                i1 += 1;
            }
            if (!second) {
                insert(symbol, set1, limit1);
                limit1 += 1;
            }
            i2 += 1;
        }
        return set1;
    }

    // insere um elemento de forma ordenada
    def insert(symbol: Char, set: StringBuilder, limit: Int) {
        for (i <- 1 until limit) {
            if (symbol < set.charAt(i)) {
                set.insert(i, symbol);
                return ;
            }
        }
        set.insert(limit, symbol);
    }

    // faz o parse de um bloco
    def parseBlock(expression: StringBuilder): StringBuilder = {
        var symbol: Char = expression.charAt(0);
        if (symbol != '(') {
            return StringBuilder.newBuilder;
        }
        expression.deleteCharAt(0);
        var result: StringBuilder = parseUnionOrDifference(expression);
        symbol = expression.charAt(0);
        expression.deleteCharAt(0);
        return result;
    }

    // faz o parse da operação '*'
    def parseIntersection(expression: StringBuilder): StringBuilder = {
        var value1: StringBuilder = parseSet(expression);
        while (expression.length > 0) {
            var symbol: Char = expression.charAt(0);
            if (symbol != '*') {
                return value1;
            }
            expression.deleteCharAt(0);
            var value2: StringBuilder = parseSet(expression);
            value1 = computeIntersection(value1, value2);
        }
        return value1;
    }

    // faz o parse de um conjunto
    def parseSet(expression: StringBuilder): StringBuilder = {
        var value: StringBuilder = parseBlock(expression);
        var result: StringBuilder = StringBuilder.newBuilder.append(value);
        if (expression.length > 0) {
            var symbol: Char = expression.charAt(0);
            if (symbol != '{') {
                return value;
            }
            do {
                symbol = expression.charAt(0);
                result.append(symbol);
                expression.deleteCharAt(0);
            } while (symbol != '}');
        }
        return result;
    }

    // faz o parse das operações '+' e '-'
    def parseUnionOrDifference(expression: StringBuilder): StringBuilder = {
        var value1: StringBuilder = parseIntersection(expression);
        while (expression.length > 0) {
            var symbol: Char = expression.charAt(0);
            if (symbol != '+' && symbol != '-') {
                return value1;
            }
            expression.deleteCharAt(0);
            var value2: StringBuilder = parseIntersection(expression);
            if (symbol == '+') {
                value1 = computeUnion(value1, value2);
            } else {
                value1 = computeDifference(value1, value2);
            }
        }
        return value1;
    }
}