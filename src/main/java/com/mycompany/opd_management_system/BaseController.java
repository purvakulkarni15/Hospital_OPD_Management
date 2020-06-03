/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.opd_management_system;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class BaseController {

    @RequestMapping(method = RequestMethod.POST, value = "/setPatientData", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String setPatientData(String name/*, int age, String gender, String illness, String emailId, String contactNo*/){
        return "Data stored successfully";
    }
}
