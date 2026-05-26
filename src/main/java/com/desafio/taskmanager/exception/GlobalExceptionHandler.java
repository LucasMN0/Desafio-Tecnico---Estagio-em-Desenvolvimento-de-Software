package com.desafio.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(RuntimeException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return erro;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erro = new HashMap<>();
        String mensagem = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        erro.put("erro", mensagem);
        return erro;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex) {
        Map<String, String> erro = new HashMap<>();
        if (ex.getMessage() != null && ex.getMessage().contains("TaskStatus")) {
            erro.put("erro", "Status inválido. Valores aceitos: A_FAZER, EM_PROGRESSO, ATRASADO, CONCLUIDO");
        } else {
            erro.put("erro", "Valor inválido: " + ex.getMessage());
        }
        return erro;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> erro = new HashMap<>();
        String mensagem = ex.getMessage();

        if (mensagem != null && mensagem.contains("TaskStatus")) {
            erro.put("erro", "Status inválido. Valores aceitos: A_FAZER, EM_PROGRESSO, ATRASADO, CONCLUIDO");
        } else if (mensagem != null && mensagem.contains("JSON parse error")) {
            erro.put("erro", "Formato JSON inválido. Verifique os campos obrigatórios");
        } else {
            erro.put("erro", "Requisição inválida");
        }
        return erro;
    }
}
