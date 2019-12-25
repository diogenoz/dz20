package com.geekbrains.server.services;

import com.geekbrains.gwt.common.EmployeeDto;
import com.geekbrains.server.entities.Employee;
import com.geekbrains.server.mappers.EmployeeMapper;
import com.geekbrains.server.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.MAPPER.toEmployee(employeeDto);
        employee = employeeRepository.save(employee);
        return EmployeeMapper.MAPPER.fromEmployee(employee);
    }

    public List<EmployeeDto> findAll() {
        return EmployeeMapper.MAPPER.fromEmployeeList(employeeRepository.findAll());
    }

    public EmployeeDto findOne(Long id) {
        return employeeRepository.findById(id).map(EmployeeMapper.MAPPER::fromEmployee).get();
    }

    public void remove(Long id) {
        employeeRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }
}
