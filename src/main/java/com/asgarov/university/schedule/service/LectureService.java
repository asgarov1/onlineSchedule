package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.dao.LectureDao;
import com.asgarov.university.schedule.domain.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LectureService extends AbstractDaoService<Long, Lecture> {

    public LectureService(final LectureDao lectureDao) {
        super(lectureDao);
    }

    public Page<Lecture> findPaginated(Pageable pageable) {
        List<Lecture> lectures = findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Lecture> list;

        if (lectures.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lectures.size());
            list = lectures.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), lectures.size());
    }
}
