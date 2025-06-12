package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.BannerDto;
import itbrains.az.edu.vegetables.dtos.SliderDto;
import itbrains.az.edu.vegetables.models.Banner;
import itbrains.az.edu.vegetables.repositories.BannerRepository;
import itbrains.az.edu.vegetables.services.BannerService;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public BannerDto getBanner(long id) {
        Banner banner =bannerRepository.findById(id).orElseThrow();
        BannerDto bannerDto =new BannerDto();
        bannerDto.setId(banner.getId());
        bannerDto.setTitle(banner.getTitle());
        bannerDto.setSubtitle(banner.getSubtitle());
        bannerDto.setPrice(banner.getPrice());
        bannerDto.setImageUrl(banner.getImageUrl());
        bannerDto.setNumber(banner.getNumber());

        return bannerDto;
    }
}
