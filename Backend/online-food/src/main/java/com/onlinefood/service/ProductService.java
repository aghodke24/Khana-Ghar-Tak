package com.onlinefood.service;

import org.springframework.web.multipart.MultipartFile;

import com.onlinefood.dto.Productdto;
import com.onlinefood.model.Product;

public interface ProductService {
	
	void addProduct(Product product, MultipartFile productImmage);
	
	Product getProduct(Integer id);
	
//	Productdto update(Integer id , Product product);

}
