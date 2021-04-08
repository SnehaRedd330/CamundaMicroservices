package com.mudynamics.metadataservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.metadataservice.models.ContextMenu;

/**
 * MongoRepository for  ContextMenuRepository
 * @author SB00485980
 *
 */
@Repository
public interface ContextMenuRepository extends JpaRepository<ContextMenu, Long> {

	public List<ContextMenu> findByPalletIconId(Long palletIconId);
	
	public void deleteByPalletIconId(Long palletIconId);
	
	Optional<ContextMenu> findByIdAndPalletIconId(Long id, Long palletIconId);
	
	Optional<ContextMenu> findByName(String name);
	
	boolean existsByName(String name);

}
