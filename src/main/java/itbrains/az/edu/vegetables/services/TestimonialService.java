package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.TestimonialDto;
import itbrains.az.edu.vegetables.models.Testimonial;

import java.util.List;

public interface TestimonialService {
    List<TestimonialDto> getAllTestimonial();
}
