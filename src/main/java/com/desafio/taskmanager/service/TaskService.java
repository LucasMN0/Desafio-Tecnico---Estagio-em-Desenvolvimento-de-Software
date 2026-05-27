package com.desafio.taskmanager.service;

import com.desafio.taskmanager.dto.TaskRequestDTO;
import com.desafio.taskmanager.dto.TaskResponseDTO;
import com.desafio.taskmanager.dto.TaskStatusUpdateDTO;
import com.desafio.taskmanager.entity.Task;
import com.desafio.taskmanager.enums.TaskStatus;
import com.desafio.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponseDTO criar(TaskRequestDTO dto) {
        Task task = toEntity(dto);
        Task taskSalva = taskRepository.save(task);
        return toResponseDTO(taskSalva);
    }

    private Task obterTaskPorId(Long id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));
    }

    public TaskResponseDTO buscarPorId(Long id) {
        Task task = obterTaskPorId(id);
        calcularStatusAtrasado(task);
        return toResponseDTO(task);
    }

    public List<TaskResponseDTO> listarTodas(String status, String search) {
        List<Task> tarefas;

        if (status == null && search == null) { // Sem filtro
            tarefas = taskRepository.findAll();
        } else if (status != null && search == null) { // Filtrar por status
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            tarefas = taskRepository.findByStatus(taskStatus);
        } else if (status == null && search != null) { // Buscar por título ou descrição
            tarefas = taskRepository.findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(search, search);
        } else { // Filtrar por status E buscar por título ou descrição
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            tarefas = taskRepository.findByStatusAndTituloContainingIgnoreCaseOrStatusAndDescricaoContainingIgnoreCase(
                    taskStatus, search, taskStatus, search
            );
        }

        List<TaskResponseDTO> resultado = new ArrayList<>();
        for (Task task : tarefas) {
            calcularStatusAtrasado(task);
            TaskResponseDTO dto = toResponseDTO(task);
            resultado.add(dto);
        }
        return resultado;
    }

    public TaskResponseDTO editar(Long id, TaskRequestDTO dto) { // Altera Título, Descrição, Status, Responsável e DataLimite
        Task task = obterTaskPorId(id);

        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setStatus(dto.getStatus());
        task.setResponsavel(dto.getResponsavel());
        task.setDataLimite(dto.getDataLimite());

        Task taskAtualizada = taskRepository.save(task);
        return toResponseDTO(taskAtualizada);
    }

    public TaskResponseDTO alterarStatus(Long id, TaskStatusUpdateDTO dto) { // Altera somente Status
        Task task = obterTaskPorId(id);

        task.setStatus(dto.getStatus());

        Task taskAtualizada = taskRepository.save(task);
        return toResponseDTO(taskAtualizada);
    }

    private void calcularStatusAtrasado(Task task) {
        if (task.getDataLimite() != null){
            LocalDate hoje = LocalDate.now();
            if(task.getDataLimite().isBefore(hoje) && task.getStatus() != TaskStatus.CONCLUIDO && task.getStatus() != TaskStatus.ATRASADO){
                task.setStatus(TaskStatus.ATRASADO);
                taskRepository.save(task);
            }
        }
    }

    private Task toEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setStatus(dto.getStatus());
        task.setResponsavel(dto.getResponsavel());
        task.setDataLimite(dto.getDataLimite());
        return task;
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setDescricao(task.getDescricao());
        dto.setStatus(task.getStatus());
        dto.setResponsavel(task.getResponsavel());
        dto.setDataCriacao(task.getDataCriacao());
        dto.setDataLimite(task.getDataLimite());
        return dto;
    }
}
