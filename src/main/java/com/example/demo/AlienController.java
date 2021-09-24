package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AlienRepo;
import com.example.demo.model.Alien;

//@Controller  
//If we don't want to mention responsebody again n again in restservices instead of controller use rest controller
@RestController
public class AlienController {
	
	@Autowired
	AlienRepo repo; // Automatically we will get an object of AlienRepo
	
	@RequestMapping("/")
	public String home() {
		
		return "home.jsp";
	}
	
	@RequestMapping("/addAlien")
	public String addAlien(Alien alien) {
		
		
		repo.save(alien);
		return "home.jsp";
	}
	
	@RequestMapping("/fetchAlien")
	public ModelAndView fetchAlien(@RequestParam int aid) {
		
		// Find an alien by using their aid
		
		Alien alien = repo.findById(aid).orElse(new Alien()); // If id is not found in database then we should returns a dummy object
		ModelAndView mv = new ModelAndView("showAlien.jsp"); // We can pass view(.jsp) in constructor as well
		mv.addObject(alien);

		return mv;
		
		// Complex Query to search aliens
		
		// 1) Find aliens by their technology-> Declare method in interface AlienRepo 
		// Rules to follow -> Method name should start from findBy or getBy and after that is should be name of property of class
		
//		System.out.println(repo.findByTech("Java"));
		
//		2) Find aliens having aid>1
		
//		System.out.println(repo.findByAidGreaterThan(1));
		
		// 3) Writing custom queries -> find tech sorted, query in AlienRepo
		
//		System.out.println(FindByTechSorted("Java"));

	}
	
	// Making REST Services 
	
	//1) Get Request - =======> Getting data as string
	
	// If we want to not return any view and instead show data we will pass ResponseBody
//	@RequestMapping("/aliens")
//	@ResponseBody
//	public String getAliens() {
//		
//		return repo.findAll().toString(); //To convert to String object
//	}
	
	//2) Path Variable
//	@RequestMapping("/alien/{aid}")
//	@ResponseBody
//	public String getAliens(@PathVariable("aid") int aid) {
//		
//		return repo.findById(aid).toString(); //To convert to String object
//	}
	
	// ====> Getting data as Json (Although we are getting List of aliens but in maven dependency Jackson convertes Java object into JSON
	
	//1)Change crudrepository to Jpa Repository
	//2)Change return type from string to Optional<Alien>
	@RequestMapping("/aliens")
	@ResponseBody
	public List<Alien> getAliens() {
		
		return repo.findAll();
	}
	
	@RequestMapping("/alien/{aid}")
	@ResponseBody
	public Optional<Alien> getAliens(@PathVariable("aid") int aid) { // Will return alien object if no Alien object found it will return optional data
		
		return repo.findById(aid);
	}
	
//	====> Content negotiation Prevent json from generating
	
//	@RequestMapping(path="/aliens", produces = {"application/xml"})
//	@ResponseBody
//	public List<Alien> getAliens() {
//		
//		return repo.findAll();
//	}
	
	// 2) Post Request
	
//	@PostMapping("/alien")  // By default it is get 
////	@ResponseBody // Also if I don't want to mention response body coz in rest api we anyway need it always instead of controller we will use a @RestController instead of controller
//	public Alien getAliens(Alien alien) {
//		
//		repo.save(alien);
//		return alien;
//	}
	
	// To insert json data into DB by sending raw JSON from postman we need to enable @RequestBody in argument
	@PostMapping(path="/alien",consumes= {"application/json"})  // We can use consume to only accept JSON
	public Alien getAliens(@RequestBody Alien alien) {
		
		repo.save(alien);
		return alien;
	}
	
//	3)Delete a data 
	@DeleteMapping("/alien/{aid}")  // We can use consume to only accept JSON
	public String deleteAlien(@PathVariable("aid") int aid) {
		
		Alien alien = repo.getOne(aid);
		repo.delete(alien);
		return "Deleted successfully" ;
	}
	
//	4)Update a data
	@PutMapping("/alien")
	public Alien updateAlien(@RequestBody Alien alien) { // Same as post
		
		repo.save(alien);
		return alien;
	}
	
}
