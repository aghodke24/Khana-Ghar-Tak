package com.onlinefood.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.onlinefood.customException.NoSuchResourceFound;
import com.onlinefood.dto.ApiResponse;
import com.onlinefood.model.Product;
import com.onlinefood.repository.ProductRepository;

@Service
@Transactional
public class ImageHandlingServiceImpl implements ImageHandlingService
{
	@Value("${product.path.images}")
	private String folderName;
	
	@Autowired
	private ProductRepository productRepo;
	
	@PostConstruct
	public void myInit()
	{
		System.out.println("in myInit " + folderName);
		// chk of folder exists --o.w create one!
		File path = new File(folderName);
		if (!path.exists()) 
		{
			System.out.println("creating images folder...");
			path.mkdirs();
		} 
		else
			System.out.println("folder alrdy exists....");
	}
	
	@Override
	public ApiResponse uploadImage(Integer productId,MultipartFile imageFile) throws IOException
	{
		Product product = productRepo.findById(productId).orElseThrow(
				()->new NoSuchResourceFound("Invalid Product ID"));
		
		String targetPath = folderName +  File.separator +  imageFile.getOriginalFilename();
		System.out.println(targetPath);
		
		Files.copy(imageFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
		
		product.setImageName(targetPath);
		return new ApiResponse("Image Uploaded successfully!");
	}
	
	@Override
	public byte[] serveImage(Integer productId) throws IOException
	{
		Product product = productRepo.findById(productId).orElseThrow(
				()->new NoSuchResourceFound("Invalid Product ID"));
		
		String path = product.getImageName();
		if(path == null)
			throw new NoSuchResourceFound("Image Does Not Exists");
		
		return Files.readAllBytes(Paths.get(path));
	}	
}
