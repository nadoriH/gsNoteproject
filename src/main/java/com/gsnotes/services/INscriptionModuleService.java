package com.gsnotes.services;
import java.util.List;

import com.gsnotes.bo.InscriptionModule;
public interface INscriptionModuleService {
	public List<InscriptionModule> getAllInscriptionModule ();
  
   
   public List<InscriptionModule> findByModule(Long idmodule);
}
