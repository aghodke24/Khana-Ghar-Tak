package com.onlinefood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onlinefood.dao.CategoryDao;
import com.onlinefood.dao.ProductDao;
import com.onlinefood.dao.UserDao;
import com.onlinefood.dto.ProductAddRequest;
import com.onlinefood.model.Category;
import com.onlinefood.model.Product;
import com.onlinefood.service.ImageHandlingService;
import com.onlinefood.service.ProductService;
import com.onlinefood.utility.StorageService;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ImageHandlingService imageService;
	
//	@Autowired
//	private FileUpload fileUpload;
//	@Value("${product.path.images}")
//	
//	private String imagePath;
//	@PostMapping("/image/{productid}")
//    public ResponseEntity<?> uploadImage(@PathVariable int productid , @RequestParam("product_image") MultipartFile file){
//        System.out.println("in image uploading"+productid);
//        Product product = this.productService.getProduct(productid);
//        String imageName = null;
//
//        try{
//
//            String uploadImage = this.fileUpload.uploadImage(imagePath, file);
//            product.setImageName(uploadImage);
//            Productdto updatedProduct = this.productService.update(productid, product);
//            return  new ResponseEntity<>(updatedProduct,HttpStatus.ACCEPTED);
//        }catch (Exception exception){
//            exception.printStackTrace();
//            return new ResponseEntity<>(Map.of("message","file not upload in server"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//	
//	@GetMapping(value = "/getimage/{productid}",produces = MediaType.IMAGE_JPEG_VALUE)
//	public ResponseEntity<?> getProductImage(@PathVariable int productid)
//	{
//		Product product = productService.getProduct(productid);
//		try
//		{
//			byte[] imageBytes = fileUpload.downloadImage(product.getImageName(), imagePath);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.IMAGE_JPEG);   // Set the content type of the response
//			
//			
//			// Set the response body and headers
//			return new ResponseEntity<>(imageBytes,headers,HttpStatus.OK);  
//			
//		}
//		catch(Exception exception)
//		{
//			exception.printStackTrace();
//			return new ResponseEntity<>(Map.of("message", "Error downloading image"), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping(value="/{productId}/image",consumes = "multipart/form-data")
	public ResponseEntity<?> uploadImageToServerSideFolder(@RequestParam MultipartFile imageFile, @PathVariable Integer productId) throws IOException
	{
		System.out.println("In Image Upload " + productId +" " + imageFile.getOriginalFilename());
		return new ResponseEntity<>(imageService.uploadImage(productId, imageFile),HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{productId}/image", produces = { MediaType.IMAGE_GIF_VALUE, 
 			MediaType.IMAGE_JPEG_VALUE,
 			MediaType.IMAGE_PNG_VALUE,})
	public ResponseEntity<?> serveImageFromServerSideFolder(@PathVariable Integer productId) throws IOException {
 		System.out.println("in serve img " + productId);
 		return new ResponseEntity<>(imageService.serveImage(productId), HttpStatus.OK);
 	}

	
	@PostMapping("add")
	public ResponseEntity<?> addProduct(ProductAddRequest productDto) {
		System.out.println("recieved request for ADD PRODUCT");
		System.out.println(productDto);
		Product product=ProductAddRequest.toEntity(productDto);
		
		Optional<Category> optional = categoryDao.findById(productDto.getCategoryId());
		Category category = null;
		if(optional.isPresent()) {
			category = optional.get();
		}
		
		product.setCategory(category);
		productService.addProduct(product, productDto.getImage());
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(product);
		
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllProducts() {
		
		System.out.println("request came for getting all products");
		
		List<Product> products = new ArrayList<Product>();
		
		products = productDao.findAll();
		
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(products);
		
	}
	
	@GetMapping("id")
	public ResponseEntity<?> getProductById(@RequestParam("productId") int productId) {
		
		System.out.println("request came for getting Product by Product Id");
		
		Product product = new Product();
		
		Optional<Product> optional = productDao.findById(productId);
		
		if(optional.isPresent()) {
			product = optional.get();
		}
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(product);
		
	}
	
	@GetMapping("category")
	public ResponseEntity<?> getProductsByCategories(@RequestParam("categoryId") int categoryId) {
		
		System.out.println("request came for getting all products by category");
		
		List<Product> products = new ArrayList<Product>();
		
		products = productDao.findByCategoryId(categoryId);
		
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(products);
		
	}
	
	@GetMapping(value="/{productImageName}", produces = "image/*")
	public void fetchProductImage(@PathVariable("productImageName") String productImageName, HttpServletResponse resp) {
		System.out.println("request came for fetching product pic");
		System.out.println("Loading file: " + productImageName);
		Resource resource = storageService.load(productImageName);
		if(resource != null) {
			try(InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("response sent!");
	}

}
