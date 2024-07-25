package com.unsa.eda.api.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.util.UriComponentsBuilder;

//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;


//import com.unsa.eda.service.Credentials;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

  final SecureRandom secureRandom = new SecureRandom();
  String token;

  class Credentials{
    public String getClientId() {
        return "8f6fcb6c81684d88b6f479c7f77dbe21";
    }


    public String getClientSecret() {
        return "91254e810a394a9bbc173e3f1a1e8d36";
    }

  }
  Credentials credentials;

  private String generateRandomString(int length) {
    byte[] randomBytes = new byte[length];
    secureRandom.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
}


  @GetMapping("/auth/login")
  public void login(HttpServletResponse response) throws IOException {

        String scope = "streaming user-read-email user-read-private";
        String state = generateRandomString(16);
        String REDIRECT_URI = "http://localhost:3000/auth/callback";

        String authUrl = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", credentials.getClientId())
                .queryParam("scope", scope)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("state", state)
                .toUriString();

        response.sendRedirect(authUrl);
    }
  @GetMapping("/auth/token")
  public String getToken(){
    return token;
  }


  @GetMapping("/auth/callback")
  public String authCallback(@RequestParam("code") String code) {

        String authHeader = "Basic " + Base64.getEncoder().encodeToString((credentials.getClientId() + ":" + credentials.getClientSecret()).getBytes(StandardCharsets.UTF_8));
        String redirectUri = "http://localhost:8080/auth/callback";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", authHeader);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
            "https://accounts.spotify.com/api/token",
            HttpMethod.POST,
            requestEntity,
            String.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Extract the access token from the response body
            // Assuming you have a method to extract it from the JSON response
            token = extractAccessToken(responseEntity.getBody());
            // Do something with the access token
            return "redirect:/";
        } else {
            // Handle error case
            return "error";
        }
    }

    private String extractAccessToken(String responseBody) {
        // Implement a method to parse the response body and extract the access token
        // You can use a JSON library like Jackson or org.json for this
        // Example with org.json:
        // JSONObject jsonObject = new JSONObject(responseBody);
        // return jsonObject.getString("access_token");
        return ""; // Replace with actual implementation
    }

}
