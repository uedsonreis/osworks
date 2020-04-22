package com.uedsonreis.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uedsonreis.osworks.domain.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	public List<Client> findByName(String name);
	public List<Client> findByNameContaining(String name);
	
}