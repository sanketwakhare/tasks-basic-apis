package com.sanket.tasksbasicapis.tasks;

import com.sanket.tasksbasicapis.common.ErrorResponse;
import com.sanket.tasksbasicapis.common.Message;
import com.sanket.tasksbasicapis.common.Response;
import com.sanket.tasksbasicapis.tasks.dtos.CreateTaskRequestDto;
import com.sanket.tasksbasicapis.tasks.dtos.TaskResponseDto;
import com.sanket.tasksbasicapis.tasks.dtos.UpdateTaskRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // get a task by id
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable(name = "taskId") Integer taskId) {
        Task task = taskService.getTaskById(taskId);
        TaskResponseDto taskResponseDto = getTaskResponseDto(task);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }

    // add a new task
    @PostMapping("")
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody CreateTaskRequestDto taskDto) {
        Task task = taskService.addTask(taskDto.getName(), taskDto.getDueDate());
        TaskResponseDto taskResponseDto = getTaskResponseDto(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);
    }

    // update a task
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody UpdateTaskRequestDto taskDto,
                                                      @PathVariable(name = "taskId") Integer taskId) {
        Task updatedTask = taskService.updateTask(taskId, taskDto.getDueDate(), taskDto.getIsCompleted());
        TaskResponseDto taskResponseDto = getTaskResponseDto(updatedTask);
        return ResponseEntity.ok(taskResponseDto);
    }

    // delete a single task by id
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Response> deleteTaskById(@PathVariable(name = "taskId", required = false) Integer taskId) {
        boolean isDeleted = taskService.deleteTaskById(taskId);
        if (isDeleted) {
            return ResponseEntity.ok(new Message("Task with id " + taskId + " deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Task with id " + taskId + " cannot be deleted"));
        }
    }

    // bulk delete tasks based on completed status
    @DeleteMapping("")
    public ResponseEntity<Response> deleteTasks(@RequestParam(name = "completed") Boolean isCompletedStatus) {
        boolean isDeleted = taskService.deleteTasks(isCompletedStatus);
        if (isDeleted) {
            String isCompleted = isCompletedStatus ? "completed" : "incomplete";
            return ResponseEntity.ok(new Message("All " + isCompleted + " tasks are deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No tasks found to delete"));
        }
    }

    // filter tasks based on sort and completed parameter
    @GetMapping("")
    public ResponseEntity<List<TaskResponseDto>> getTasks(@RequestParam(name = "completed", required = false) Boolean completed, @RequestParam(name = "sort", required = false) String sortType) {
        TaskFilter taskFilter = new TaskFilter(completed, sortType);
        List<Task> tasks = taskService.getTasks(taskFilter);
        List<TaskResponseDto> responseDto = getTaskListResponseDto(tasks);
        return ResponseEntity.ok(responseDto);
    }

    private TaskResponseDto getTaskResponseDto(Task task) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setName(task.getName());
        taskResponseDto.setDueDate(task.getDueDate());
        taskResponseDto.setIsCompleted(task.getIsCompleted());
        return taskResponseDto;
    }

    private List<TaskResponseDto> getTaskListResponseDto(List<Task> tasks) {
        List<TaskResponseDto> responseDto = new ArrayList<>();
        for (Task task : tasks) {
            responseDto.add(getTaskResponseDto(task));
        }
        return responseDto;
    }
}
