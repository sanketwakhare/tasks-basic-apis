package com.sanket.tasksbasicapis.tasks.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;

import javax.annotation.processing.SupportedOptions;
import java.util.Date;

@Data
public class UpdateTaskRequestDto {
    @Nullable
    Date dueDate;
    @Nullable
    Boolean isCompleted;
}
