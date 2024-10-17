package com.mentorship.mentorship.mapper;

import com.mentorship.mentorship.dto.UserDto;
import com.mentorship.mentorship.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDtos(List<User> users);
    User mapUserForUpdate(@MappingTarget User dbRequest, User user);
}

