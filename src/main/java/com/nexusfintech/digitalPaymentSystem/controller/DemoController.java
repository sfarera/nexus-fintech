package com.nexusfintech.digitalPaymentSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    // 1. Sirf ROLE_CUSTOMER access kar sakta hai
    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, String>> customerEndpoint() {
        return ResponseEntity.ok(Map.of(
                "message", "Hello Customer! Aap apna wallet aur payment dekh sakte hain.",
                "access", "ROLE_CUSTOMER ONLY"
        ));
    }

    // 2. Sirf ROLE_MERCHANT access kar sakta hai
    @GetMapping("/merchant")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<Map<String, String>> merchantEndpoint() {
        return ResponseEntity.ok(Map.of(
                "message", "Hello Merchant! Aap QR code aur merchant settlements dekh sakte hain.",
                "access", "ROLE_MERCHANT ONLY"
        ));
    }

    // 3. Sirf ROLE_ADMIN access kar sakta hai
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> adminEndpoint() {
        return ResponseEntity.ok(Map.of(
                "message", "Hello Admin! Aap compliance records aur fraud control dekh sakte hain.",
                "access", "ROLE_ADMIN ONLY"
        ));
    }

    // 4. Any authenticated user: Current logged-in user ki detail dekhna
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "username", auth.getName(),
                "authorities", auth.getAuthorities()
        ));
    }
}
