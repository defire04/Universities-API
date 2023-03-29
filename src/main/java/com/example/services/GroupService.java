package com.example.services;


import com.example.models.Course;
import com.example.models.Group;
import com.example.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public Group save(Group group) {
        groupRepository.save(group);
        return group;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Optional<Group> findById(int id) {
        return  groupRepository.findById(id);
    }

    public Optional<Group> getGroupByCourseAndValueOnSite(Course course, String valueOnSite){
        return groupRepository.findGroupByCourseAndValueOnSite(course, valueOnSite);
    }
}
