package com.geekbrains.server.mappers;

import com.geekbrains.gwt.common.EmployeeDto;
import com.geekbrains.server.entities.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);

    Employee toEmployee(EmployeeDto employeeDto);

    @InheritInverseConfiguration
    EmployeeDto fromEmployee(Employee employee);

    List<Employee> toEmployeeList(List<EmployeeDto> employeeDtos);

    List<EmployeeDto> fromEmployeeList(List<Employee> employees);
}
