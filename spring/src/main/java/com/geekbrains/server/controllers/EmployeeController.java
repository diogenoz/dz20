package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.EmployeeDto;
import com.geekbrains.server.exceptions.ResourceAlreadyExistException;
import com.geekbrains.server.exceptions.ResourceNotFoundException;
import com.geekbrains.server.mappers.EmployeeMapper;
import com.geekbrains.server.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getOne(@PathVariable Long id) {
        if (!employeeService.existsById(id)) {
            throw new ResourceNotFoundException("Employee with id: " + id + " not found");
        }
        return new ResponseEntity<>(employeeService.findOne(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeDto employeeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        if (employeeDto.getId() != null && employeeService.existsById(employeeDto.getId())) {
            throw new ResourceAlreadyExistException("Employee with id: " + employeeDto.getId() + " already exists");
        }
        return new ResponseEntity<>(employeeService.save(employeeDto), HttpStatus.CREATED);
    }
}