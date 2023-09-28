package com.onlinefood.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinefood.model.User;

public interface AdminDao extends JpaRepository<User, Integer> 
{
	User findByEmailIdAndPasswordAndRole(String emailId, String password, String role);
	List<User> findByRole(String role);
}
