package com.onlinefood.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.onlinefood.dto.ApiResponse;

public interface ImageHandlingService 
{
	ApiResponse uploadImage(Integer Id, MultipartFile imageFile) throws IOException;
	
	byte[] serveImage(Integer Id) throws IOException;
}
