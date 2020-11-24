package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.annotations.Loggable;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class AbstractService<T, ID> {
    private final CrudRepository<T, ID> repository;

    public AbstractService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Loggable
    public T create(T object) {
        return repository.save(object);
    }

    @Loggable
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Loggable
    public void update(T object) {
        repository.save(object);
    }

    @Loggable
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public void saveAll(List<T> objects) {
        objects.forEach(this::create);
    }
}
