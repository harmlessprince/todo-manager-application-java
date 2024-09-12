package com.harmlessprince.todomanagerapplication.Repositories;

import com.harmlessprince.todomanagerapplication.models.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Override
    @EntityGraph(value = "Task.user")
    List<Task> findAll();
}