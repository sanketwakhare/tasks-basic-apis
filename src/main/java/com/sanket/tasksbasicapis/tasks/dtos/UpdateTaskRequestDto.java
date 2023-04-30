package com.sanket.tasksbasicapis.tasks.dtos;

public class UpdateTaskRequestDto {
    String dueDate;
    Boolean isCompleted;

    UpdateTaskRequestDto() {
    }

    UpdateTaskRequestDto(String dueDate, Boolean isCompleted) {
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
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
