# Spring Boot - Task APIs v2

## Development

### Test API

- v1 -> There is [a postman collection](./task_manager_apis.postman_collection.json) to test the API
- v2 -> There is [a postman collection](./task_manager_apis_v2.postman_collection.json) to test the API

## Assignment

### 03. Make a Task Manager (only  Controller) (v1)

#### Project Description

Make a Task Manager with the following features:

- Create a task
    - Error if name is missing
    - Error if due date is missing or invalid (before today)
- List all tasks
- Fetch a task by id
    - Respond with 404 if task is not found
- Update a task (status and due date can be updated)
    - Respond with 404 if task is not found
    - > HINT: `@PatchMapping("/{id}")` can be used to update a task by id
- Delete a task
    - Respond with 404 if task is not found
    - > HINT: `@DeleteMapping("/{id}")` can be used to delete a task by id

#### Submission Requirements

- For this project, you can write all the code inside `TasksController`
- Separating out Services and Repository not required for this assignment
- The tasks are stored in an ArrayList in the controller itself
    - Tasks list will get reset on every server restart; which is acceptable for this assignment

##### How to submit

- Create a new repository on your GitHub account
- Create a new Spring Boot project inside it
- Finish the tasks
- Push the code to GitHub
- Submit the Github repo link [on this form](https://docs.google.com/forms/d/e/1FAIpQLSfYBoju84gWZNybklLwrqiATCiK_GkJvNIzlk-0A1tGH1rskQ/viewform?usp=sf_link)

#### Bonus Tasks

1. Sort and filter functionality for list tasks
    1. `GET /tasks?completed=true` should return all completed tasks
    2. `GET /tasks?completed=false` should return all incomplete tasks
    3. `GET /tasks?sort=dateDesc` should return all tasks sorted by due date
    4. `GET /tasks?sort=dateAsc` should return all tasks sorted by due date

2. Bulk Delete tasks which are completed to be implemented
    1. `DELETE /tasks?completed=true` should delete all completed tasks

### 04. Make a Task Manager (with Service layer and exception handling)  (v2)

1. Implement all TODOs mentioned in the code
   1. TODO 01: implement PATCH task
   2. TODO 02: implement DELETE task
   3. TODO 03: create a TaskResponseDTO and do not return Task entity directly
   4. TODO 04: generate error for invalid dueDate (before today)
   5. TODO 05: generate error for invalid name (less than 5 char, or more than 100 char)
   6. TODO 06: generate error for invalid dueDate (before today)
   7. TODO 07: also handle IllegalArgumentException (due date, name etc)
   8. TODO 08: in error responses send the error message in a JSON object

#### Solution

- master repository contains the latest code
- This repository [task-apis-v1](https://github.com/sanketwakhare/tasks-basic-apis/tree/task-apis-v1) contains the solution for assignment 03    
- This repository [task-apis-v2](https://github.com/sanketwakhare/tasks-basic-apis/tree/task-apis-v2) contains the solution for assignment 04

#### APIs
#### 1. create a new task 
   - **Request Type:** POST
   - **URL:** http://localhost:8080/tasks
   - **Request payload:** 
     ```
       {
          "name": String,
          "dueDate": Date,
       }
     ```
   - **Response payload**:
       ```
       {
          "id": Integer,
          "name": String,
          "dueDate": Date,
          "isCompleted": Boolean
       }
       ```

#### 2. get all tasks
   - **Request Type:** GET
   - **URL**: http://localhost:8080/tasks
   - **Response payload**:
     ```
     [{
          "id": Integer,
          "name": String,
          "dueDate": Date,
          "isCompleted": Boolean
     },
     {
          "id": Integer,
          "name": String,
          "dueDate": Date,
          "isCompleted": Boolean
     }]
     ``` 
     
#### 3. find task by id 
   - **Request Type:** GET
   - **URL**: http://localhost:8080/tasks/{id}
   - **Response payload**:
     ```
     {
        "id": Integer,
        "name": String,
        "dueDate": Date,
        "isCompleted": Boolean
     }
     ```

#### 4. filter tasks by completed status or sort tasks by due date
   - **Description:** 
     - **completed** path parameter values can be either _true_ or _false_
     - **sort** path parameter values can be either _dateAsc_ or _dateDesc_
     - both of these parameters are optional
   - **Request Type:** GET
   - **URL**: http://localhost:8080/tasks?completed=true&sort=dateDesc
   - **Response payload**:
     ```
     [{
          "id": Integer,
          "name": String,
          "dueDate": Date,
          "isCompleted": Boolean
     },
     {
          "id": Integer,
          "name": String,
          "dueDate": Date,
          "isCompleted": Boolean
     }]
     ```

#### 5. update a task with due date and completed status
   - **Description:**
     - **isCompleted** request parameter values can be either _true_ or _false_
     - **dueDate** request parameter value is of type Date 
     - both of these parameters are optional
   - **Request Type:** PATCH
   - **URL**: http://localhost:8080/tasks/{id}
   - **Request payload:** 
     ```
     {       
        "dueDate": Date,
        "isCompleted": Boolean
     }
     ```
   - **Response payload**:
     ```
     {
        "id": Integer,
        "name": String,
        "dueDate": Date,
        "isCompleted": Boolean
     }
     ```
     
#### 6. delete a task by id
   - **Request Type:** DELETE
   - **URL**: http://localhost:8080/tasks/{id}
   - **Response payload**:
     1. Success Response
     ```
     {
         "message": String          
     }
     ```
     2. Error Response
     ```
     {
         "errorMessage": String          
     }
     ```

#### 7. delete multiple tasks by completed status value
   - **Description:**
     - **completed** path parameter values can be either _true_ or _false_
     - this parameter value is required to delete multiple tasks
   - **Request Type:** DELETE
   - **URL**: http://localhost:8080/tasks?completed=true
   - **Response payload**:
      1. Success Response
     ```
     {
          "message": String          
     }
     ```
      2. Error Response
     ```
     {
         "errorMessage": String          
     }
     ```
