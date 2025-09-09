package com.prinsdev.tasks.controllers;

import com.prinsdev.tasks.domain.dto.TaskDto;
import com.prinsdev.tasks.domain.entities.Task;
import com.prinsdev.tasks.mappers.TaskMapper;
import com.prinsdev.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path ="/task-lists/{task_list_id}/tasks")
public class TasksController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListID) {
        return taskService.listTasks(taskListID).stream()
                .map(taskMapper::toDto)
                .toList();

    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListID,
                              @RequestBody TaskDto taskDto) {
        Task createdTask = taskService.createTask(taskListID, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(createdTask);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID taskListID,
                                     @PathVariable("task_id") UUID taskId
    ){
           return taskService.getTask(taskListID, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path ="/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListID,
                              @PathVariable("task_id") UUID taskId,
                              @RequestBody TaskDto taskDto
    ){
       Task updatedTask = taskService.updateeTask(
                taskListID,
                taskId,
                taskMapper.fromDto(taskDto));

       return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID taskListID,
                           @PathVariable("task_id") UUID taskId
    ){
        taskService.deleteTask(taskListID, taskId);
    }
}
