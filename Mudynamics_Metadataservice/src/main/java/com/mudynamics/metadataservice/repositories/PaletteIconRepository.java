package com.mudynamics.metadataservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.metadataservice.models.PalletIcon;

@Repository
public interface PaletteIconRepository extends JpaRepository<PalletIcon,Long>{
	
	
	public boolean existsByLabel(String label);
	public Optional<PalletIcon> findByLabel(String label);
	
}
