package com.onlinefood.dto;

import java.time.LocalDateTime;

public class ApiResponse 
{
	private String message;
	private LocalDateTime timeStamp;
	public ApiResponse(String message)
	{
		super();
		this.message=message;
		this.timeStamp=LocalDateTime.now();
	}
}
