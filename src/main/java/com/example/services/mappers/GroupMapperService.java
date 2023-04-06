package com.example.services.mappers;

import com.example.dto.CourseDTO;
import com.example.dto.GroupDTO;
import com.example.models.Course;
import com.example.models.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupMapperService {

    private final ModelMapper modelMapper;

    public GroupMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupDTO convertGroupToGroupDTO(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }

    public List<GroupDTO> convertGroupListToGroupDTOList(Set<Group> groups) {
        return groups.stream().map(this::convertGroupToGroupDTO).collect(Collectors.toList());
    }
}
