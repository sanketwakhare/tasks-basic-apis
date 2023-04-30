package com.sanket.tasksbasicapis.tasks.dtos;

public class CreateTaskRequestDto {
    String name;
    String dueDate;

    Boolean isCompleted;

    public CreateTaskRequestDto() {
    }

    public CreateTaskRequestDto(String name, String dueDate, Boolean isCompleted) {
        this.name = name;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
