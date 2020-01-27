package com.ipiecoles.java.mdd050.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;

@RestController
@RequestMapping("/managers")
public class ManagerController {

	@Autowired
	TechnicienRepository technicienRepository;
	@Autowired
	ManagerRepository managerRepository;
	
	
	@RequestMapping(value = "/{idManager}/equipe/{idTechnicien}/remove", method = RequestMethod.GET)
	public void deleletTech(@PathVariable("idManager") Long idmanager, @PathVariable("idTechnicien") Long idtechnicien) {
		Technicien t=technicienRepository.findById(idtechnicien).get();
		t.setManager(null);
		technicienRepository.save(t);
	}
	
	@RequestMapping(value = "/{idManager}/equipe/{matriculeTechnicien}/add", method = RequestMethod.GET)
	public Technicien addTech(@PathVariable("idManager") Long idmanager, @PathVariable("matriculeTechnicien") String matricule) {
		Manager m=managerRepository.findById(idmanager).get();
		Technicien t=technicienRepository.findByMatricule(matricule);
		t.setManager(m);
		return  technicienRepository.save(t);
	}
}
