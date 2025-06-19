package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialCreateDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialDashboardDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialUpdateDto;

import java.util.List;

public interface TestimonialService {
    List<TestimonialDto> getAllTestimonial();

    List<TestimonialDashboardDto> getDashboardTestimonials();

    void createTestimonial(TestimonialCreateDto testimonialCreateDto);

    TestimonialUpdateDto getUpdateTestimonial(Long id);

    void updateTestimonial(Long id, TestimonialUpdateDto testimonialUpdateDto);

    void deleteTestimonial(Long id);
}
