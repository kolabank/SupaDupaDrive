package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {


    static final String KEY = "myKeyString@$123";
    private NotesService notesService;
    private CredentialsService credentialsService;
    private FileService fileService;
    private EncryptionService encryptionService;

    public HomeController(NotesService notesService, CredentialsService credentialsService, FileService fileService, EncryptionService encryptionService) {
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String showNotes(Authentication authentication, Notes note, Credentials credentials, Files files,
            Model model){
        String username = authentication.getName();
        model.addAttribute("theNotes", notesService.getNotes(username));
        model.addAttribute("theCredentials", credentialsService.getCredentials(username));
        model.addAttribute("theFiles", fileService.getFiles(username));

        return "home";
    }

}
