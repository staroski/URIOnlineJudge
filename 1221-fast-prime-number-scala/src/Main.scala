import scala.io.StdIn

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1221">1221 Fast Prime Number</a>
 *
 * @author ricardo.staroski
 */
object Main {

    def main(args: Array[String]) {
        val testCases = StdIn.readInt();
        for (i <- 1 to testCases) {
            println(if (isPrime(StdIn.readInt())) "Prime" else "Not Prime");
        }
    }

    def isPrime(n: Int): Boolean = {
        if (n < 2) {
            return false;
        }
        if (n < 4) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        var i: Int = 5;
        while (i * i <= n) {
            if (n % i == 0) {
                return false;
            }
            if (n % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        return true;
    }
}