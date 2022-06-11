package com.gsnotes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gsnotes.bo.Module;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.dao.InscriptionModuleDao;
import com.gsnotes.services.INscriptionModuleService;

@Service
public class IncsriptionModuleImpl implements INscriptionModuleService{
	
@Autowired
InscriptionModuleDao inscrmoduledao ;

	@Override
	public List<InscriptionModule> getAllInscriptionModule() {
		
		return inscrmoduledao.findAll();
	}

	@Override
	public List<InscriptionModule> findByModule(Long idmodule) {
		   Module mod = new Module();
		   mod.setIdModule(idmodule);
		   inscrmoduledao.findByModule(mod);
		return null;
	}


	

	

	
	

	
}
