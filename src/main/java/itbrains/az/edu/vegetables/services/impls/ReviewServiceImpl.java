package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.review.ReviewDto;
import itbrains.az.edu.vegetables.dtos.review.ReviewDashboardDto;
import itbrains.az.edu.vegetables.dtos.review.ReviewUpdateDto;
import itbrains.az.edu.vegetables.models.Review;
import itbrains.az.edu.vegetables.repositories.ReviewRepository;
import itbrains.az.edu.vegetables.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<ReviewDashboardDto> getDashboardReviews() {
        return reviewRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))   //
                .stream()
                .map(cat -> modelMapper.map(cat, ReviewDashboardDto.class))
                .toList();
    }

    @Override
    public ReviewUpdateDto getUpdateReview(Long id) {
        Review review=reviewRepository.findById(id).orElseThrow();
        ReviewUpdateDto reviewUpdateDto=modelMapper.map(review,ReviewUpdateDto.class);
        return reviewUpdateDto;
    }

    @Override
    public void updateReview(Long id, ReviewUpdateDto reviewUpdateDto) {
        Review review=reviewRepository.findById(id).orElseThrow();
        review.setName(reviewUpdateDto.getName());
        review.setDate(reviewUpdateDto.getDate());
        review.setStars(reviewUpdateDto.getStars());
        review.setComment(reviewUpdateDto.getComment());
        review.setAvatarUrl(reviewUpdateDto.getAvatarUrl());
        review.setProductId(reviewUpdateDto.getProductId());
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review review=reviewRepository.findById(id).orElseThrow();
        reviewRepository.delete(review);
    }

}
