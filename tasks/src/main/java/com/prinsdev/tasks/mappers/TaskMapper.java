package com.prinsdev.tasks.mappers;

import com.prinsdev.tasks.domain.dto.TaskDto;
import com.prinsdev.tasks.domain.entities.Task;


public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}

