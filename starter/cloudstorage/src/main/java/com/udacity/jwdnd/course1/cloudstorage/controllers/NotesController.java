package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class NotesController {

private NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @RequestMapping("/notes")
        public String getNotes( Authentication authentication, @ModelAttribute("notes") Notes notes, Model model){
        String username = authentication.getName();

       if( notes.getNoteid()==null){
           System.out.println("Got into the add route");
           notesService.addNote(notes.getNotetitle(), notes.getNotedescription(), username);
       }
       else{
           System.out.println("Got into the edit route");

           notesService.editNote(notes.getNoteid(), notes.getNotetitle(), notes.getNotedescription());
        }
        return "redirect:/home";
        }

    @RequestMapping ("/deleteNote/{noteId}")
    public RedirectView deleteNote(@PathVariable("noteId") Integer noteId, Model model){
        notesService.deleteNote(noteId);
        return new RedirectView("/home");
    }

    }


