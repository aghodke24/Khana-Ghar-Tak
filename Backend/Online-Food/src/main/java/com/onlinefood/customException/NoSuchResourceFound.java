package com.onlinefood.customException;

public class NoSuchResourceFound  extends RuntimeException
{
	public  NoSuchResourceFound(String msg)
	{
        super(msg);
    }
}
