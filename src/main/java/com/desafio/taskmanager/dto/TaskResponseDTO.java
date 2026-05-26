package com.desafio.taskmanager.dto;

import com.desafio.taskmanager.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private TaskStatus status;

    private String responsavel;

    private LocalDateTime dataCriacao;

    private LocalDate dataLimite;
}
