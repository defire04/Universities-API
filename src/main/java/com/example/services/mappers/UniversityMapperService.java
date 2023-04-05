package com.example.services.mappers;

import com.example.dto.UniversityDTO;
import com.example.models.University;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityMapperService {

    private final ModelMapper modelMapper;


    public UniversityMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public UniversityDTO convertUniversityToUniversityDTO(University university){
        return modelMapper.map(university, UniversityDTO.class);
    }


    public List<UniversityDTO> convertUniversityListToUniversityDTOList(List<University> universities){
        return universities.stream().map(this::convertUniversityToUniversityDTO).collect(Collectors.toList());
    }
}
