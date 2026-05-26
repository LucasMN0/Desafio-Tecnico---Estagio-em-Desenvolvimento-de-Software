package com.desafio.taskmanager.repository;

import com.desafio.taskmanager.entity.Task;
import com.desafio.taskmanager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String titulo, String descricao);

    List<Task> findByStatusAndTituloContainingIgnoreCaseOrStatusAndDescricaoContainingIgnoreCase(
            TaskStatus status1, String titulo, TaskStatus status2, String descricao
    );
}
