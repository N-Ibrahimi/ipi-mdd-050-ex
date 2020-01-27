package com.ipiecoles.java.mdd050.controller;

import java.util.List;
import java.util.Set;

import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;

@RestController
@RequestMapping(value = "/employes")
public class EmployeController {
	
	@Autowired
	EmployeRepository employesRepository;
	@Autowired
	ManagerRepository maneRepository;
	@Autowired
	TechnicienRepository technicienRepository; 
	
	@RequestMapping(value = "/count" , method = RequestMethod.GET)
	public Long countEmployes() {	
		return employesRepository.count();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET )
	public Employe getEmploye(@PathVariable("id") Long id_emp) {
		if(id_emp!=null) {
			Employe emp = employesRepository.findById(id_emp).get();
			System.out.println(emp);
			return emp;
		}else {
			return null;
		}
	}
	
	@RequestMapping(value = " ", params = "matricule", method = RequestMethod.GET)
	public Employe getbyMatricule(@RequestParam(value ="matricule")String matricule) {
		Employe emp=employesRepository.findByMatricule(matricule);
		if(emp!=null) {
			return emp;
		}else {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<Employe> get_all_emp(
			@RequestParam(value = "page",defaultValue ="0" )Integer page, //required=false
			@RequestParam(value = "size", defaultValue = "10")Integer size, 
			@RequestParam(value = "sortProperty", defaultValue = "matricule") String sortProperty, 
			@RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDireciton){
		/*
		 * @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDireciton){
		 * Pageable pageable = PageRequest.of(page,size,sortDireciton, sortProperty); if we define the sort direction by default, we cas use this code 
		 */
		Pageable pageable = PageRequest.of(page,size,Sort.Direction.fromString(sortDireciton), sortProperty);
		return employesRepository.findAll(pageable);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "")
	public Employe createEmploye(@RequestBody Commercial commercial){
		return employesRepository.save(commercial);
	}
	

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public Employe ModifyEmploye(@RequestBody Employe employe, @PathVariable(value = "id") Long id){
		return employesRepository.save(employe);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteEmploye(@PathVariable(value = "id") Long id){
				employesRepository.deleteById(id);

	}

}
