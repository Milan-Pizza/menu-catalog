package app.milanpizza.menucatalog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TestUtil {

    public static <T> ResponseEntity<T> postJson(RestTemplate restTemplate,
                                                 String url, Object request, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String requestBody = new ObjectMapper().writeValueAsString(request);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            return restTemplate.postForEntity(url, entity, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JSON request", e);
        }
    }
}