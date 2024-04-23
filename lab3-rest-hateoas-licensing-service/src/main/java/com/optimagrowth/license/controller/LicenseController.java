package com.optimagrowth.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;

//@RestController annotation, which combines the @Controller and @ResponseBody annotations.
@RestController
//@RequestMapping maps all HTTP operations by default.
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {

	@Autowired
	private LicenseService licenseService;

	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	//Because the @RestController annotation is present on the class,
	// an implicit @ResponseBody annotation is added to the method.
	// This causes Spring MVC to render the returned HttpEntity and its payload license directly
	// to the response.
	public ResponseEntity<License> getLicense( @PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		//that create a new License
		License license = licenseService.getLicense(licenseId, organizationId);
		//add links to the representation model
		license.add(
				// linkTo(…) and methodOn(…) are static methods on ControllerLinkBuilder
				// that let you fake a method invocation on the controller.
				// The returned LinkBuilder will have inspected the controller method’s mapping annotation
				// to build up exactly the URI to which the method is mapped.
				//The call to withSelfRel() creates a Link instance that you add to the Greeting representation model.
				//The "self" relation indicates that the link represents the resource itself.
				// It's commonly used to provide clients with the URL to access the resource
				linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
				// Creates a placeholder call to the createLicense() method in the LicenseController, specifying the required parameters
				//.withRel("createLicense"): Assigns a relationship name to the generated link, in this case "createLicense".
				// This name is used to identify the link and provide it in the JSON output document.
				// null is placeholder for Request-Body
				linkTo(methodOn(LicenseController.class).createLicense(organizationId, license, null)).withRel("createLicense"),
				// Creates a placeholder call to the updateLicense() method in the LicenseController, specifying the required parameters
				linkTo(methodOn(LicenseController.class).updateLicense(organizationId, license)).withRel("updateLicense"),
				// Creates a placeholder call to the deleteLicense() method in the LicenseController, specifying the required parameters
				linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, license.getLicenseId())).withRel("deleteLicense")
		);
		//ResponseEntity: This class represents an HTTP response entity,
		// which includes the HTTP status code, headers, and body.
		//ok(license): This method is a static factory method of ResponseEntity that creates a new instance
		// with a status code of 200 (OK) and sets the given license object as the response body.
		return ResponseEntity.ok(license);
	}

	//produces = { "application/hal+json" } gibt an,
	// dass die Methode nur dann aufgerufen werden soll,
	// wenn der Client eine Antwort im JSON-Format vom Typ application/hal+json erwartet
	@GetMapping(value="/licenses", produces = { "application/hal+json" })
	public ResponseEntity<CollectionModel<License>> getLicenses(@PathVariable String organizationId) {
		List<License> licenses = licenseService.getLicenses();
		for (License license : licenses) {
			license.add(
					linkTo(methodOn(LicenseController.class).getLicense(license.getOrganizationId(), license.getLicenseId())).withSelfRel(),
					linkTo(methodOn(LicenseController.class).createLicense(license.getOrganizationId(), license, null)).withRel("createLicense"),
					linkTo(methodOn(LicenseController.class).updateLicense(license.getOrganizationId(), license)).withRel("updateLicense"),
					linkTo(methodOn(LicenseController.class).deleteLicense(license.getOrganizationId(), license.getLicenseId())).withRel("deleteLicense")
			);
		}
		CollectionModel<License> result = CollectionModel.of(licenses);
		return ResponseEntity.ok(result);
	}

	@PutMapping
	public ResponseEntity<String> updateLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request) {
		return ResponseEntity.ok(licenseService.updateLicense(request, organizationId));
	}

	@PostMapping
	public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request,
			//den Wert eines bestimmten HTTP-Request-Headers abzurufen
												// Locale-Objekt um, das die bevorzugte Sprache des Clients darstellt
			@RequestHeader(value = "Accept-Language",required = false) Locale locale) {

		return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
	}

	@DeleteMapping(value="/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));
	}
}
