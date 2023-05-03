package com.sanket.tasksbasicapis.tasks.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class TaskResponseDto {
    Integer id;
    String name;
    Date dueDate;
    Boolean isCompleted;
}
