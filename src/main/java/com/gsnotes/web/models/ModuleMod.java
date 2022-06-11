package com.gsnotes.web.models;

public class ModuleMod {
Long id;
String titre;


public ModuleMod(Long id) {
	
	this.id = id;
}


public ModuleMod(String titre) {
	
	this.titre = titre;
}


public ModuleMod(Long id, String titre) {
	
	this.id = id;
	this.titre = titre;
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getTitre() {
	return titre;
}


public void setTitre(String titre) {
	this.titre = titre;
}


@Override
public String toString() {
	return "ModuleMod [id=" + id + ", titre=" + titre + "]";
}




}
