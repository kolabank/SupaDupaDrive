package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CredentialsController {

    CredentialsService credentialsService;
    EncryptionService encryptionService;

    static final String KEY = "myKeyString@$123";

    public CredentialsController(CredentialsService credentialsService, EncryptionService encryptionService) {
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @RequestMapping("/credentials")
    public String addOrEditController(Authentication authentication, @ModelAttribute("credentials") Credentials credentials, Model model) {

        String username = authentication.getName();
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), KEY);
        if (credentials.getCredentialid() == null) {
            System.out.println("Got into the add route");
            credentialsService.addCredential(credentials.getUrl(), credentials.getUsername(), KEY, encryptedPassword, username);
        }
        else {
            credentialsService.updateCredentials(credentials.getCredentialid(), credentials.getUrl(), credentials.getUsername(), KEY, encryptedPassword);
        }
        return "redirect:/home";
    }

    @RequestMapping("/deleteCredential/{credentialId}")
    public String deleteController(@PathVariable("credentialId") Integer credentialId, Model model){
        credentialsService.deleteCredential(credentialId);
        return "redirect:/home";
    }



}
