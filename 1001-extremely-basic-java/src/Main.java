import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1001">1001 Extremely Basic</a>
 * 
 * @author ricardo.staroski
 */
public class Main {

	public static void main(String[] args) throws IOException {
		final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("X = " + (Integer.parseInt(in.readLine()) + Integer.parseInt(in.readLine())));
	}
}