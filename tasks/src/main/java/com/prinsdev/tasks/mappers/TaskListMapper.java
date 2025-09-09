package com.prinsdev.tasks.mappers;

import com.prinsdev.tasks.domain.dto.TaskListDto;
import com.prinsdev.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto (TaskListDto taskListDto);
    TaskListDto toDto (TaskList taskList);
}
