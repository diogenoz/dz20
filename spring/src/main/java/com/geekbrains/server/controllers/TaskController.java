package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.ErrorDto;
import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.ValidationErrorDto;
import com.geekbrains.server.entities.Task;
import com.geekbrains.server.exceptions.ResourceAlreadyExistException;
import com.geekbrains.server.exceptions.ResourceNotFoundException;
import com.geekbrains.server.exceptions.RestResourceException;
import com.geekbrains.server.mappers.TaskMapper;
import com.geekbrains.server.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getOne(@PathVariable Long id) {
        if (!taskService.existsById(id)) {
            throw new ResourceNotFoundException("Task with id: " + id + " not found");
        }
        return new ResponseEntity<>(taskService.findOne(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TaskDto TaskDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        if (TaskDto.getId() != null && taskService.existsById(TaskDto.getId())) {
            throw new ResourceAlreadyExistException("Task with id: " + TaskDto.getId() + " already exists");
        }
        return new ResponseEntity<>(taskService.save(TaskDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }
}