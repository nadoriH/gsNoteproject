package com.gsnotes.web.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;
import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Utilisateur;
import com.gsnotes.services.INscriptionModuleService;
import com.gsnotes.services.impl.ModuleServiceImp;
import com.gsnotes.services.impl.NiveauServiceImpl;
import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.utils.export.ExcelHandler;
import com.gsnotes.utils.export.ExcelHandlerException;
import com.gsnotes.utils.export.ExcelHandlere;
import com.gsnotes.web.models.UserPreference;
import com.gsnotes.utils.export.ExcelHandler;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noteFile") 
public class GenerateFileController {
	
@Autowired
ModuleServiceImp moduledao;

@Autowired
NiveauServiceImpl niveaudao;
@Autowired 
INscriptionModuleService inscrmoduledao ;


   @RequestMapping(value = "showPage", method = RequestMethod.GET)
    public String showPage(Model model) {
		UserPreference pref = new UserPreference();
		model.addAttribute("preferences",pref);
	  	
		
	 return "admin/GenerateFile";
}
   
   
    @RequestMapping(value="showPreferences", method = RequestMethod.POST)
	public String ShowPreferences(@ModelAttribute("preferences") UserPreference pref) {
		//System.out.println("Id Module "+pref.getModule().getIdModule());
		 if(pref.getModule()!= null) {
			 
			 System.out.println("Telechargement de fichier de module "+pref.getModule().getTitre()+".....");
			 return "redirect:/noteFile/export?idN="+(pref.getNiveau()!= null?(pref.getNiveau().getIdNiveau()):-1)+"&&idS="+pref.getSession()+"&&idM="+pref.getModule().getIdModule();
		 }else {
			 System.out.println("Telechargement des fichiers de niveau "+pref.getNiveau()+".....");
			 return "redirect:/noteFile/exportzip?idN="+(pref.getNiveau()!= null?(pref.getNiveau().getIdNiveau()):-1)+"&&idS="+pref.getSession();
		 }
	      
		 
		
	}
	
	@ModelAttribute("allNiveau")
	public List<Niveau> populateNiveau(){
		  ArrayList<Niveau> niveaux = new ArrayList<Niveau>();
		  niveaux.add(new Niveau(new Long(-1),"---- Choisir Niveau ----"));
		// ArrayList<Niveau> niveaux = (ArrayList<Niveau>) niveaudao.getAllNiveaux();
		  
	      for(Niveau nv : niveaudao.getAllNiveaux()) {
	        Niveau n = new Niveau(nv.getIdNiveau(),nv.getTitre());
	        	niveaux.add(nv);
	        }
	       
	       return niveaux;
	}
	
	@ModelAttribute("allModules")
	
    public List<Module> populateModules() 
    {
        ArrayList<Module> modules = new ArrayList<Module>();
         modules.add(new Module(new Long(-1),"--- Choisir Module ---"));
        for(Module md : moduledao.getAllModules()) {
        	Module  m = new Module(md.getIdModule(),md.getTitre());
        	//System.out.println(m);
        	modules.add(m);
        }
       
        return modules;
    }
	
	
	@GetMapping(value = "exportzip")
	public void zipFiles(HttpServletResponse response, @RequestParam Long idN,@RequestParam Integer idS) throws IOException, ExcelHandlerException {

		
		
		response.setStatus(HttpServletResponse.SC_OK);
		String name = niveaudao.getNiveauById(idN).getTitre();
		response.addHeader("Content-Disposition", "attachment; filename=\""+name+".zip\"");
		

		ByteArrayOutputStream workbookByteArrayStream = null;
		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
		ZipEntry zipEntry = null;
		HashMap<String,XSSFWorkbook> workbooks = new HashMap<String, XSSFWorkbook>();

		// get all modules for that level
		List<Module> modules = niveaudao.getNiveauById(idN).getModules();
		
		for (Module mdl : modules ) {
			
			 List<InscriptionModule> liste   = inscrmoduledao.getAllInscriptionModule();
		     List<Etudiant> etds = new ArrayList<Etudiant>();
		     for(InscriptionModule ins : liste) {
		    	 if(ins.getModule().getIdModule()== mdl.getIdModule()  )
		    	  etds.add(ins.getInscriptionAnnuelle().getEtudiant());
		     }
		 
			   
			   System.out.println(mdl);
				//ExcelHandler exp = ExcelHandler.getInstance();
				 List<ArrayList<Object>> pDataAndHeader = new ArrayList<ArrayList<Object>>() ;
			     ArrayList<Object> l1 = new ArrayList<Object>();
			     ArrayList<Object> l2 = new ArrayList<Object>();
			     ArrayList<Object> l3 = new ArrayList<Object>();
			     ArrayList<Object> l4 = new ArrayList<Object>();
			   l1.add("Module"); l1.add( mdl.getTitre());l1.add("Semestre");l1.add(mdl.getCode().equals("A")?"Autonome":"Printempe");l1.add("Année ");l1.add("2021/2022");
			   l2.add("Enseignant");l2.add(mdl.getEnseignant().getNom()); l2.add("Session");  l2.add(idS==1?"Rattrappage":"Normale"); l2.add("Classe"); l2.add(mdl.getNiveau().getTitre());
		       l3.add("ID"); l3.add("CNE");l3.add("NOM"); l3.add("PRENOM");
				   //System.out.println(mod.getElements());
				   if(mdl.getElements().isEmpty()) {
					   l3.add(mdl.getTitre());
				   }else {
					   for(Element elm :mdl.getElements()) {
						   
						   l3.add(elm.getNom());
					   }
				   }
				   
				    l3.add("Moyenne");
				    l3.add("Validation");
				    pDataAndHeader.add(l1);
				    pDataAndHeader.add(l2);
				    pDataAndHeader.add(l4);
				    pDataAndHeader.add(l3);
				   
			
				     
						for(Etudiant etd : etds) {
							 ArrayList<Object> lx = new ArrayList<Object>();
							   lx.add(etd.getCin());
							   lx.add(etd.getCne());
							   lx.add(etd.getNom());
							   lx.add(etd.getPrenom());
							
							pDataAndHeader.add(lx);
						   }
						ExcelHandlere ex = new ExcelHandlere();
						ex.export( mdl.getTitre(), pDataAndHeader);
				
					workbooks.put(mdl.getTitre(),ex.getWorkbook());
		}
		
		
		

			
		

			
		

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:ms");

		int i = 0;
		for (String  wb : workbooks.keySet()) {
			String currentDateTime = dateFormatter.format(new Date());
			zipEntry = new ZipEntry(wb + currentDateTime + i + ".xlsx");
			zipOutputStream.putNextEntry(zipEntry);
			workbookByteArrayStream = new ByteArrayOutputStream();
			workbooks.get(wb).write(workbookByteArrayStream);
			zipEntry.setSize(workbookByteArrayStream.size());
			zipOutputStream.write(workbookByteArrayStream.toByteArray());
			zipOutputStream.closeEntry();
			i++;
		}
		zipOutputStream.close();

	}
	
