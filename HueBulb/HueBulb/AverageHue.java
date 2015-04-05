import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AverageHue {
	static float[] avgScreenHSB;
	static final int NEST_TIMER = 100;
	
	public static void main(String args[]) throws Exception {
		Robot r = new Robot();
//		sendColor(HSBtoPhilipHue(Color.RGBtoHSB(0, 0, 225, null)));
//		System.out.println(Arrays.toString(HSBtoPhilipHue(Color.RGBtoHSB(0, 0, 225, null))));
		int nestCounter = 0;
		while(true) {
			r.delay(100);
			BufferedImage img = r.createScreenCapture(new Rectangle(0, 45, 1435, 810));
			float[] avgHSV = avgImageHSL(img);
			System.out.println(Arrays.toString(avgHSV));
			sendColor(avgHSV);
			if (nestCounter++ >= NEST_TIMER) {
				NestController.updateTemp();
				nestCounter = 0;
			}
		}
	}
	
	public static void sendColor(float[] HSV) throws Exception{
		URL url = new URL("https://graph.api.smartthings.com/api/smartapps/installations/d7a29a5f-e184-46f7-89ec-fa559b95465e/color");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Authorization", "Bearer 2515bdb1-5cf3-44e7-b757-0402e0af43cc");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write(String.format("{\"hue\":%f, \"saturation\":%f, \"level\":%f}", HSV[0], HSV[1], HSV[2]));
		osw.flush();
		osw.close();
		System.err.println("HueBulb: " + connection.getResponseCode());
	}
	
	public static float[] avgImageHSL(BufferedImage img) {
		int rSum = 0;
		int gSum = 0;
		int bSum = 0;
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color c = new Color(img.getRGB(x, y));
				rSum += c.getRed();
				gSum += c.getGreen();
				bSum += c.getBlue();
			}
		}
		
		int rAvg = rSum / (height * width);
		int gAvg = gSum / (height * width);
		int bAvg = bSum / (height * width);
		
		float[] HSB = Color.RGBtoHSB(rAvg, gAvg, bAvg, null);
		avgScreenHSB = Arrays.copyOf(HSB, 3);
		return HSBtoPhilipHue(HSB);
	}
	
	public static float[] HSBtoPhilipHue(float[] HSB) {
		for (int i = 0; i < 3; i++) {
			HSB[i] = HSB[i] * 100;
		}
		HSB[2] *= 0.5f;
		return HSB;
	}
}
