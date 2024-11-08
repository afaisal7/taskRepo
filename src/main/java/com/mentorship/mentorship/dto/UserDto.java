package com.mentorship.mentorship.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String designation;
}
