package com.ipiecoles.java.mdd050.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipiecoles.java.mdd050.repository.TechnicienRepository;

@RestController
@RequestMapping("/technicien")
public class TechnicienController {
	
	@Autowired
	TechnicienRepository technicienRepository;
	
	@RequestMapping(value = "/{idmanager}/equipe/{matricule}/add")
	public void addManager(@PathVariable(value = "idmanager") Long idManager, @PathVariable(value = "matricule") String matricule) {
		
	}

}
