package com.desafio.taskmanager.dto;

import com.desafio.taskmanager.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateDTO {

    @NotNull(message = "Status é obrigatório")
    private TaskStatus status;
}
