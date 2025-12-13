// Package
package dk.project.server;

// Imports
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailSetupAPI {

    // Attributes
    private static final String API_KEY = "@Guacamoleboy for API Key";
    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";
    private static final int TIMEOUT_MS = 10000;

    // _______________________________________________________________________________

    public static boolean sendMail(String to, String subject, String body) {

        // Timeout setup to prevent hang
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(TIMEOUT_MS))
                .setConnectTimeout(Timeout.ofMilliseconds(TIMEOUT_MS))
                .setResponseTimeout(Timeout.ofMilliseconds(TIMEOUT_MS))
                .build();

        // Try-catch using the Timeout Config Setup
        try (CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build()) {

            // Initial API Setup
            HttpPost post = new HttpPost(API_URL);
            post.addHeader("Content-Type", "application/json");
            post.addHeader("api-key", API_KEY);

            // Sender info
            Map<String, String> sender = new HashMap<>();
            sender.put("name", "Fog");
            sender.put("email", "fog@travlr.dk");

            // Recipient
            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", to);
            List<Map<String, String>> toList = new ArrayList<>();
            toList.add(recipient);

            // Payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("sender", sender);
            payload.put("to", toList);
            payload.put("subject", subject);
            payload.put("textContent", body);

            // JSON (Required)
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);
            post.setEntity(new StringEntity(json, org.apache.hc.core5.http.ContentType.APPLICATION_JSON)); // UTF-8 Danish

            // Execute request
            client.execute(post, response -> {
                int status = response.getCode();

                System.out.println("HTTP status: " + status); // Debug

                InputStream bodyStream = response.getEntity().getContent();
                JsonNode responseJson = mapper.readTree(bodyStream);

                System.out.println("API respons: " + responseJson.toPrettyString()); // Debug

                if (status >= 200 && status < 300) {
                    System.out.println("Mail sendt via MailSetupAPI til: " + to); // Debug
                } else {
                    System.err.println("Fejl ved MailSetupAPI til " + to); // Debug
                }

                return null;

            });

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

} // MailSetupAPI end