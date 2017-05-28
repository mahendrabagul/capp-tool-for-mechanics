package com.variantcapptool.web.rest;

import com.variantcapptool.VariantCappToolApp;

import com.variantcapptool.domain.Part;
import com.variantcapptool.repository.PartRepository;
import com.variantcapptool.service.PartService;
import com.variantcapptool.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartResource REST controller.
 *
 * @see PartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VariantCappToolApp.class)
public class PartResourceIntTest {

    private static final String DEFAULT_PART_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PART_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PART_LENGTH_DIMENSION = 1D;
    private static final Double UPDATED_PART_LENGTH_DIMENSION = 2D;

    private static final Double DEFAULT_PART_HEIGHT_WIDTH_DIAMETER = 1D;
    private static final Double UPDATED_PART_HEIGHT_WIDTH_DIAMETER = 2D;

    private static final Double DEFAULT_RATIO_LD = 1D;
    private static final Double UPDATED_RATIO_LD = 2D;

    private static final Integer DEFAULT_NO_OF_HOLES = 1;
    private static final Integer UPDATED_NO_OF_HOLES = 2;

    private static final Integer DEFAULT_MASS = 1;
    private static final Integer UPDATED_MASS = 2;

    private static final String DEFAULT_PART_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PART_CODE = "BBBBBBBBBB";

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartService partService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartMockMvc;

    private Part part;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartResource partResource = new PartResource(partService);
        this.restPartMockMvc = MockMvcBuilders.standaloneSetup(partResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Part createEntity(EntityManager em) {
        Part part = new Part()
            .partName(DEFAULT_PART_NAME)
            .partLengthDimension(DEFAULT_PART_LENGTH_DIMENSION)
            .partHeightWidthDiameter(DEFAULT_PART_HEIGHT_WIDTH_DIAMETER)
            .ratioLD(DEFAULT_RATIO_LD)
            .noOfHoles(DEFAULT_NO_OF_HOLES)
            .mass(DEFAULT_MASS)
            .partCode(DEFAULT_PART_CODE);
        return part;
    }

    @Before
    public void initTest() {
        part = createEntity(em);
    }

    @Test
    @Transactional
    public void createPart() throws Exception {
        int databaseSizeBeforeCreate = partRepository.findAll().size();

        // Create the Part
        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isCreated());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeCreate + 1);
        Part testPart = partList.get(partList.size() - 1);
        assertThat(testPart.getPartName()).isEqualTo(DEFAULT_PART_NAME);
        assertThat(testPart.getPartLengthDimension()).isEqualTo(DEFAULT_PART_LENGTH_DIMENSION);
        assertThat(testPart.getPartHeightWidthDiameter()).isEqualTo(DEFAULT_PART_HEIGHT_WIDTH_DIAMETER);
        assertThat(testPart.getRatioLD()).isEqualTo(DEFAULT_RATIO_LD);
        assertThat(testPart.getNoOfHoles()).isEqualTo(DEFAULT_NO_OF_HOLES);
        assertThat(testPart.getMass()).isEqualTo(DEFAULT_MASS);
        assertThat(testPart.getPartCode()).isEqualTo(DEFAULT_PART_CODE);
    }

    @Test
    @Transactional
    public void createPartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partRepository.findAll().size();

        // Create the Part with an existing ID
        part.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartMockMvc.perform(post("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParts() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        // Get all the partList
        restPartMockMvc.perform(get("/api/parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(part.getId().intValue())))
            .andExpect(jsonPath("$.[*].partName").value(hasItem(DEFAULT_PART_NAME.toString())))
            .andExpect(jsonPath("$.[*].partLengthDimension").value(hasItem(DEFAULT_PART_LENGTH_DIMENSION.doubleValue())))
            .andExpect(jsonPath("$.[*].partHeightWidthDiameter").value(hasItem(DEFAULT_PART_HEIGHT_WIDTH_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].ratioLD").value(hasItem(DEFAULT_RATIO_LD.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfHoles").value(hasItem(DEFAULT_NO_OF_HOLES)))
            .andExpect(jsonPath("$.[*].mass").value(hasItem(DEFAULT_MASS)))
            .andExpect(jsonPath("$.[*].partCode").value(hasItem(DEFAULT_PART_CODE.toString())));
    }

    @Test
    @Transactional
    public void getPart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        // Get the part
        restPartMockMvc.perform(get("/api/parts/{id}", part.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(part.getId().intValue()))
            .andExpect(jsonPath("$.partName").value(DEFAULT_PART_NAME.toString()))
            .andExpect(jsonPath("$.partLengthDimension").value(DEFAULT_PART_LENGTH_DIMENSION.doubleValue()))
            .andExpect(jsonPath("$.partHeightWidthDiameter").value(DEFAULT_PART_HEIGHT_WIDTH_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.ratioLD").value(DEFAULT_RATIO_LD.doubleValue()))
            .andExpect(jsonPath("$.noOfHoles").value(DEFAULT_NO_OF_HOLES))
            .andExpect(jsonPath("$.mass").value(DEFAULT_MASS))
            .andExpect(jsonPath("$.partCode").value(DEFAULT_PART_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPart() throws Exception {
        // Get the part
        restPartMockMvc.perform(get("/api/parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePart() throws Exception {
        // Initialize the database
        partService.save(part);

        int databaseSizeBeforeUpdate = partRepository.findAll().size();

        // Update the part
        Part updatedPart = partRepository.findOne(part.getId());
        updatedPart
            .partName(UPDATED_PART_NAME)
            .partLengthDimension(UPDATED_PART_LENGTH_DIMENSION)
            .partHeightWidthDiameter(UPDATED_PART_HEIGHT_WIDTH_DIAMETER)
            .ratioLD(UPDATED_RATIO_LD)
            .noOfHoles(UPDATED_NO_OF_HOLES)
            .mass(UPDATED_MASS)
            .partCode(UPDATED_PART_CODE);

        restPartMockMvc.perform(put("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPart)))
            .andExpect(status().isOk());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeUpdate);
        Part testPart = partList.get(partList.size() - 1);
        assertThat(testPart.getPartName()).isEqualTo(UPDATED_PART_NAME);
        assertThat(testPart.getPartLengthDimension()).isEqualTo(UPDATED_PART_LENGTH_DIMENSION);
        assertThat(testPart.getPartHeightWidthDiameter()).isEqualTo(UPDATED_PART_HEIGHT_WIDTH_DIAMETER);
        assertThat(testPart.getRatioLD()).isEqualTo(UPDATED_RATIO_LD);
        assertThat(testPart.getNoOfHoles()).isEqualTo(UPDATED_NO_OF_HOLES);
        assertThat(testPart.getMass()).isEqualTo(UPDATED_MASS);
        assertThat(testPart.getPartCode()).isEqualTo(UPDATED_PART_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingPart() throws Exception {
        int databaseSizeBeforeUpdate = partRepository.findAll().size();

        // Create the Part

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartMockMvc.perform(put("/api/parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(part)))
            .andExpect(status().isCreated());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePart() throws Exception {
        // Initialize the database
        partService.save(part);

        int databaseSizeBeforeDelete = partRepository.findAll().size();

        // Get the part
        restPartMockMvc.perform(delete("/api/parts/{id}", part.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Part.class);
        Part part1 = new Part();
        part1.setId(1L);
        Part part2 = new Part();
        part2.setId(part1.getId());
        assertThat(part1).isEqualTo(part2);
        part2.setId(2L);
        assertThat(part1).isNotEqualTo(part2);
        part1.setId(null);
        assertThat(part1).isNotEqualTo(part2);
    }
}
