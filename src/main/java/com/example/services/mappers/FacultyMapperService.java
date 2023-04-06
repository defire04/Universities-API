package com.example.services.mappers;

import com.example.dto.FacultyDTO;
import com.example.models.Faculty;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyMapperService {

    private final ModelMapper modelMapper;

    public FacultyMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FacultyDTO convertFacultyToFacultyDTO(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDTO.class);
    }

    public List<FacultyDTO> convertFacultyListToFacultyDTOList(List<Faculty> faculties) {
        return faculties.stream().map(this::convertFacultyToFacultyDTO).collect(Collectors.toList());
    }
}
