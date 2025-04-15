Implemented Features:

User Management
    Users can sign up and log in using email.
    Unique email constraint enforced.

Task Creation
    Any user can create a task with a title, description, and select exactly 3 approvers.
    createdBy stores the user who created the task.
    Status is managed as an enum (PENDING, APPROVED).

Approvals
    Users (excluding creator) can approve a task.
    Comments can be added at the time of approval.
    Each approval is tracked in a TaskApproval entity (one-to-one mapping per user-task).
    When 3 distinct users approve a task, it is auto-marked as APPROVED.

Design Pointers & Hooks:

Approvers Selection Dropdown
  The CreateTaskRequest DTO accepts approverIds: List<Long> to assign approvers during task creation.

Email Notifications
    Upon task creation and approval, email alerts should be sent.
    A NotificationService is implemented with sendEmail(to, subject, body).
    Sends emails to approvers on task creation.
    Notifies the creator each time an approver signs off.
    sends a final email to all participants when the task is fully approved.
    integrated with external services like SendGrid or Mailgun.
    use kafka to enable to above process
Final Approval Alert
    Once 3 approvals are done, all participants should be alerted.
    After the 3rd approval, backend logic sends emails to all involved (creator + approvers).

Future Enhancements:
    Add rejection capability with REJECTED status
    Pagination for task list APIs
    handling race conditions
    ratelimiting notifications






