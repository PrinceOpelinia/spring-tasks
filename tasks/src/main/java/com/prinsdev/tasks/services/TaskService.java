package com.prinsdev.tasks.services;

import com.prinsdev.tasks.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListI, Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskID);
    Task updateeTask(UUID taskListId, UUID taskID, Task task);
    void deleteTask(UUID taskListId, UUID taskID);
}
