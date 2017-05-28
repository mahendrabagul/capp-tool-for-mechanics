package com.variantcapptool.repository;

import com.variantcapptool.domain.Part;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Part entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartRepository extends JpaRepository<Part,Long> {

}
