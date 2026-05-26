package com.desafio.taskmanager.entity;

import com.desafio.taskmanager.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "Descrição não pode exceder 500 caracteres")
    private String descricao;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Size(max = 100, message = "Responsável não pode exceder 100 caracteres")
    private String responsavel;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    private LocalDate dataLimite;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
