package com.gsnotes.web.models;

import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Module;
public class UserPreference {
Module module;
Niveau niveau ;
Integer session ;
public Integer getSession() {
	return session;
}
public void setSession(Integer session) {
	this.session = session;
}
public Niveau getNiveau() {
	return niveau;
}
public Module getModule() {
	return module;
}
public void setModule(Module module) {
	this.module = module;
}
public void setNiveau(Niveau niveau) {
	this.niveau = niveau;
}
@Override
public String toString() {
	return "UserPreference [module=" +module+ ", niveau=" + niveau + "]";
}





}
