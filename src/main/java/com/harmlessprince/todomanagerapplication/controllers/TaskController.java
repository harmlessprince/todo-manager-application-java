package com.harmlessprince.todomanagerapplication.controllers;

import com.harmlessprince.todomanagerapplication.dto.TaskRequestDto;
import com.harmlessprince.todomanagerapplication.dto.UpdateTaskRequest;
import com.harmlessprince.todomanagerapplication.exceptions.NotFoundException;
import com.harmlessprince.todomanagerapplication.interfaces.DateValidatorInterface;
import com.harmlessprince.todomanagerapplication.models.Task;
import com.harmlessprince.todomanagerapplication.Repositories.TaskRepository;
import com.harmlessprince.todomanagerapplication.utils.ApiResponseHelper;
import com.harmlessprince.todomanagerapplication.interfaces.AuthHelperInterface;
import com.harmlessprince.todomanagerapplication.utils.Enum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {


    private final AuthHelperInterface authHelperInterface;
    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private DateValidatorInterface dateTimeValidator;


    public TaskController(AuthHelperInterface authHelperInterface, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.authHelperInterface = authHelperInterface;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('view_any_task')")
    public Object index(HttpServletRequest request) {
        List<Task> tasks = taskRepository.findAll();

        return ApiResponseHelper.success(tasks, "All tasks fetched successfully");
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAuthority('view_task')")
    public Object show(HttpServletRequest request, @PathVariable Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new NotFoundException("Task not found");
        }
        return ApiResponseHelper.success(task.get(), "Task fetched successfully");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create_task')")
    public ResponseEntity<ApiResponseHelper<Object>> store(HttpServletRequest request, @Valid @RequestBody TaskRequestDto taskRequestDto) {


        Task task = modelMapper.map(taskRequestDto, Task.class);

        task.setPlannedEndDate(taskRequestDto.getPlannedEndDate());
        task.setPlannedStartDate(taskRequestDto.getPlannedStartDate());

        if (task.getPlannedStartDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(ApiResponseHelper.error("Planned start date must be in the future"));
        }
        if (task.getPlannedEndDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(ApiResponseHelper.error("Planned end date must be in the future"));
        }
        if (task.getPlannedStartDate().isAfter(task.getPlannedEndDate())) {
            return ResponseEntity.badRequest().body(ApiResponseHelper.error("Planned end date must be after planned start date"));
        }
        task.setUser(authHelperInterface.userDetails());
        task.setStatus(Enum.TASK_STAUSES.PENDING.name());
        taskRepository.save(task);
        return ResponseEntity.ok().body(ApiResponseHelper.success(task));
    }


    @PatchMapping("/{taskId}")
    @PreAuthorize("hasAuthority('update_task')")
    public ResponseEntity<ApiResponseHelper<Object>> update(HttpServletRequest request, @Valid @RequestBody UpdateTaskRequest taskRequestDto, @PathVariable Integer taskId) throws Exception {

        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task Not Found");
        }

        List<String> statuses = List.of(Task.TASK_STATUES);
        if (taskRequestDto.getStatus() != null){
            if(!statuses.contains(taskRequestDto.getStatus())){
               return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponseHelper.error("Invalid Task Status"));
            }
        }
        if (taskRequestDto.getActualStartDate() != null){
            if (taskOptional.get().getActualStartDate().isAfter(taskOptional.get().getPlannedEndDate())) {
                return ResponseEntity.badRequest().body(ApiResponseHelper.error("Planned start date must be in the future"));
            }
        }

        if (taskOptional.get().getPlannedEndDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(ApiResponseHelper.error("Planned end date must be in the future"));
        }
        if (taskOptional.get().getPlannedStartDate().isAfter(taskOptional.get().getPlannedEndDate())) {
            return ResponseEntity.badRequest().body(ApiResponseHelper.error("Planned end date must be after planned start date"));
        }
        modelMapper.map(taskRequestDto, taskOptional.get());


        Task task = taskRepository.save(taskOptional.get());
        return ResponseEntity.ok(ApiResponseHelper.success(task));
    }



    @GetMapping("/{taskId}/completed")
    @PreAuthorize("hasAuthority('update_task')")
    public ResponseEntity<ApiResponseHelper<Object>> markTaskAsCompleted(HttpServletRequest request, @PathVariable Integer taskId) throws Exception {

        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task Not Found");
        }

        taskOptional.get().setActualEndDate(LocalDateTime.now());
        taskOptional.get().setStatus(Enum.TASK_STAUSES.COMPLETED.name());

        Task task = taskRepository.save(taskOptional.get());
        return ResponseEntity.ok(ApiResponseHelper.success(task));
    }


}
