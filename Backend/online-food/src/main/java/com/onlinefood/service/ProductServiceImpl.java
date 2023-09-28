package com.onlinefood.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.onlinefood.customException.NoSuchResourceFound;
import com.onlinefood.dao.ProductDao;
import com.onlinefood.dto.Productdto;
import com.onlinefood.model.Product;
import com.onlinefood.repository.ProductRepository;
import com.onlinefood.utility.StorageService;
import org.modelmapper.*;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
//	@Autowired(required = true)
//	private ModelMapper mapper;
	
	@Autowired 
	private ProductDao productDao;
	
	@Autowired
	private StorageService storageService;

	@Override
	public void addProduct(Product product, MultipartFile productImmage) {
		
		String productImageName = storageService.store(productImmage);
		
		product.setImageName(productImageName);
		
		this.productDao.save(product);
	}
	
//	@Override
//	public Productdto update(Integer id , Product product)
//	{
//		Product oldProduct =  productRepository.findById(id).orElseThrow(() -> new NoSuchResourceFound("no such product"));
//		oldProduct.setId(product.getId());
//		oldProduct.setTitle(product.getTitle());
//		oldProduct.setPrice(product.getPrice());
//		oldProduct.setDescription(product.getDescription());
//		oldProduct.setQuantity(product.getQuantity());
//		oldProduct.setImageName(product.getImageName());
//		productRepository.save(oldProduct);
//		return mapper.map(oldProduct,Productdto.class);
//	}
	
	@Override
    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElseThrow(()->new NoSuchResourceFound("no such element found"));
    }

}
