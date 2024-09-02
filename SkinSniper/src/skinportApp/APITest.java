package skinportApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class APITest {
	public static void main(String[] args) {

		// skinHandler

		// for getting items history price
		SkinHandler sH = new SkinHandler(callAPI("https://api.skinport.com/v1/sales/history"),callAPI("https://api.skinport.com/v1/items"));

		callAPI("GET https://api.skinscout.net/api/v1/itemPrices/★ Bayonet | Doppler (Factory New)?phase=Phase 2&token=???");
		
//		for (Skin skin : sH.getSkinList()) {
//			System.out.println(skin.getMarket_hash_name() + "Volume 90 days: " + skin.getLast_90_days().getVolume());
//		}
//		
//		// for getting items current price
//		sH.addCurrentSkin(callAPI("https://api.skinport.com/v1/items"));

		
		//Test!!
//		SkinHandler sH = new SkinHandler(
//				"[{\"market_hash_name\":\"AK-47 | Baroque Purple (Field-Tested)\",\"version\":null,\"currency\":\"EUR\",\"item_page\":\"https://skinport.com/item/ak-47-baroque-purple-field-tested\",\"market_page\":\"https://skinport.com/market?item=Baroque%20Purple&cat=Rifle&type=AK-47\",\"last_24_hours\":{\"min\":3.58,\"max\":3.58,\"avg\":3.58,\"median\":3.58,\"volume\":1},\"last_7_days\":{\"min\":3.58,\"max\":3.94,\"avg\":3.75,\"median\":3.73,\"volume\":4},\"last_30_days\":{\"min\":3.58,\"max\":5.07,\"avg\":4.11,\"median\":3.94,\"volume\":26},\"last_90_days\":{\"min\":3.41,\"max\":31,\"avg\":4.67,\"median\":4.2,\"volume\":116}},]");
//
//		sH.addCurrentSkin(
//				"[{\"market_hash_name\":\"AK-47 | Baroque Purple (Field-Tested)\",\"currency\":\"EUR\",\"suggested_price\":4.44,\"item_page\":\"https://skinport.com/item/ak-47-baroque-purple-field-tested\",\"market_page\":\"https://skinport.com/market?item=Baroque%20Purple&cat=Rifle&type=AK-47\",\"min_price\":3.8,\"max_price\":29.99,\"mean_price\":6.82,\"median_price\":5.62,\"quantity\":126,\"created_at\":1574875873,\"updated_at\":1701618015},]");
	}

	public static String callAPI(String endpoint) {
		try {
			// Your API key and secret
			String apiKey = "174e9aceda5c4415b6df58149f81b137";
			String apiSecret = "MNN2EUmz4tmgO0G+jzvc6EIR1mBP7fY1lWeyve2wYjRw9t+oPlP5KOY5z37mtoynEaKLwvHGrwndfVStRZ1dig==";

			// Combine API key and secret
			String credentials = apiKey + ":" + apiSecret;

			// Encode credentials to Base64
			String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

			// Create URL object
			URL url = new URL(endpoint);

			// Open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set request method
			connection.setRequestMethod("GET");

			// Set Authorization header
			connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

			// Get response code
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			// Read response
			BufferedReader reader;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

//          Print response
			System.out.println("Response: " + response.toString());
			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR ERROR ERROR ERROR (DID NOT WORK)";
	}
}