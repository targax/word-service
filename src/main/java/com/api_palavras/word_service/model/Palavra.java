package com.api_palavras.word_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_palavras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String termo;
}
