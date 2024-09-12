package com.harmlessprince.todomanagerapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmlessprince.todomanagerapplication.interfaces.DateValidatorInterface;
import com.harmlessprince.todomanagerapplication.models.Task;
import com.harmlessprince.todomanagerapplication.utils.CaseMode;
import com.harmlessprince.todomanagerapplication.validators.interfaces.CheckCase;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A DTO for the {@link com.harmlessprince.todomanagerapplication.models.Task} entity
 */
public class TaskRequestDto implements Serializable {

    private Integer id;
    @NotBlank(message = "Name can not be blank")
    private String title;
    @NotBlank(message = "Description can not be blank")
    private String description;

    @CheckCase(value = CaseMode.UPPER, message = "Status must be in uppercase")
    private String status;
    private float hours;

    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Please provide when you intend to start the task")
    private LocalDateTime plannedStartDate;


    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Please prove when you intent to finish the task")
    private LocalDateTime plannedEndDate;
    private LocalDateTime actualStartDate;
    private LocalDateTime actualEndDate;
    private DateValidatorInterface dateTimeValidator;
    public TaskRequestDto(String title, String description, String status, float hours, @FutureOrPresent @NotBlank LocalDateTime plannedStartDate, @NotBlank LocalDateTime plannedEndDate, LocalDateTime actualStartDate, LocalDateTime actualEndDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.hours = hours;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
    }

    public TaskRequestDto() {
    }

    //    public LocalDateTime getParsedDateTime(String dateTime) {
//        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public float getHours() {
        return hours;
    }

    public LocalDateTime getPlannedStartDate() {
        return plannedStartDate;
    }

    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    public LocalDateTime getActualStartDate() {
        return actualStartDate;
    }

    public LocalDateTime getActualEndDate() {
        return actualEndDate;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public void setPlannedStartDate(LocalDateTime plannedStartDate) {

        this.plannedStartDate = plannedStartDate;
    }

    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public void setActualStartDate(LocalDateTime actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

//    public void setActualEndDate(LocalDateTime actualEndDate) {
//        this.actualEndDate = actualEndDate;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRequestDto entity = (TaskRequestDto) o;
        return Objects.equals(this.title, entity.title) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.status, entity.status) &&
                Objects.equals(this.hours, entity.hours) &&
                Objects.equals(this.plannedStartDate, entity.plannedStartDate) &&
                Objects.equals(this.plannedEndDate, entity.plannedEndDate) &&
                Objects.equals(this.actualStartDate, entity.actualStartDate) &&
                Objects.equals(this.actualEndDate, entity.actualEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, status, hours, plannedStartDate, plannedEndDate, actualStartDate, actualEndDate);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "description = " + description + ", " +
                "status = " + status + ", " +
                "hours = " + hours + ", " +
                "plannedStartDate = " + plannedStartDate + ", " +
                "plannedEndDate = " + plannedEndDate + ", " +
                "actualStartDate = " + actualStartDate + ", " +
                "actualEndDate = " + actualEndDate + ")";
    }

    public static Task toEntity(TaskRequestDto taskRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(taskRequestDto, Task.class);
    }
}