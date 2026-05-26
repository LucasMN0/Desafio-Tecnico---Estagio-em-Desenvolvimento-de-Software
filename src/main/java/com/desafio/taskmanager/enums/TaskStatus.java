package com.desafio.taskmanager.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    A_FAZER,
    EM_PROGRESSO,
    ATRASADO,
    CONCLUIDO;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        // Se vier vazio ou null, lança erro claro
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Status é obrigatório e não pode estar vazio");
        }
        
        // Tenta converter para uppercase
        try {
            return TaskStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            // Se não encontrar o status, lança erro claro
            throw new IllegalArgumentException("Status '" + value + "' é inválido. Valores aceitos: A_FAZER, EM_PROGRESSO, ATRASADO, CONCLUIDO");
        }
    }
}