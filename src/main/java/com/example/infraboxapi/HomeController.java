package com.example.infraboxapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${login.url}")
    private String loginUrl;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String message = "<html><head><style>" +
                "body { background-color: #1c1b22; color: #ffffff; font-family: Arial, sans-serif; }" +
                "h1 { color: #ffa500; }" +
                "p { font-size: 18px; }" +
                "a { color: #ffa500; text-decoration: none; }" +
                "a:hover { text-decoration: underline; }" +
                "</style></head>" +
                "<body>" +
                "<h1>Welcome to the Infrabox API!</h1>" +
                "<p>This is the backend for the Infrabox project. If you are reading this message, it means the API is operational.</p>" +
                " <a href=\"" + loginUrl + "\">click here to log in</a>" +
                "</body></html>";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}


