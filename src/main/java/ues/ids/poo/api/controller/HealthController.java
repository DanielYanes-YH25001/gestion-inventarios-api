package ues.ids.poo.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ping")
@RequiredArgsConstructor
public class HealthController {
    @GetMapping
    public Map<String, String> ping() {
        return Map.of("message", "Pong!");
    }
}
