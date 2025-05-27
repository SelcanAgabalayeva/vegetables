package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.SliderDto;
import itbrains.az.edu.vegetables.models.Slider;

import java.util.List;

public interface SliderService {


    List<SliderDto> getAllSliders();
    SliderDto getSlider(Long id);
}
