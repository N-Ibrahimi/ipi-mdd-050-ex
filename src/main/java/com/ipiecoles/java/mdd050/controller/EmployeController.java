package com.ipiecoles.java.mdd050.controller;


import java.lang.reflect.Field;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import com.ipiecoles.java.mdd050.exception.GlobalExeptionHandler;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;

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
	
	@RequestMapping(value = " ", params = "matricule", method = RequestMethod.GET)
	public Employe getbyMatricule(@RequestParam(value ="matricule")String matricule) {
		if(matricule==null || !matricule.matches(REGEX_MATRICUL)) {
			throw new IllegalArgumentException("le "+matricule+" n'est pas bon ! ");
		}
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
