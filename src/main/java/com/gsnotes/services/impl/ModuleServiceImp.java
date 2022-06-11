package com.gsnotes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.dao.IModuleDao;
import com.gsnotes.services.IModuleService;
import com.gsnotes.bo.Module;
@Service
public class ModuleServiceImp implements IModuleService{
@Autowired
	IModuleDao moduledao ;

@Override
public List<Module> getAllModules() {
	
	return moduledao.findAll();
}

public Module getModuleById(Long id) {
	
	return moduledao.getById(id);
}
	
}
