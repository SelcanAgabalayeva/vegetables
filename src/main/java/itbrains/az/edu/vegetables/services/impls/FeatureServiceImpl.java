package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.FeaturDto;
import itbrains.az.edu.vegetables.dtos.SliderDto;
import itbrains.az.edu.vegetables.models.Featur;
import itbrains.az.edu.vegetables.repositories.FeaturRepository;
import itbrains.az.edu.vegetables.services.FeaturService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class FeatureServiceImpl implements FeaturService {
    private final FeaturRepository featurRepository;
    private final ModelMapper modelMapper;

    public FeatureServiceImpl(FeaturRepository featurRepository, ModelMapper modelMapper) {
        this.featurRepository = featurRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeaturDto> getAllFeaturs() {
        List<Featur> featurList =featurRepository.findAll().stream().collect(Collectors.toList());
        List<FeaturDto> featurDtoList = featurList.stream().map(slider -> modelMapper.map(slider, FeaturDto.class)).collect(Collectors.toList());
        return featurDtoList;
    }
}
