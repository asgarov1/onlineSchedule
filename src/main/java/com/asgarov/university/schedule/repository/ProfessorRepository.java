package com.asgarov.university.schedule.repository;

import com.asgarov.university.schedule.domain.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
    List<Professor> findAll();
}
