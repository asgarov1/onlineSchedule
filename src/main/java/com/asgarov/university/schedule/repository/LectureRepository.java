package com.asgarov.university.schedule.repository;

import com.asgarov.university.schedule.domain.Lecture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends CrudRepository<Lecture, Long> {
    List<Lecture> findAll();

    void deleteById(Long id);
}
