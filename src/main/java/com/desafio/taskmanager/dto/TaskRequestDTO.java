package com.desafio.taskmanager.dto;

import com.desafio.taskmanager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100)
    private String titulo;

    @Size(max = 500)
    private String descricao;

    @NotNull
    private TaskStatus status;

    @Size(max = 100)
    private String responsavel;

    private LocalDate dataLimite;
}
