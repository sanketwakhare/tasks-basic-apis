package com.sanket.tasksbasicapis.tasks;

import com.sanket.tasksbasicapis.tasks.exceptions.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class TaskService {

    private final List<Task> tasks;
    private Integer id;

    public TaskService() {
        tasks = new ArrayList<>();
        id = 1;
    }

    public List<Task> getTasks(TaskFilter taskFilter) {
        if (taskFilter != null) {
            return taskFilter.filterTasks(tasks);
        }
        return tasks;
    }

    public Task getTaskById(Integer taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        throw new TaskNotFoundException(taskId);
    }

    public Task addTask(String name, Date dueDate) {
        // validate task name and due date
        TaskValidator.validateTaskName(name, false);
        TaskValidator.validateTaskDueDate(dueDate, false);

        Task task = new Task(id++, name);
        task.setDueDate(dueDate);
        task.setIsCompleted(false);
        tasks.add(task);
        return task;
    }

    public Task updateTask(Integer taskId, Date dueDate, Boolean isCompleted) {
        Task task = getTaskById(taskId);
        // validate task due date
        if (!Objects.isNull(dueDate)) {
            TaskValidator.validateTaskDueDate(dueDate, true);
            task.setDueDate(dueDate);
        }
        // validate task isCompleted status
        if (!Objects.isNull(isCompleted)) {
            task.setIsCompleted(isCompleted);
        }
        return task;
    }

    public Boolean deleteTaskById(Integer taskId) {
        Task task = getTaskById(taskId);
        return tasks.remove(task);
    }

    public Boolean deleteTasks(Boolean isCompletedStatus) {
        if (isCompletedStatus != null) {
            Stream<Task> completedTasksStream = tasks.stream().filter(task -> task.getIsCompleted().equals(isCompletedStatus));
            List<Task> taskToDelete = completedTasksStream.toList();
            if (taskToDelete.size() > 0) {
                taskToDelete.forEach(tasks::remove);
                return true;
            }
        }
        return false;
    }
}
