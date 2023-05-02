package com.sanket.tasksbasicapis.tasks;

import com.sanket.tasksbasicapis.common.ErrorResponse;
import com.sanket.tasksbasicapis.common.Message;
import com.sanket.tasksbasicapis.tasks.dtos.CreateTaskRequestDto;
import com.sanket.tasksbasicapis.tasks.dtos.UpdateTaskRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.sanket.tasksbasicapis.services.DateService.isValid;
import static com.sanket.tasksbasicapis.services.DateService.toDate;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    List<Task> tasks = new ArrayList<>();
    int nextTaskId = 1;

    // get a task by id
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable(name = "taskId") Integer taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ErrorResponse("Task not found"), HttpStatus.NOT_FOUND);
    }

    // add a new task
    @PostMapping("")
    public ResponseEntity<?> addTask(@RequestBody CreateTaskRequestDto taskDto) {
        if (taskDto == null) {
            return new ResponseEntity<>(new ErrorResponse("Task cannot be null"), HttpStatus.BAD_REQUEST);
        }
        if (taskDto.getName() == null || taskDto.getName().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Task name cannot be null or empty"), HttpStatus.BAD_REQUEST);
        }
        if (taskDto.getDueDate() == null || taskDto.getDueDate().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Task due date cannot be null or empty"), HttpStatus.BAD_REQUEST);
        }
        if (!isValid(taskDto.getDueDate())) {
            return new ResponseEntity<>(new ErrorResponse("Task due date is not valid. Please specify date format in yyyy-MM-dd"), HttpStatus.BAD_REQUEST);
        }

        // create new task
        Task task = new Task();
        task.setId(nextTaskId++);
        task.setName(taskDto.getName());
        task.setDueDate(toDate(taskDto.getDueDate()));
        task.setIsCompleted(taskDto.getIsCompleted());
        tasks.add(task);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    // update a task
    @PatchMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequestDto taskDto,
                                        @PathVariable(name = "taskId") Integer taskId) {
        Task existingTask = null;
        if (taskDto == null) {
            return new ResponseEntity<>(new ErrorResponse("Task cannot be null"), HttpStatus.BAD_REQUEST);
        }
        if (taskId == null) {
            return new ResponseEntity<>(new ErrorResponse("Task id cannot be null"), HttpStatus.BAD_REQUEST);
        } else {
            for (Task task : tasks) {
                if (task.getId().equals(taskId)) {
                    existingTask = task;
                    break;
                }
            }
        }
        if (existingTask == null) {
            return new ResponseEntity<>(new ErrorResponse("Task not found"), HttpStatus.NOT_FOUND);
        }
        if (taskDto.getDueDate() == null || taskDto.getDueDate().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Task due date cannot be null or empty"), HttpStatus.BAD_REQUEST);
        }
        if (!isValid(taskDto.getDueDate())) {
            return new ResponseEntity<>(new ErrorResponse("Task due date is not valid. Please specify date format in yyyy-MM-dd"), HttpStatus.BAD_REQUEST);
        }
        if (taskDto.getIsCompleted() == null) {
            return new ResponseEntity<>(new ErrorResponse("Task isCompleted cannot be null"), HttpStatus.BAD_REQUEST);
        }

        existingTask.setDueDate(toDate(taskDto.getDueDate()));
        existingTask.setIsCompleted(taskDto.getIsCompleted());
        return new ResponseEntity<>(new Message("Task " + existingTask.id + " updated successfully"), HttpStatus.OK);
    }

    // delete a single task by id
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable(name = "taskId", required = false) Integer taskId) {

        Task existingTask = null;
        if (taskId == null) {
            return new ResponseEntity<>(new ErrorResponse("Task id cannot be null"), HttpStatus.BAD_REQUEST);
        } else {
            for (Task task : tasks) {
                if (task.getId().equals(taskId)) {
                    existingTask = task;
                    break;
                }
            }
        }
        if (existingTask == null) {
            return new ResponseEntity<>(new ErrorResponse("Task not found"), HttpStatus.NOT_FOUND);
        }
        tasks.remove(existingTask);
        return new ResponseEntity<>(new Message("Task " + existingTask.id + " deleted successfully"), HttpStatus.OK);
    }

    // bulk delete tasks based on completed status
    @DeleteMapping("")
    public ResponseEntity<?> deleteTasks(@RequestParam(name = "completed") Boolean completed) {

        // give preference to completed param
        if (completed != null) {
            Stream<Task> completedTasksStream = tasks.stream().filter(task -> task.getIsCompleted().equals(completed));
            completedTasksStream.toList().forEach(task -> tasks.remove(task));
            String isCompleted = completed ? "completed" : "incomplete";
            return new ResponseEntity<>(new Message("All " + isCompleted + " tasks are deleted successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse("completed attribute must be specified"), HttpStatus.BAD_REQUEST);
    }

    // filter tasks based on sort and completed parameter
    @GetMapping("")
    public ResponseEntity<?> filterTasks(@RequestParam(name = "completed", required = false) Boolean completed,
                                         @RequestParam(name = "sort", required = false) String sortType) {

        if (completed == null && sortType == null) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }

        List<Task> filteredTasks = new ArrayList<>();
        if (completed != null) {
            for (Task task : tasks) {
                if (task.getIsCompleted().equals(completed)) {
                    filteredTasks.add(task);
                }
            }
        } else {
            filteredTasks.addAll(tasks);
        }

        if (sortType != null) {
            if (sortType.equals("dateAsc")) {
                filteredTasks.sort(Comparator.comparing(Task::getDueDate));
            } else if (sortType.equals("dateDesc")) {
                filteredTasks.sort((t1, t2) -> t2.getDueDate().compareTo(t1.getDueDate()));
            } else {
                return new ResponseEntity<>(new ErrorResponse("Invalid sort type. Please specify sort type as dateAsc or dateDesc"), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

}
