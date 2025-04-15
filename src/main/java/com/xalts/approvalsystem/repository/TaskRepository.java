package com.xalts.approvalsystem.repository;

import com.xalts.approvalsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
