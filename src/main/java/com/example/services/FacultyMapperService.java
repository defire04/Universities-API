package com.example.services;

import com.example.dto.FacultyDTO;
import com.example.dto.UniversityDTO;
import com.example.models.Faculty;
import com.example.models.University;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FacultyMapperService {

    private final ModelMapper modelMapper;


    public FacultyMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FacultyDTO convertFacultyToFacultyDTO(Faculty faculty){
        return modelMapper.map(faculty, FacultyDTO.class);
    }
}
