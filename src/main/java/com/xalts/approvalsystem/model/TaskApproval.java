package com.xalts.approvalsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_approvals", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"task_id", "approver_id"})
})
@NoArgsConstructor
@AllArgsConstructor
public class TaskApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;

    private boolean approved;
    private String comment;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
