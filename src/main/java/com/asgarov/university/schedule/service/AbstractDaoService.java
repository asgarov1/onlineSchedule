package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.annotations.Loggable;
import com.asgarov.university.schedule.dao.AbstractDao;
import com.asgarov.university.schedule.dao.exception.DaoException;

import java.util.List;

public abstract class AbstractDaoService<K, T> {

    private AbstractDao<K, T> abstractDao;

    public AbstractDaoService(final AbstractDao<K, T> abstractDao) {
        this.abstractDao = abstractDao;
    }

    @Loggable
    public Long create(T object) {
        return abstractDao.create(object);
    }

    @Loggable
    public T findById(K id) {
        return abstractDao.findById(id);
    }

    @Loggable
    public List<T> findAll() {
        return abstractDao.findAll();
    }

    @Loggable
    public void update(T object) {
        abstractDao.update(object);
    }

    public void createAll(List<T> list) {
        list.forEach(this::create);
    }

    @Loggable
    public void deleteById(K id) throws DaoException {
        abstractDao.deleteById(id);
    }
}
