import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="https://www.urionlinejudge.com.br/judge/en/problems/view/1762">1762 Sleds of Santa Claus</a>
 * 
 * @author ricardo.staroski
 */
public final class Main {

	public static void main(String[] args) throws IOException {
		final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder output = new StringBuilder(4096);
		for (int i = 0, testCases = Integer.parseInt(in.readLine()); i < testCases; i++) {
			final Map<String, Double> batch = new HashMap<>(4096);
			double totalWeight = 0;
			for (int j = 0, gifts = Integer.parseInt(in.readLine()); j < gifts; j++) {
				batch.put(in.readLine(), Double.parseDouble(in.readLine()));
			}
			double sledCapacity = Double.parseDouble(in.readLine());
			String item = in.readLine();
			int quantity = Integer.parseInt(in.readLine());
			for (; !"-".equals(item);) {
				Double metrics = batch.get(item);
				if (metrics != null) {
					totalWeight += (metrics * quantity);
				} else {
					output.append("NAO LISTADO: ").append(item).append("\n");
				}
				item = in.readLine();
				quantity = Integer.parseInt(in.readLine());
			}
			output.append(String.format(Locale.ROOT, "Peso total: %.2f kg\n", totalWeight));
			output.append("Numero de trenos: ").append((int) Math.ceil(totalWeight / sledCapacity)).append("\n\n");
		}
		System.out.printf("%s", output.toString());
	}
}