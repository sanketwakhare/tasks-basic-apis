package com.sanket.tasksbasicapis.tasks.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CreateTaskRequestDto {
    String name;
    Date dueDate;
}
