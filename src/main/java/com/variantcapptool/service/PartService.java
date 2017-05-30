package com.variantcapptool.service;

import com.variantcapptool.domain.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Part.
 */
public interface PartService
{

	/**
	 * Save a part.
	 *
	 * @param part the entity to save
	 * @return the persisted entity
	 */
	Part save(Part part);

	/**
	 *  Get all the parts.
	 *  
	 *  @param pageable the pagination information
	 *  @return the list of entities
	 */
	Page<Part> findAll(Pageable pageable);

	/**
	 *  Get the "id" part.
	 *
	 *  @param id the id of the entity
	 *  @return the entity
	 */
	Part findOne(Long id);

	/**
	 *  Delete the "id" part.
	 *
	 *  @param id the id of the entity
	 */
	void delete(Long id);

	/**
	 * 
	 * @param partCode
	 * @return
	 */
	Part findByPartCode(String partCode);
}
