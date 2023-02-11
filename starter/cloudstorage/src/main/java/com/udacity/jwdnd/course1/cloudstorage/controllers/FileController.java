package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @RequestMapping("/fileRoute")
    public String addFile(Authentication authentication, @ModelAttribute("files") Files files, Model model){
        return "redirect:/home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {

        String username = authentication.getName();
        List<String> allFileNames = fileService.getFilesNames(username);
        if (allFileNames.contains(file.getOriginalFilename()) ){
            System.out.println("We got into the duplicated file route");
            String errorMessage = "File name is duplicated";
            model.addAttribute("errorMessage", errorMessage);
            return("/error");
        }
        else {
            fileService.addFile(file, username);
            System.out.println("The filename is " + fileService.getFiles(username).get(0).getFilename());
        }

        return "redirect:/home";
    }

    @GetMapping("/deleteFile/{fileid}")
    public String deleteFile(@PathVariable("fileid") Integer fileid, Model model){
        fileService.deleteFile(fileid);
        return "redirect:/home";
    }

    @GetMapping(value = "/viewFile/{fileid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] viewFile(@PathVariable("fileid") Integer fileid, Model model){
        System.out.println("I got ino the view controller");
        return fileService.getFileById(fileid).getFiledata();

    }

}
