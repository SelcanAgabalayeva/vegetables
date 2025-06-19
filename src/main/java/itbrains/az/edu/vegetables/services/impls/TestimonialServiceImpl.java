package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialCreateDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialDashboardDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialUpdateDto;
import itbrains.az.edu.vegetables.models.Testimonial;
import itbrains.az.edu.vegetables.repositories.TestimonialRepository;
import itbrains.az.edu.vegetables.services.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<TestimonialDashboardDto> getDashboardTestimonials() {
        return testimonialRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(tes -> modelMapper.map(tes, TestimonialDashboardDto.class))
                .toList();
    }

    @Override
    public void createTestimonial(TestimonialCreateDto testimonialCreateDto) {
        Testimonial testimonial = new Testimonial();
        modelMapper.map(testimonialCreateDto,testimonial);
        testimonialRepository.save(testimonial);
    }

    @Override
    public TestimonialUpdateDto getUpdateTestimonial(Long id) {
        Testimonial testimonial=testimonialRepository.findById(id).orElseThrow();
        TestimonialUpdateDto testimonialUpdateDto=modelMapper.map(testimonial,TestimonialUpdateDto.class);
        return testimonialUpdateDto;
    }

    @Override
    public void updateTestimonial(Long id, TestimonialUpdateDto testimonialUpdateDto) {
        Testimonial testimonial=testimonialRepository.findById(id).orElseThrow();
        testimonial.setClientName(testimonialUpdateDto.getClientName());
        testimonial.setRate(testimonialUpdateDto.getRate());
        testimonial.setDescription(testimonialUpdateDto.getDescription());
        testimonial.setImageUrl(testimonialUpdateDto.getImageUrl());
        testimonial.setProfession(testimonialUpdateDto.getProfession());
        testimonialRepository.save(testimonial);
    }

    @Override
    public void deleteTestimonial(Long id) {
        Testimonial testimonial=testimonialRepository.findById(id).orElseThrow();
        testimonialRepository.delete(testimonial);
    }
}