	// Code de Module
	@GetMapping("export")
	public void exportTox(HttpServletResponse response,@RequestParam Long idN, @RequestParam Long idM,@RequestParam Integer idS) throws IOException, InterruptedException, ExcelHandlerException {
		System.out.println("Bonjour Hicham");
	
   Module mod = moduledao.getModuleById(idM);
	   
	   System.out.println(mod);
		ExcelHandler exp = ExcelHandler.getInstance();
		 List<ArrayList<Object>> pDataAndHeader = new ArrayList<ArrayList<Object>>() ;
	     ArrayList<Object> l1 = new ArrayList<Object>();
	     ArrayList<Object> l2 = new ArrayList<Object>();
	     ArrayList<Object> l3 = new ArrayList<Object>();
	     ArrayList<Object> l4 = new ArrayList<Object>();
	   l1.add("Module"); l1.add( mod.getTitre());l1.add("Semestre");l1.add(mod.getCode().equals("A")?"Autonome":"Printempe");l1.add("Année ");l1.add("2021/2022");
	   l2.add("Enseignant");l2.add(mod.getEnseignant().getNom()); l2.add("Session");  l2.add(idS==1?"Rattrappage":"Normale"); l2.add("Classe"); l2.add(mod.getNiveau().getTitre());
       l3.add("ID"); l3.add("CNE");l3.add("NOM"); l3.add("PRENOM");
		   //System.out.println(mod.getElements());
		   if(mod.getElements().isEmpty()) {
			   l3.add(mod.getTitre());
		   }else {
			   for(Element elm :mod.getElements()) {
				   
				   l3.add(elm.getNom());
			   }
		   }
		   
		    l3.add("Moyenne");
		    l3.add("Validation");
		    pDataAndHeader.add(l1);
		    pDataAndHeader.add(l2);
		    pDataAndHeader.add(l4);
		    pDataAndHeader.add(l3);
		    
		     List<InscriptionModule> liste   = inscrmoduledao.getAllInscriptionModule();
		     List<Etudiant> etds = new ArrayList<Etudiant>();
		     for(InscriptionModule ins : liste) {
		    	 if(ins.getModule().getIdModule()==idM  )
		    	  etds.add(ins.getInscriptionAnnuelle().getEtudiant());
		     }
		   
		   
				for(Etudiant etd : etds) {
					 ArrayList<Object> lx = new ArrayList<Object>();
					   lx.add(etd.getCin());
					   lx.add(etd.getCne());
					   lx.add(etd.getNom());
					   lx.add(etd.getPrenom());
					
					pDataAndHeader.add(lx);
				   }
			
			
	   
		   response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());
		    String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=Modele_"+mod.getTitre() + currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);
		    exp.export(response,mod.getTitre(),pDataAndHeader);  
	
	  
	   
	
	}
	
}
