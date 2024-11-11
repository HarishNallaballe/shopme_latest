package com.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.entitty.Customer;
import com.customer.exception.CustomerNotFoundException;
import com.customer.repo.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repo;
	
	public String saveCustomer(Customer customer) {
		Customer save = repo.save(customer);
		return save.getId()>0?"User Saved":"User Not Saved";
	}
	
	public Customer getCustomer(Integer id) throws CustomerNotFoundException {
		 Customer customer = repo.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer Not Exist"));
		return customer;
	}

}
