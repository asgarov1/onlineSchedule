package com.asgarov.university.schedule.data;

import com.asgarov.university.schedule.dao.DegreeDao;
import com.asgarov.university.schedule.dao.RoleDao;
import com.asgarov.university.schedule.domain.Role;
import com.asgarov.university.schedule.domain.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataGenerator implements CommandLineRunner {

    private final RoleDao roleDao;
    private final DegreeDao degreeDao;

    public DataGenerator(RoleDao roleDao, DegreeDao degreeDao) {
        this.roleDao = roleDao;
        this.degreeDao = degreeDao;
    }

    @Override
    public void run(final String... args) {
        Arrays.stream(Role.values()).forEach(
                role -> {
                    if (roleDao.findAll().isEmpty()) {
                        roleDao.create(role);
                    }
                });
        Arrays.stream(Student.Degree.values()).forEach(
                degree -> {
                    if (degreeDao.findAll().isEmpty()) {
                        degreeDao.create(degree);
                    }
                });
    }
}
