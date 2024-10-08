package com.ecommerce.project.service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new
                ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setImage("DefaultPng");
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01 * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRespository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
