package com.mentorship.mentorship.mapper;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {
    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDtos(List<Task> users);

    Task mapUserForUpdate(@MappingTarget Task dbTask, Task task);
}

