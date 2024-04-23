package com.optimagrowth.license.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.optimagrowth.license.model.License;

@Service
public class LicenseService {

	//MessageSource is an interface in Spring Framework
	// that provides methods for resolving messages, with support
	// for internationalization (i18n).
	// It's often used for obtaining localized messages in a Spring application.

	private List<License> licenses = new ArrayList<License>();
	
	@Autowired
	MessageSource messages;



	//private static final String TEMPLATE = "Software Company department %s!";



	public License getLicense(String licenseId, String organizationId){
		License license = new License(organizationId);
		license.setId(new Random().nextInt(1000));
		license.setLicenseId(licenseId);
		//license.setOrganizationId(organizationId);
		license.setDescription("Software product");
		license.setProductName("Ostock");
		license.setLicenseType("full");

		return license;
	}

	/*
	{
  "id": 1243,
  "licenseId": "LICENSE-ABC",
  "description": "This is a license description.",
  "organizationId": "2",
  "productName": "Product XYZ",
  "licenseType": "Standard"
}
	 */

	public String createLicense(License license, String organizationId, Locale locale){
		String responseMessage = null;
		if(!StringUtils.isEmpty(license)) {
			license.setOrganizationId(organizationId);
			licenses.add(license);
			//null an, dass keine dynamischen Parameter interpoliert werden müssen
			responseMessage = String.format(messages.getMessage("license.create.message",null,locale), license);
		}

		return responseMessage;
	}

	public String updateLicense(License license, String organizationId){
		String responseMessage = null;
		if(!StringUtils.isEmpty(license)) {
			license.setOrganizationId(organizationId);
			for (License l : licenses){
				if (l.getOrganizationId().equals(organizationId))
				l = license;
			}
			responseMessage = String.format(messages.getMessage("license.update.message", null, null), license);
		}

		return responseMessage;
	}

	public String deleteLicense(String licenseId, String organizationId){
		String responseMessage;
		for (License l : licenses){
			if (l.getOrganizationId().equals(organizationId))
				licenses.remove(l);
		}
		responseMessage = String.format(messages.getMessage("license.delete.message", null, null),licenseId, organizationId);
		return responseMessage;

	}

	public List<License> getLicenses() {
		return new ArrayList<>(licenses);
	}
}
