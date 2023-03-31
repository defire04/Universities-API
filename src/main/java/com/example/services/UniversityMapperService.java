package com.example.services;

import com.example.dto.UniversityDTO;
import com.example.models.University;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;

@Service
public class UniversityMapperService {

    private final ModelMapper modelMapper;


    public UniversityMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public UniversityDTO convertUniversityToUniversityDTO(University university){
        return modelMapper.map(university, UniversityDTO.class);
    }


}
