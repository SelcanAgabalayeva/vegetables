package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.SliderDto;
import itbrains.az.edu.vegetables.dtos.TestimonialDto;
import itbrains.az.edu.vegetables.models.Testimonial;
import itbrains.az.edu.vegetables.repositories.TestimonialRepository;
import itbrains.az.edu.vegetables.services.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestimonialServiceImpl implements TestimonialService {
    private final TestimonialRepository testimonialRepository;
    private final ModelMapper modelMapper;

    public TestimonialServiceImpl(TestimonialRepository testimonialRepository, ModelMapper modelMapper) {
        this.testimonialRepository = testimonialRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TestimonialDto> getAllTestimonial() {
        List<Testimonial> testimonialList=testimonialRepository.findAll().stream().collect(Collectors.toList());
        List<TestimonialDto> testimonialDtoList =testimonialList.stream().map(testimonial -> modelMapper.map(testimonial, TestimonialDto.class)).collect(Collectors.toList());
        return testimonialDtoList;
    }
}
