package com.gsnotes.services.impl;

import java.util.List;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.Module;
import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Role;
import com.gsnotes.bo.Utilisateur;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.services.INiveauService;
import com.gsnotes.utils.export.ExcelExporter;
@Service
public class NiveauServiceImpl  implements INiveauService{

	@Autowired
	INiveauDao niveaudao;

	@Override
	public List<Niveau> getAllNiveaux() {
		
		
		return niveaudao.findAll();
	}

	

	@Override
	public ExcelExporter prepareModeleExport(Module modules) {
		String[] columnNames = new String[] { "Module" };
		String[][] data = {{}} ;

		int i = 0;
		/*for (Module m : modules) {
			data[i][0] = m.getTitre();
			i++;
			}*/

		return new ExcelExporter(columnNames, data, "module");
	}



	@Override
	public Niveau getNiveauById(Long id) {
		 return niveaudao.getById(id);
	}


	

}
