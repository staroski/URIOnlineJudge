import scala.io.StdIn

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1672">1672 In Danger</a>
 *
 * @author ricardo.staroski
 */
object Main {

    def main(args: Array[String]) {
        var persons: Int = parseXYeZ(StdIn.readLine());
        while (persons > 0) {
            println(safePosition(persons));
            persons = parseXYeZ(StdIn.readLine());
        }
    }

    def parseXYeZ(value: String): Int = {
        val x = value.charAt(0).asDigit * 10;
        val y = value.charAt(1).asDigit;
        val z = math.pow(10, value.charAt(3).asDigit).toInt;
        return (x + y) * z;
    }

    def safePosition(n: Int): Int = {
        return 2 * (n - highestOneBit(n)) + 1;
    }

    def highestOneBit(n: Int): Int = {
        var i: Int = n;
        i = i | (i >> 1);
        i = i | (i >> 2);
        i = i | (i >> 4);
        i = i | (i >> 8);
        i = i | (i >> 16);
        return i - (i >> 1);
    }
}