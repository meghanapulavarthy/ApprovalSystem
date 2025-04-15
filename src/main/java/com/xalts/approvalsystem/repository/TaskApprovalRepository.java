package com.xalts.approvalsystem.repository;

import com.xalts.approvalsystem.model.TaskApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskApprovalRepository extends JpaRepository<TaskApproval, Long> {
    Optional<TaskApproval> findByTaskIdAndApproverId(Long taskId, Long approverId);
    long countByTaskIdAndApprovedTrue(Long taskId);
}
