package com.variantcapptool.service.impl;

import com.variantcapptool.service.PartService;
import com.variantcapptool.domain.Part;
import com.variantcapptool.repository.PartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Part.
 */
@Service
@Transactional
public class PartServiceImpl implements PartService{

    private final Logger log = LoggerFactory.getLogger(PartServiceImpl.class);
    
    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /**
     * Save a part.
     *
     * @param part the entity to save
     * @return the persisted entity
     */
    @Override
    public Part save(Part part) {
        log.debug("Request to save Part : {}", part);
        Part result = partRepository.save(part);
        return result;
    }

    /**
     *  Get all the parts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Part> findAll(Pageable pageable) {
        log.debug("Request to get all Parts");
        Page<Part> result = partRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one part by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Part findOne(Long id) {
        log.debug("Request to get Part : {}", id);
        Part part = partRepository.findOne(id);
        return part;
    }

    /**
     *  Delete the  part by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Part : {}", id);
        partRepository.delete(id);
    }
}
