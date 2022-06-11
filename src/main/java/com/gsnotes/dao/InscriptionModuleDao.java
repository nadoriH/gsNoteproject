package com.gsnotes.dao;
import com.gsnotes.bo.Module;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.InscriptionModule;



public interface InscriptionModuleDao extends JpaRepository<InscriptionModule, Long>{
			
  public List<InscriptionModule> findByModule(Module module);
}
