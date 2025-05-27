package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.FeaturElementDto;
import itbrains.az.edu.vegetables.models.FeaturElement;
import itbrains.az.edu.vegetables.repositories.FeaturElementRepository;
import itbrains.az.edu.vegetables.services.FeaturElementService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeaturElementServiceImpl implements FeaturElementService {
    private final FeaturElementRepository featurElementRepository;
    private final ModelMapper modelMapper;

    public FeaturElementServiceImpl(FeaturElementRepository featurElementRepository, ModelMapper modelMapper) {
        this.featurElementRepository = featurElementRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeaturElementDto> getAllFeaturElement() {
        List<FeaturElement> featurElements =featurElementRepository.findAll().stream().collect(Collectors.toList());
        List<FeaturElementDto> featurElementDtoList = featurElements.stream().map(slider -> modelMapper.map(slider, FeaturElementDto.class)).collect(Collectors.toList());
        return featurElementDtoList;
    }
}
