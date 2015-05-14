/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.firstmodule.web.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.validator.PatientIdentifierValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The main controller.
 */
@Controller
public class  FirstModuleManageController {

    PatientService patientService=Context.getPatientService();

	SessionFactory sessionFactory;

	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/firstmodule/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());



	}


	@RequestMapping(value = "/module/firstmodule/saveForm", method = RequestMethod.GET)
	public String saveForm(ModelMap model,
                           @RequestParam(value = "family_name", required = false) String family_name,
                           @RequestParam(value = "middle_name", required = false) String middle_name,
                           @RequestParam(value = "given_name", required = false) String given_name,
                           @RequestParam(value = "dob", required = false) Date dob,
                           @RequestParam(value = "id_number", required = false) String id_number,
                           @RequestParam(value = "gender", required = false) String gender,
                           @RequestParam(value = "address", required = false) Integer address,
                           @RequestParam(value = "postal_code", required = false) Integer postal_code,
                           @RequestParam(value = "town", required = false) String town,
                           @RequestParam(value = "country", required = false) String country) {

		//Create a person instance
		Person person=new Person();


		PersonName personName=new PersonName();
		personName.setFamilyName(family_name);
		personName.setMiddleName(middle_name);
		personName.setGivenName(given_name);

		//Populate person with details

		person.addName(personName);
		PersonAddress personAddress=new PersonAddress();
		personAddress.setAddress1(address.toString());
		personAddress.setCityVillage(town);
		personAddress.setCountry(country);
		personAddress.setPostalCode(postal_code.toString());
		person.addAddress(personAddress);
		person.setGender(gender);

        DateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");

        Date birthday= null;
        try {
            birthday = dateFormat.parse(String.valueOf(dob));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        person.setBirthdate(birthday);


        //Create new patient using person details
		Patient patient=new Patient(person);


        PatientIdentifier openmrsId = new PatientIdentifier();
        String TARGET_ID_KEY = "patientmodule.idType";
        String TARGET_ID = Context.getAdministrationService().getGlobalProperty(TARGET_ID_KEY);
        PatientIdentifierType openmrsIdType = patientService.getPatientIdentifierTypeByName(TARGET_ID);
        openmrsId.setIdentifier(id_number);
        openmrsId.setDateCreated(new Date());
        openmrsId.setLocation(Context.getLocationService().getDefaultLocation());
        openmrsId.setIdentifierType(openmrsIdType);
        PatientIdentifierValidator.validateIdentifier(openmrsId);
        patient.addIdentifier(openmrsId);

        //save new patient

        if (!patientService.isIdentifierInUseByAnotherPatient(openmrsId)) {
            patientService.savePatient(patient);
            model.addAttribute("save_success","Patient successfully saved");
            return "redirect:manage";
        }else {
            model.addAttribute("save_failed","Sorry, request to save new Patient Failed : That ID already exists!");
            return "redirect:manage";
        }





	}

    /* List all saved patients */

    @RequestMapping(value = "/module/firstmodule/listPatients", method = RequestMethod.GET)
    public String listPatients(ModelMap model) {
        //model.addAttribute("user", Context.getAuthenticatedUser());
        List<Patient>patientList=patientService.getAllPatients();
        model.addAttribute("listPatient",patientList);

        return ("saved");
    }

}