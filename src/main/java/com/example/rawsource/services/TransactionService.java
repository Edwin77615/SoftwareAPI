package com.example.rawsource.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.rawsource.entities.Transaction;
import com.example.rawsource.entities.dto.transaction.TransactionDto;
import com.example.rawsource.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    public Page<TransactionDto> listTransactions(Pageable pageable) {
        return repository.findAllByOrderByTimestampDesc(pageable)
                .map(this::toDto);
    }

    public Page<TransactionDto> listTransactionsBefore(LocalDateTime timestamp, Pageable pageable) {
        return repository.findByTimestampBeforeOrderByTimestampDesc(timestamp, pageable)
                .map(this::toDto);
    }

    private TransactionDto toDto(Transaction t) {
        return new TransactionDto(t.getId(), t.getProductId(), t.getQuantity(), t.getType(), t.getTimestamp());
    }
}