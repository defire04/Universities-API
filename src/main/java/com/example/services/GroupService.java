package com.example.services;


import com.example.models.Course;
import com.example.models.Group;
import com.example.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    public Group save(Group group) {
        groupRepository.save(group);
        return group;
    }


    public List<Group> save(List<Group> groups) {
        List<Group> result = new ArrayList<>();
        groups.forEach(group -> {
            result.add(findGroupByCourseAndValueOnSite(group.getCourse(), group.getValueOnSite())
                    .orElseGet(() -> groupRepository.save(new Group(group.getCourse(), group.getTitle(), group.getValueOnSite()))));
        });

        return result;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Optional<Group> findById(int id) {
        return groupRepository.findById(id);
    }

    public Optional<Group> findGroupByCourseAndValueOnSite(Course course, String valueOnSite) {
        return groupRepository.findGroupByCourseAndValueOnSite(course, valueOnSite);
    }

    public Optional<Group> findByCourse(Course course){
        return groupRepository.findByCourse(course);
    }
}
