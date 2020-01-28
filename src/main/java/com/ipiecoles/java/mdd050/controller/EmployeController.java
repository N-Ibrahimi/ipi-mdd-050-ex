package com.ipiecoles.java.mdd050.controller;


import java.lang.reflect.Field;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;

@Validated
@RestController
@RequestMapping(value = "/employes")
public class EmployeController {
	public static final String REGEX_MATRICUL="^[MTC][0-9] {5}$";
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
	public Employe getEmploye(@PathVariable("id") Long id_emp){
			Optional<Employe> emp = employesRepository.findById(id_emp);
			if(!emp.isPresent()) {
				throw new EntityNotFoundException("l'Employé d'indentifiant "+id_emp+" n'existe pas ! ");
				}
			return emp.get();
			}
	
	/* 2nd solution pour verifier la validation de getBy matricule
  	if(matricule==null || !matricule.matches(REGEX_MATRICUL)) {
	throw new IllegalArgumentException("le "+matricule+" n'est pas bon ! ");
	}
 */

	
	@GetMapping(value = "", params = "matricule")
	public Employe getbyMatricule(
			@NotBlank 
			@Pattern(regexp = REGEX_MATRICUL) // les validates doivent être avant @request param 
			@RequestParam(value ="matricule")String matricule) {
		Employe emp=employesRepository.findByMatricule(matricule);
		if(emp==null ) {
			throw new EntityNotFoundException("l'Employé de Matricule "+matricule+" n'existe pas ! ");
		}else {
			return emp;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<Employe> get_all_emp(
			@RequestParam(value = "page",defaultValue ="0" )Integer page, //required=false
			@RequestParam(value = "size", defaultValue = "10")Integer size, 
			@RequestParam(value = "sortProperty", defaultValue = "matricule") String sortProperty, 
			@RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDireciton){
		
		Field[] fields=Employe.class.getDeclaredFields(); // requpérer les champ de notre classe et voir est ce que la column existe de notre base ou non
		Boolean found=false;
		for(Field f: fields) {
			if(f.getName().equals(sortProperty)) {
				found=true;
				break;
			}
		}
		if(!found) {
			throw new IllegalArgumentException("Le champ " + sortProperty + " n'existe pas.");
		}
		if(page < 0) {
			throw new IllegalArgumentException("La page ne peut pas être moins de zero ");
		}
		Pageable pageable = PageRequest.of(page,size,Sort.Direction.fromString(sortDireciton), sortProperty);
		Long count=employesRepository.count();
		if(size >50){
			throw new  EntityNotFoundException(" la taille de size ne peut pas être supérieur à 50 ");
		} else if( page > (count/size)+1) {
			throw new  EntityNotFoundException(" la taille de page "+ page +" ne correspond pas à la size défini "+ size);
		}else {
			
			return employesRepository.findAll(pageable);
		}
		
		/*
		 * @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDireciton){
		 * Pageable pageable = PageRequest.of(page,size,sortDireciton, sortProperty); if we define the sort direction by default, we cas use this code 
		 */
	}
	
	
	@RequestMapping(value = "", method=RequestMethod.POST)
	public Employe createEmploye(@Valid @RequestBody Commercial commercial, BindingResult bindingResult){
		//@vaild must be befor @requestbody 
		//@ResponseStatus
		//@ExceptionHandler
		//method argument not valid exception if the valid is not respected 
		//if(bindingResult.hasErrors()) {
				return commercial;
		//}else {
		//	return employesRepository.save(commercial);
		//}
	}
	

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public Employe ModifyEmploye(@Valid @RequestBody Employe employe, @PathVariable(value = "id") Long id){
		return employesRepository.save(employe);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteEmploye(@PathVariable(value = "id") Long id){
				employesRepository.deleteById(id);

	}

}
