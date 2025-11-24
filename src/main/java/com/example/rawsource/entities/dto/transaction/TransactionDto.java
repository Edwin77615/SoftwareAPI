package com.example.rawsource.entities.dto.transaction;

import java.time.LocalDateTime;

public class TransactionDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String type;
    private LocalDateTime timestamp;

    public TransactionDto() {}

    public TransactionDto(Long id, Long productId, Integer quantity, String type, LocalDateTime timestamp) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}