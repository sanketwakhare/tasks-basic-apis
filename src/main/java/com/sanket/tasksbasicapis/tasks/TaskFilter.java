package com.sanket.tasksbasicapis.tasks;

import com.sanket.tasksbasicapis.exceptions.InvalidSortTypeException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TaskFilter {

    Boolean isCompleted;
    String sortType;

    public List<Task> filterTasks(List<Task> tasks) {
        if (this.isCompleted != null) {
            tasks = tasks.stream().filter(task -> task.getIsCompleted() == this.isCompleted).collect(Collectors.toList());
        }
        if (this.sortType != null) {
            switch (TaskDueDateSortType.fromString(sortType)) {
                case DATE_ASC ->
                        tasks = tasks.stream().sorted(Comparator.comparing(Task::getDueDate)).collect(Collectors.toList());
                case DATE_DESC ->
                        tasks = tasks.stream().sorted(Comparator.comparing(Task::getDueDate).reversed()).collect(Collectors.toList());
                default -> throw new InvalidSortTypeException(this.sortType);
            }
        }
        return tasks;
    }
}
