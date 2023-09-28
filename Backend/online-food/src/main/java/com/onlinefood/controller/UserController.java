package com.onlinefood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinefood.dao.AddressDao;
import com.onlinefood.dao.UserDao;
import com.onlinefood.dto.AddAdminRequest;
import com.onlinefood.dto.AddUserRequest;
import com.onlinefood.dto.AdminLoginRequest;
import com.onlinefood.dto.UserLoginRequest;
import com.onlinefood.model.Address;
import com.onlinefood.model.User;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@PostMapping("register")
	public ResponseEntity<?> registerUser(@RequestBody AddUserRequest userRequest) {
		System.out.println("recieved request for REGISTER USER");
		System.out.println(userRequest);
		
		Address address = new Address();
		address.setCity(userRequest.getCity());
		address.setPincode(userRequest.getPincode());
		address.setStreet(userRequest.getStreet());
		
		Address addAddress = addressDao.save(address);
		
		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(userRequest.getEmailId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhoneNo(userRequest.getPhoneNo());
		//String encodedPassword= encoder.encode();
		user.setPassword(userRequest.getPassword());
		user.setRole(userRequest.getRole());
		User addUser = userDao.save(user);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(addUser);
	}
	
	
	@PostMapping("admin/register")
	public ResponseEntity<?> registerAdmin(@RequestBody AddAdminRequest adminRequest) {
		System.out.println("recieved request for REGISTER ADMIN");
		System.out.println(adminRequest);
		
		Address address = new Address();
		address.setCity(adminRequest.getCity());
		address.setPincode(adminRequest.getPincode());
		address.setStreet(adminRequest.getStreet());
		
		Address addAddress = addressDao.save(address);
		
		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(adminRequest.getEmailId());
		user.setFirstName(adminRequest.getFirstName());
		user.setLastName(adminRequest.getLastName());
		user.setPhoneNo(adminRequest.getPhoneNo());
		user.setPassword(adminRequest.getPassword());
		user.setRole(adminRequest.getRole());
		User addUser = userDao.save(user);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(addUser);
	}
	
	@PostMapping("register/deliveryperson")
	public ResponseEntity<?> registerDeliveryPerson(@RequestBody AddUserRequest userRequest) {
		System.out.println("recieved request for REGISTER DELIVERY PERSON");
		System.out.println(userRequest);
		
		Address address = new Address();
		address.setCity(userRequest.getCity());
		address.setPincode(userRequest.getPincode());
		address.setStreet(userRequest.getStreet());
		
		Address addAddress = addressDao.save(address);
		
		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(userRequest.getEmailId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhoneNo(userRequest.getPhoneNo());
		user.setPassword(userRequest.getPassword());
		user.setRole(userRequest.getRole());
		User addUser = userDao.save(user);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(addUser);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
		System.out.println("recieved request for LOGIN USER");
		System.out.println(loginRequest);
		
		User user = new User();
		user = userDao.findByEmailIdAndPasswordAndRole(loginRequest.getEmailId(), loginRequest.getPassword(),loginRequest.getRole());
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("admin/login")
	public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest loginRequest) {
		System.out.println("recieved request for LOGIN Admin");
		System.out.println(loginRequest);
		
		User user = new User();
		user = userDao.findByEmailIdAndPasswordAndRole(loginRequest.getEmailId(), loginRequest.getPassword(),loginRequest.getRole());
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("deliveryperson/all")
	public ResponseEntity<?> getAllDeliveryPersons() {
		System.out.println("recieved request for getting ALL Delivery Persons!!!");
		
		List<User> deliveryPersons = this.userDao.findByRole("Delivery");
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(deliveryPersons);
	}

}
