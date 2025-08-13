package com.andrew.todolist_api.service;

import com.andrew.todolist_api.entities.Task;
import com.andrew.todolist_api.exceptions.ResourceNotFoundException;
import com.andrew.todolist_api.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));

        return task;
    }

    public Task createTask(Task task){
        Task savedTask = taskRepository.save(task);
        System.out.println("Task salva com sucesso!");
        return savedTask;
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = findById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task deleteTask = findById(id);
        taskRepository.delete(deleteTask);
        System.out.println("Tarefa deletada com sucesso!");
    }
}
