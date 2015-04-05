import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class NestController {
	static final int MEDIAN_TEMPERATURE = 72;
	static final int MAX_CHANGE_TEMPERATURE = 8;
	static final int NEUTRAL_HUE = 120;
	
	public static void updateTemp() throws Exception {
		int newTemp = Math.round(MEDIAN_TEMPERATURE + warmness() * MAX_CHANGE_TEMPERATURE);
		System.out.printf("newTemp: %d\n", newTemp);
		URL url = new URL("https://developer-api.nest.com/devices/thermostats/8HvbM0cjhwBvze8oTsLuOTaB5R80IUDY?auth=c.D1MRLWIewq4qpIGmoCv6WQf8CRghIaZWGanOHalsGsEifSknnNWVj4OiSFRnVXi5pKMbUOfar1H9iIMSDXfQnmu7bwToEEVLYdEDG8jrXaeNfo670w5rJd8j6HKMgG52w98s2hDb7Z0dhePm");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write(String.format("{\"target_temperature_f\": %d}", newTemp));
		osw.flush();
		osw.close();
		System.err.println("Nest: " + connection.getResponseCode());
	}
	
	/** Returns -1 for coldest color, 1 for warmest. **/
	public static float warmness() {
		float currentHue = NEUTRAL_HUE;
		System.out.println("#########"+AverageHue.avgScreenHSB[0]);
		try {
			currentHue = AverageHue.avgScreenHSB[0] * 360;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		if (currentHue >= 0 && currentHue <= 240) {
			return (120f - currentHue) / 120f;
		}
		return (currentHue - 300f) / 60f;
	}

}
