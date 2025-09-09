package com.prinsdev.tasks.services.impl;

import com.prinsdev.tasks.domain.entities.Task;
import com.prinsdev.tasks.domain.entities.TaskList;
import com.prinsdev.tasks.domain.entities.TaskPriority;
import com.prinsdev.tasks.domain.entities.TaskStatus;
import com.prinsdev.tasks.repositories.TaskListRepository;
import com.prinsdev.tasks.repositories.TaskRepository;
import com.prinsdev.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(task.getId() != null){
            throw new IllegalArgumentException("Task Already has an ID");
        }
        if(task.getTitle() == null || task.getTitle().isBlank()){
        throw new IllegalArgumentException("Task Title is required");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("Invalid task list ID"));

        LocalDateTime now = LocalDateTime.now();

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now

        );

        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskID) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskID);
    }

    @Transactional
    @Override
    public Task updateeTask(UUID taskListId, UUID taskID, Task task) {
        if(null == task.getId()){
            throw new IllegalArgumentException("Task must have an ID");
        }
        if(!Objects.equals(taskID, task.getId())){
            throw new IllegalArgumentException("Task IDs don't match");
        }
        if(null == task.getPriority()){
            throw new IllegalArgumentException("Task priority is required");
        }
        if(null == task.getStatus()){
            throw new IllegalArgumentException("Task status is required");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskID)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskID) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskID);
    }

}
