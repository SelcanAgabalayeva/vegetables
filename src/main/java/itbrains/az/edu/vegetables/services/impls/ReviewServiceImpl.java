package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.FeaturDto;
import itbrains.az.edu.vegetables.dtos.ReviewDto;
import itbrains.az.edu.vegetables.models.Review;
import itbrains.az.edu.vegetables.repositories.ReviewRepository;
import itbrains.az.edu.vegetables.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewDto> getallReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(review -> {
            ReviewDto dto = new ReviewDto();
            dto.setId(review.getId());
            dto.setName(review.getName());
            dto.setComment(review.getComment());
            dto.setStars(review.getStars());
            dto.setAvatarUrl(review.getAvatarUrl());
            dto.setDate(review.getDate());
            return dto;
        }).collect(Collectors.toList());
    }

}
