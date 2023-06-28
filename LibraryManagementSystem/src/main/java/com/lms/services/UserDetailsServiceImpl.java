package com.lms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lms.config.CustomerUserDetails;
import com.lms.dao.UserRepository;
import com.lms.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		System.out.println("User: "+user);
		if(user==null) {
			throw new UsernameNotFoundException("Wrong Username or Password");
		}
		CustomerUserDetails customerUserDetails=new CustomerUserDetails(user);
		System.out.println("customerUserDetails: "+customerUserDetails);
		return customerUserDetails;
	}

}
