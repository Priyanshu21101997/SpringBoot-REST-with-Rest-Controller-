package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Alien;

import java.util.List;

// In arguments mention class name and type of PrimaryKey 
// CrudRepository for normal crud
// JPARepository for RestServices
public interface AlienRepo extends JpaRepository<Alien, Integer> {
	
	List<Alien> findByTech(String tech);
	List<Alien> findByAidGreaterThan(int aid);
	
	// Defining own queries
	// tech=?1 means we are passing one argument
	@Query("from Alien where tech=?1 order by aname")
	List<Alien> findByTechSorted(String tech);
}
