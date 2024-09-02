package csgoFloat;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class Listing {
    @SerializedName("item_name")
    private String itemName;
    // Add other fields you need from the response JSON

    // Generate getters and setters for the fields
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    // Generate getters and setters for other fields
}

public class CSFloatMarketAPI {

    public static void main(String[] args) {
        try {
            // Set up the URL and query parameters
            String apiUrl = "https://csfloat.com/api/v1/listings";
            int page = 0;
            int limit = 50;
            String sortBy = "best_deal";
            // Other query parameters can be added as needed

            // Construct the URL with query parameters
            URL url = new URL(apiUrl + "?page=" + page + "&limit=" + limit + "&sort_by=" + sortBy);
            // Add more query parameters as needed

            // Create HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println("Response code: " + connection.getResponseCode());

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON using Gson
            Gson gson = new Gson();
            Listing[] listings = gson.fromJson(response.toString(), Listing[].class);

            // Access the listings
            for (Listing listing : listings) {
                //System.out.println("Item Name: " + listing.getItemName());
                // Access other fields as needed
            }

            // Close connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
