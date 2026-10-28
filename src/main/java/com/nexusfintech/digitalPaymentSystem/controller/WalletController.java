package com.nexusfintech.digitalPaymentSystem.controller;

import com.nexusfintech.digitalPaymentSystem.dto.TransferRequest;
import com.nexusfintech.digitalPaymentSystem.model.WalletTransaction;
import com.nexusfintech.digitalPaymentSystem.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    // 1. POST /api/v1/wallet/transfer (Money Transfer API)
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, Object>> transfer(
            Authentication authentication, // ⭐️ Logged-in customer ka email SecurityContextHolder se aayega
            @Valid @RequestBody TransferRequest request
    ) {
        String senderEmail = authentication.getName();
        return ResponseEntity.ok(walletService.transferFunds(senderEmail, request));
    }

    // 2. GET /api/v1/wallet/balance (Check Wallet Balance)
    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getBalance(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(walletService.getBalance(email));
    }

    // 3. GET /api/v1/wallet/history (Passbook / Audit History)
    @GetMapping("/history")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<WalletTransaction>> getHistory(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(walletService.getTransactionHistory(email));
    }
}
