package com.xalts.approvalsystem.dto;

public class ApprovalRequest {
    private Long taskId;
    private Long userId;
    private String comment;

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
