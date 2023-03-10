package com.fenonq.oriltask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Cryptocurrency {
    @Id
    private String id;
    private String curr1;
    private String curr2;
    private BigDecimal lprice;
    private LocalDateTime dateTime;
}
