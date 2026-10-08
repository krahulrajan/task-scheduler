# Task scheduler service

If you run 5 instances of a service, a standard timer (like Spring’s @Scheduled) will run 5 times simultaneously.
To prevent duplicate execution and ensure tasks run reliably even if a pod crashes, you need distributed scheduling.

The 3 common way to achieve it
1. Distributed locking --
    Best for periodic maintainence, daily report generation and cleanup.
    It uses a shared lock and only one instance can access at a time. ShedLock(redis lock) or Quartz Scheduler are tools used most widely.
2. Scheduler service + Message broker --
    A single lightweight scheduler service (or cron runner) tracks when tasks are due. When time is up, it publishes a message (e.g., SEND_INVOICE_JOB) to a queue/broker like RabbitMQ or Kafka.
    Best for Heavy background tasks, sending batch emails, or processing payments.
3. Cloud / Infrastracture managed --
    Kubernetes CronJobs: Spins up a container on a schedule, executes the task, and terminates.
    Containerized cloud workloads where you don't want to maintain scheduler infrastructure yourself.



