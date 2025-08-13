package com.andrew.todolist_api.controllers;

import com.andrew.todolist_api.entities.Task;
import com.andrew.todolist_api.repositories.TaskRepository;
import com.andrew.todolist_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Gerencia as operações de tarefas.")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @Operation(
            summary = "Cria uma nova tarefa",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova tarefa",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Task.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Tarefa criada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida (corpo da requisição com dados incorretos)"
                    )
            }
    )
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }


    @Operation(summary = "Lista todas as tarefas")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> listTasks = taskService.findAll();
        return ResponseEntity.ok(listTasks);
    }


    @Operation(
            summary = "Encontra uma tarefa pelo ID dela",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarefa encontrada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarefa não encontrada"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(
            @Parameter(description = "ID da tarefa a ser buscada", example = "1")
            @PathVariable Long id) {
        Task taskFound = this.taskService.findById(id);
        return ResponseEntity.ok(taskFound);
    }

    @Operation(
            summary = "Atualiza uma tarefa atraves do ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarefa atualizada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarefa não encontrada"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
            summary = "Excluir tarefa pelo seu ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Tarefa deletada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarefa não encontrada"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID da tarefa a ser deletada", example = "1")
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
