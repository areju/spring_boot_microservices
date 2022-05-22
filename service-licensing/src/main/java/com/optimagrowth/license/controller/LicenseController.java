package com.optimagrowth.license.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LiscenceService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
@CrossOrigin( origins = "http://localhost:8080")
public class LicenseController {
	
	@Autowired
	private LiscenceService liscenceService;
	
	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(
	                 @PathVariable("organizationId") String organizationId,
	                @PathVariable("licenseId") String licenseId) {
	       
	        License license = liscenceService.getLicense(licenseId, organizationId);
	        license.add(
	                 linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
	                linkTo(methodOn(LicenseController.class).createLicense(organizationId, license, null)).withRel("createLicense"),
	                 linkTo(methodOn(LicenseController.class).updateLicense(organizationId, license)).withRel("updateLicense"),
	                linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, license.getLicenseId())).withRel("deleteLicense"));
	       
	        return ResponseEntity.ok(license);
	}
	
	@PostMapping
	public ResponseEntity<String> updateLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request) {
		return ResponseEntity.ok(  liscenceService.updateLicense(request, organizationId));
		
	}
	
	@PutMapping
	public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId,
			@RequestBody License request, @RequestHeader(value = "Accept-Language", required=false) Locale locale) {
		
		return ResponseEntity.ok( liscenceService.createLicense(request, organizationId,locale));
		
	}
	
	@DeleteMapping(value = "/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
		return ResponseEntity.ok(liscenceService.deleteLicense(licenseId, organizationId));
	}

}
