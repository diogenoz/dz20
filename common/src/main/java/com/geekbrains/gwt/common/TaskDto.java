package com.geekbrains.gwt.common;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaskDto {
    private Long id;

    @Size(min = 4, message = "Task title too short")
    private String title;

    private EmployeeDto owner;
    private EmployeeDto assignee;
    private String description;
    private TaskStatusDto status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EmployeeDto getOwner() {
        return owner;
    }

    public void setOwner(EmployeeDto owner) {
        this.owner = owner;
    }

    public EmployeeDto getAssignee() {
        return assignee;
    }

    public void setAssignee(EmployeeDto assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskStatusDto getStatus() {
        return status;
    }

    public void setStatus(TaskStatusDto status) {
        this.status = status;
    }

    public TaskDto() {
    }


    public TaskDto(Long id, String title, EmployeeDto owner, EmployeeDto assignee, String description, TaskStatusDto status) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.assignee = assignee;
        this.description = description;
        this.status = status;
    }

    public enum TaskStatusDto {
        Open("Open", 1), InProgress("In Progress", 2), Done("Done", 3);
        private String statusName;
        private int prior;

        TaskStatusDto(String statusName, int prior) {
            this.statusName = statusName;
            this.prior = prior;
        }

        public String getStatusName() {
            return statusName;
        }
    }
}
