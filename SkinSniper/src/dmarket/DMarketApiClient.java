package dmarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SignatureException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.time.Instant;
import java.nio.charset.StandardCharsets;

public class DMarketApiClient {

    private static final String API_KEY = "4a8cedd663ded904e7e12883b5c344b4d483897366c847c43b1baac94ee47cfd"; // Replace with your API key
    private static final String SECRET_KEY = "d62b1ad411abd7ee2e5cbd435577045d0aeea2cbfbc1751274ec0cd086617d814a8cedd663ded904e7e12883b5c344b4d483897366c847c43b1baac94ee47cfd"; // Replace with your secret key

    public static void main(String[] args) {
        try {
            // Example endpoint, replace with your desired endpoint
            String endpoint = "https://api.dmarket.com/exchange/v1/market/items";

            String apiKey = API_KEY;
            String signDate = String.valueOf(Instant.now().getEpochSecond());
            String signature = generateSignature("GET", endpoint, "", signDate, SECRET_KEY);

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", apiKey);
            connection.setRequestProperty("X-Sign-Date", signDate);
            connection.setRequestProperty("X-Request-Sign", signature);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response Body: " + response.toString());

        } catch (IOException | SignatureException e) {
            e.printStackTrace();
        }
    }

    private static String generateSignature(String method, String path, String body, String timestamp, String secretKey)
            throws SignatureException {

        String unsignedString = method + path + body + timestamp;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("Ed25519");
            KeyPair keyPair = keyGen.generateKeyPair();

            Signature signing = Signature.getInstance("Ed25519");
            signing.initSign(keyPair.getPrivate());
            signing.update(unsignedString.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = signing.sign();

            return bytesToHex(signatureBytes);

        } catch (Exception e) {
            throw new SignatureException("Error while generating signature: " + e.getMessage());
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }
}
