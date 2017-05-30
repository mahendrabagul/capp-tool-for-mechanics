package com.variantcapptool.web.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.variantcapptool.common.components.DocsComponent;
import com.variantcapptool.common.util.PartCodeUtil;
import com.variantcapptool.domain.Part;
import com.variantcapptool.service.PartService;
import com.variantcapptool.service.dto.PartDTO;
import com.variantcapptool.web.rest.util.HeaderUtil;
import com.variantcapptool.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Part.
 */
@RestController
@RequestMapping("/api")
public class PartResource
{

	private final Logger		log			= LoggerFactory.getLogger(PartResource.class);

	private static final String	ENTITY_NAME	= "part";

	private final PartService	partService;

	@Autowired
	private DocsComponent		docsComponent;

	public PartResource(PartService partService)
	{
		this.partService = partService;
	}

	/**
	 * POST  /parts : Create a new part.
	 *
	 * @param part the part to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new part, or with status 400 (Bad Request) if the part has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/parts")
	@Timed
	public ResponseEntity<Part> createPart(@RequestBody Part part) throws URISyntaxException
	{
		log.debug("REST request to save Part : {}", part);
		if (part.getId() != null)
		{
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new part cannot already have an ID"))
					.body(null);
		}
		Part result = partService.save(part);
		return ResponseEntity.created(new URI("/api/parts/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT  /parts : Updates an existing part.
	 *
	 * @param part the part to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated part,
	 * or with status 400 (Bad Request) if the part is not valid,
	 * or with status 500 (Internal Server Error) if the part couldnt be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/parts")
	@Timed
	public ResponseEntity<Part> updatePart(@RequestBody Part part) throws URISyntaxException
	{
		log.debug("REST request to update Part : {}", part);
		if (part.getId() == null)
		{
			return createPart(part);
		}
		Part result = partService.save(part);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, part.getId().toString()))
				.body(result);
	}

	/**
	 * GET  /parts : get all the parts.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of parts in body
	 */
	@GetMapping("/parts")
	@Timed
	public ResponseEntity<List<Part>> getAllParts(@ApiParam Pageable pageable)
	{
		log.debug("REST request to get a page of Parts");
		Page<Part> page = partService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parts");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET  /parts/:id : get the "id" part.
	 *
	 * @param id the id of the part to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the part, or with status 404 (Not Found)
	 */
	@GetMapping("/parts/{id}")
	@Timed
	public ResponseEntity<Part> getPart(@PathVariable Long id)
	{
		log.debug("REST request to get Part : {}", id);
		Part part = partService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(part));
	}

	/**
	 * DELETE  /parts/:id : delete the "id" part.
	 *
	 * @param id the id of the part to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/parts/{id}")
	@Timed
	public ResponseEntity<Void> deletePart(@PathVariable Long id)
	{
		log.debug("REST request to delete Part : {}", id);
		partService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@PostMapping(value = "/parts/download/{partName}")
	public void downloadFile(HttpServletResponse response, @PathVariable("partName") String partName) throws IOException
	{
		File file = null;
		String fileNameWithPath = docsComponent.getFileName(partName);
		if (fileNameWithPath != null)
		{
			file = new File(fileNameWithPath);
			if (!file.exists())
			{
				String errorMessage = "Sorry. The file you are looking for does not exist";
				System.out.println(errorMessage);
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
				return;
			}
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null)
			{
				System.out.println("mimetype is not detectable, will take default");
				mimeType = "application/octet-stream";
			}
			System.out.println("mimetype : " + mimeType);
			response.setContentType(mimeType);
			/* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
			    while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			/* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
			//response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			//Copy bytes from source to destination(outputstream in this example), closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}

	@PostMapping("/parts/searchPartCode")
	@Timed
	public ResponseEntity<Part> searchPartCode(@RequestBody PartDTO partDTO) throws URISyntaxException
	{
		log.debug("REST request to save Part : {}", partDTO);
		String partCode = PartCodeUtil.getPartCode(partDTO);
		//	partCode = "99923";
		Part result = partService.findByPartCode(partCode);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}
}
