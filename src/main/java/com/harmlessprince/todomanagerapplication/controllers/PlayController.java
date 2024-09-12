package com.harmlessprince.todomanagerapplication.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmlessprince.todomanagerapplication.validators.interfaces.CustomDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class PlayController extends BaseController{

    @PostMapping("test/date")
    public String testDate(@Valid @RequestBody DateTimeDto dateTimeDto){
        return dateTimeDto.getLocalDateTime().toString();
//        return null;
    }

    @PostMapping("test/slugify")
    public String testDate(@Valid @RequestBody SlugifyDto slugifyDto){
        return slugifyDto.getSlug();
//        return null;
    }
}


@Data
class DateTimeDto{


    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate createdDate;

    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
}

@Data
class SlugifyDto{


   @NotBlank
    private String slug;

    public void setSlug(String slug) {
        this.slug  = slug.replace(" ", "-").toLowerCase();
    }
}