package com.gsnotes.dao;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.InscriptionAnnuelle;

public interface IncriptionAnnuelleDao extends JpaRepository<InscriptionAnnuelle, Long>{

	
}
