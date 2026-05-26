package com.desafio.taskmanager.controller;

import com.desafio.taskmanager.dto.TaskRequestDTO;
import com.desafio.taskmanager.dto.TaskResponseDTO;
import com.desafio.taskmanager.dto.TaskStatusUpdateDTO;
import com.desafio.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO criar(@Valid @RequestBody TaskRequestDTO dto) {
        return taskService.criar(dto);
    }

    @GetMapping
    public List<TaskResponseDTO> listar(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search
    ) {
        return taskService.listarTodas(status, search);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO buscarPorId(@PathVariable Long id) {
        return taskService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO editar(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO dto
    ) {
        return taskService.editar(id, dto);
    }

    @PatchMapping("/{id}/status")
    public TaskResponseDTO alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusUpdateDTO dto
    ) {
        return taskService.alterarStatus(id, dto);
    }
}
