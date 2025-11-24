package com.example.rawsource.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rawsource.entities.dto.transaction.TransactionDto;
import com.example.rawsource.services.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<TransactionDto> list(
            @PageableDefault(size = 20, sort = "timestamp", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable
    ) {
        return service.listTransactions(pageable);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<TransactionDto> all(
            @RequestParam("lastTimestamp") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastTimestamp,
            @PageableDefault(size = 20, sort = "timestamp", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable
    ) {
        return service.listTransactionsBefore(lastTimestamp, pageable);
    }
}