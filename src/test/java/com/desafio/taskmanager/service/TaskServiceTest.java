package com.desafio.taskmanager.service;

import com.desafio.taskmanager.dto.TaskRequestDTO;
import com.desafio.taskmanager.dto.TaskResponseDTO;
import com.desafio.taskmanager.entity.Task;
import com.desafio.taskmanager.enums.TaskStatus;
import com.desafio.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskRequestDTO taskRequestDTO;
    private Task task;

    @BeforeEach
    void setup() {
        taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitulo("Tarefa de Teste");
        taskRequestDTO.setDescricao("Descrição da tarefa");
        taskRequestDTO.setStatus(TaskStatus.A_FAZER);
        taskRequestDTO.setResponsavel("João");
        taskRequestDTO.setDataLimite(LocalDate.now().plusDays(5));

        task = new Task();
        task.setId(1L);
        task.setTitulo("Tarefa de Teste");
        task.setDescricao("Descrição da tarefa");
        task.setStatus(TaskStatus.A_FAZER);
        task.setResponsavel("João");
        task.setDataLimite(LocalDate.now().plusDays(5));
        task.setDataCriacao(LocalDateTime.now());
    }

    @Test
    void teste_criar_tarefa_com_sucesso() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        TaskResponseDTO resultado = taskService.criar(taskRequestDTO);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("Tarefa de Teste", resultado.getTitulo());
        assertEquals(TaskStatus.A_FAZER, resultado.getStatus());
    }

    @Test
    void teste_buscar_tarefa_por_id_sucesso() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        TaskResponseDTO resultado = taskService.buscarPorId(1L);

        // Assert
        assertEquals(1L, resultado.getId());
        assertEquals("Tarefa de Teste", resultado.getTitulo());
        assertEquals("Descrição da tarefa", resultado.getDescricao());
    }

    @Test
    void teste_buscar_tarefa_inexistente_lanca_excecao() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            taskService.buscarPorId(999L);
        });
    }

    @Test
    void teste_listar_todas_as_tarefas() {
        // Arrange
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitulo("Tarefa 2");
        task2.setStatus(TaskStatus.EM_PROGRESSO);

        List<Task> tarefas = new ArrayList<>();
        tarefas.add(task);
        tarefas.add(task2);
        when(taskRepository.findAll()).thenReturn(tarefas);

        // Act
        List<TaskResponseDTO> resultado = taskService.listarTodas(null, null);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Tarefa de Teste", resultado.get(0).getTitulo());
        assertEquals("Tarefa 2", resultado.get(1).getTitulo());
    }

    @Test
    void teste_filtrar_por_status() {
        // Arrange
        List<Task> tarefas = new ArrayList<>();
        tarefas.add(task);
        when(taskRepository.findByStatus(TaskStatus.A_FAZER)).thenReturn(tarefas);

        // Act
        List<TaskResponseDTO> resultado = taskService.listarTodas("A_FAZER", null);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(TaskStatus.A_FAZER, resultado.get(0).getStatus());
    }

    @Test
    void teste_filtrar_por_texto() {
        // Arrange
        List<Task> tarefas = new ArrayList<>();
        tarefas.add(task);
        when(taskRepository.findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(
            "Tarefa de Teste", "Tarefa de Teste"
        )).thenReturn(tarefas);

        // Act
        List<TaskResponseDTO> resultado = taskService.listarTodas(null, "Tarefa de Teste");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Tarefa de Teste", resultado.get(0).getTitulo());
    }

    @Test
    void teste_calcular_status_atrasado() {
        // Arrange
        Task taskAtrasada = new Task();
        taskAtrasada.setId(1L);
        taskAtrasada.setTitulo("Tarefa Atrasada");
        taskAtrasada.setStatus(TaskStatus.EM_PROGRESSO);
        taskAtrasada.setDataLimite(LocalDate.now().minusDays(5));
        taskAtrasada.setDataCriacao(LocalDateTime.now().minusDays(10));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskAtrasada));

        // Act
        TaskResponseDTO resultado = taskService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Tarefa Atrasada", resultado.getTitulo());
        assertEquals(TaskStatus.ATRASADO, resultado.getStatus());
    }
}
