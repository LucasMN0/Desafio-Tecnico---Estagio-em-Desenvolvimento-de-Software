package com.desafio.taskmanager.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    A_FAZER,
    EM_PROGRESSO,
    ATRASADO,
    CONCLUIDO;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        return TaskStatus.valueOf(value.toUpperCase());
    }
}