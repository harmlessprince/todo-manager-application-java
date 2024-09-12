package com.harmlessprince.todomanagerapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmlessprince.todomanagerapplication.utils.CaseMode;
import com.harmlessprince.todomanagerapplication.validators.interfaces.CheckCase;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {

    private Integer id;


    //    @NotNull(message = "Name can not be blank")
    @Size(min = 2)
    private String title;

    //    @NotEmpty(message = "Description can not be blank")
    @Size(min = 5)
    private String description;

    @CheckCase(value = CaseMode.UPPER, message = "Status must be in uppercase")
    @NotNull
    private String status;


    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedStartDate;


    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedEndDate;


    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualStartDate;

//    @FutureOrPresent
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime actualEndDate;
}
