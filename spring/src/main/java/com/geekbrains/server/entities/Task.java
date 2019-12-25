package com.geekbrains.server.entities;

import com.geekbrains.gwt.common.TaskDto;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;


    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Employee assignee;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;


    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Task() {
    }

    public enum TaskStatus {
        Open("Open", 1), InProgress("In Progress", 2), Done("Done", 3);
        private String statusName;
        private int prior;

        TaskStatus(String statusName, int prior) {
            this.statusName = statusName;
            this.prior = prior;
        }

        public String getStatusName() {
            return statusName;
        }
    }
}
