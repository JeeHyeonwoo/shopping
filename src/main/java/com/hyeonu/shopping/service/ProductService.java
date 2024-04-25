package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.*;
import com.hyeonu.shopping.dto.request.ProductInfoDto;
import com.hyeonu.shopping.dto.request.ProductRequestDto;
import com.hyeonu.shopping.dto.type.Gender;
import com.hyeonu.shopping.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductInfoRepository productInfoRepository;
    private final FileUtils fileUtils;
    private final ProductThumbnailRepository productThumbnailRepository;
    private final ProductDetailRepository productDetailRepository;


    @Value("${uploadDir.product-thumbnail}")
    private String thumbnailUploadDir;

    @Value("${uploadDir.product-detail}")
    private String productDetailUploadDir;


    @Transactional
    public void createProduct(ProductRequestDto productRequestDto, Long brandId) throws Exception{
        Category category = categoryRepository.getReferenceById(productRequestDto.getCategoryId());
        Brand brand = brandRepository.getReferenceById(brandId);

        Product savingProduct = new Product(
                productRequestDto.getName(),
                productRequestDto.getPrice(),
                Gender.fromString(productRequestDto.getGender()),
                0,
                category,
                brand
        );
        Product savedProduct = productRepository.save(savingProduct);

        List<ProductInfoDto> productInfoDtoList = productRequestDto.getProductInfoDtoList();
        List<ProductInfo> productInfoList = new ArrayList<>();
        productInfoDtoList.forEach(
                info -> productInfoList.add(
                        ProductInfo.from(info, savedProduct)
                )
        );

        productInfoRepository.saveAll(productInfoList);

        // 썸네일 저장
        List<MultipartFile> thumbnailList = productRequestDto.getThumbnailList();
        List<ProductThumbnail> productThumbnailList = new ArrayList<>();

        for (MultipartFile file: thumbnailList) {
            int index = 0;
            String path = fileUtils.upload(file, thumbnailUploadDir);
            ProductThumbnail productThumbnail = new ProductThumbnail(path, index++, savedProduct);
            productThumbnailList.add(productThumbnail);
        }

        productThumbnailRepository.saveAll(productThumbnailList);

        // 상세정보 이미지
        List<MultipartFile> detailImageList = productRequestDto.getProductDetail();
        List<ProductDetail> productDetailList = new ArrayList<>();

        for (MultipartFile file: detailImageList) {
            int index = 0;
            String path = fileUtils.upload(file, productDetailUploadDir);
            ProductDetail productDetail = new ProductDetail(path, index++, savedProduct);
            productDetailList.add(productDetail);
        }

        productDetailRepository.saveAll(productDetailList);

    }
//    public void createProduct(ProductRequestDto productRequestDto) {
//
//        productRepository.save()
//    }
//
//
//    private void fileUpload(List<MultipartFile> fileList, List<String> extensions) throws IOFileUploadException {
//        Path path = Paths.get(uploadDir + "/" + )
//        for (MultipartFile file: fileList) {
//
//        }
//    }
}
