package com.geekbrains.server.services;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.server.entities.Task;
import com.geekbrains.server.mappers.TaskMapper;
import com.geekbrains.server.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDto save(TaskDto taskDto) {
        Task task = TaskMapper.MAPPER.toTask(taskDto);
        task = taskRepository.save(task);
        return TaskMapper.MAPPER.fromTask(task);
    }

    public List<TaskDto> findAll() {
        return TaskMapper.MAPPER.fromTaskList(taskRepository.findAll());
    }

    public TaskDto findOne(Long id) {
        return taskRepository.findById(id).map(TaskMapper.MAPPER::fromTask).get();
    }

    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }
}
