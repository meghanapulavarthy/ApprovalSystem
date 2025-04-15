package com.xalts.approvalsystem.controller;

import com.xalts.approvalsystem.constants.TaskStatus;
import com.xalts.approvalsystem.dto.ApprovalRequest;
import com.xalts.approvalsystem.dto.CreateTaskRequest;
import com.xalts.approvalsystem.model.Task;
import com.xalts.approvalsystem.model.TaskApproval;
import com.xalts.approvalsystem.model.User;
import com.xalts.approvalsystem.repository.TaskApprovalRepository;
import com.xalts.approvalsystem.repository.TaskRepository;
import com.xalts.approvalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskApprovalRepository approvalRepo;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) {
        User creator = userRepo.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedBy(creator);

        return ResponseEntity.ok(taskRepo.save(task));
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveTask(@RequestBody ApprovalRequest request) {
        Task task = taskRepo.findById(request.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User approver = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (task.getCreatedBy().getId().equals(approver.getId())) {
            return ResponseEntity.badRequest().body("Task creator cannot approve their own task.");
        }

        boolean alreadyApproved = approvalRepo
                .findByTaskIdAndApproverId(task.getId(), approver.getId())
                .isPresent();

        if (alreadyApproved) {
            return ResponseEntity.badRequest().body("User already approved this task.");
        }

        TaskApproval approval = new TaskApproval();
        approval.setTask(task);
        approval.setApprover(approver);
        approval.setApproved(true);
        approval.setComment(request.getComment());
        approvalRepo.save(approval);

        long approvalCount = approvalRepo.countByTaskIdAndApprovedTrue(task.getId());
        if (approvalCount >= 3 && task.getStatus() != TaskStatus.APPROVED) {
            task.setStatus(TaskStatus.APPROVED);
            taskRepo.save(task);
        }

        return ResponseEntity.ok("Task approved successfully.");
    }
}
