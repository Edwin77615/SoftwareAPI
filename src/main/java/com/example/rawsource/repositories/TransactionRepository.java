package com.example.rawsource.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rawsource.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    <S extends Transaction> S save(S entity);
    
    Page<Transaction> findAllByOrderByTimestampDesc(Pageable pageable);

    Page<Transaction> findByTimestampBeforeOrderByTimestampDesc(LocalDateTime timestamp, Pageable pageable);
}