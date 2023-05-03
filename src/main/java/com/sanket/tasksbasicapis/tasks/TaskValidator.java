package com.sanket.tasksbasicapis.tasks;

import com.sanket.tasksbasicapis.exceptions.InvalidDateException;
import com.sanket.tasksbasicapis.services.DateService;
import com.sanket.tasksbasicapis.tasks.exceptions.IllegalTaskArgumentException;

import java.util.Date;

public class TaskValidator {

    static void validateTaskDueDate(Date dueDate, Boolean isOptional) {
        if (!isOptional && dueDate == null) {
            throw new IllegalTaskArgumentException("Due date cannot be empty");
        }
        if (dueDate != null && dueDate.before(new Date())) {
            throw new InvalidDateException(DateService.toString(dueDate) + "- Due date cannot be in the past");
        }
    }

    static void validateTaskName(String name, Boolean isOptional) {
        if (!isOptional && name == null || name.isBlank()) {
            throw new IllegalTaskArgumentException("Task name cannot be empty");
        }
    }
}
