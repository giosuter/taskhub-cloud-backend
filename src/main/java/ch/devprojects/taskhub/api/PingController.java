package ch.devprojects.taskhub.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Minimal health endpoint to verify context path and mapping.
 * - Localhost: http://localhost:8080/api/ping
 * - Production (WAR = taskhub-cloud.war): https://devprojects.ch/taskhub-cloud/api/ping
 * - Production (test WAR = taskhub-cloud-next.war): https://devprojects.ch/taskhub-cloud-next/api/ping
 */
@RestController
public class PingController {

    @GetMapping("/api/ping")
    public Map<String, Object> ping() {
        return Map.of(
            "ok", true,
            "app", "taskhub-cloud-backend",
            "time", System.currentTimeMillis()
        );
    }
}