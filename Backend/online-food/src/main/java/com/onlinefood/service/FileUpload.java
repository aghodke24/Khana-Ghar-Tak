package com.onlinefood.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUpload 
{
	public String uploadImage(String path, MultipartFile file)
	{
		String originalFileName = file.getOriginalFilename();
		String randomImageName = UUID.randomUUID().toString();
		String fileName = randomImageName.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
		String fullPath = path+File.separator+fileName;
		
		File fileFolder = new File(path);
		
		if(!fileFolder.exists())
		{
			fileFolder.mkdirs();
		}
		try
		{
			Files.copy(file.getInputStream(), Paths.get(fullPath));
		}
		catch(Exception exception)
		{
			exception.getStackTrace();
		}
		return fileName;
	}
	
	public byte[] downloadImage(String imageName,String imagePath) throws IOException
	{
		String fullPath = imagePath + File.separator + imageName;
        Path imageFilePath = Paths.get(fullPath);

        return Files.readAllBytes(imageFilePath);
	}
}
