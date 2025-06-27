package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.review.ReviewDto;
import itbrains.az.edu.vegetables.dtos.review.ReviewDashboardDto;
import itbrains.az.edu.vegetables.dtos.review.ReviewUpdateDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getallReviews();


    List<ReviewDashboardDto> getDashboardReviews();

    ReviewUpdateDto getUpdateReview(Long id);

    void updateReview(Long id, ReviewUpdateDto reviewUpdateDto);

    void deleteReview(Long id);
}
