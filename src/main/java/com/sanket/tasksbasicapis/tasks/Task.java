package com.sanket.tasksbasicapis.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    Integer id;
    String name;
    @Setter
    Date dueDate;
    @Setter
    Boolean isCompleted;

    public Task(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
