package com.gsnotes.services;

import java.util.List;

import com.gsnotes.utils.export.ExcelExporter;

import com.gsnotes.bo.*;
import com.gsnotes.bo.Module;
public interface INiveauService {
   // public List<Module> getAllModules();
	
	public List<Niveau> getAllNiveaux();
     public Niveau getNiveauById(Long id);
	
	///public Compte getAccountByUserName(String login);
	
	//public String createUser(Long idRole, Long idPerson);
	
	public ExcelExporter prepareModeleExport(Module modules) ;
}
