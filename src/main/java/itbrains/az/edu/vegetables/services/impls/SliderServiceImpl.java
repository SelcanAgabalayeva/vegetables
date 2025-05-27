package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.SliderDto;
import itbrains.az.edu.vegetables.models.Slider;
import itbrains.az.edu.vegetables.repositories.SliderRepository;
import itbrains.az.edu.vegetables.services.SliderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SliderServiceImpl implements SliderService {
    private final SliderRepository sliderRepository;
    private final ModelMapper modelMapper;

    public SliderServiceImpl(SliderRepository sliderRepository, SliderRepository sliderRepository1, ModelMapper modelMapper) {
        this.sliderRepository = sliderRepository1;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SliderDto> getAllSliders() {
        List<Slider> sliderList=sliderRepository.findAll().stream().collect(Collectors.toList());
        List<SliderDto> sliderDtoList=sliderList.stream().map(slider -> modelMapper.map(slider, SliderDto.class)).collect(Collectors.toList());
        return sliderDtoList;
    }

    @Override
    public SliderDto getSlider(Long id) {
        Slider slider =sliderRepository.findById(id).orElseThrow();
        SliderDto sliderDto =new SliderDto();
        sliderDto.setId(slider.getId());
        sliderDto.setPrimaryText(slider.getPrimaryText());
        sliderDto.setSecondaryText(slider.getSecondaryText());
        return sliderDto;
    }
}
