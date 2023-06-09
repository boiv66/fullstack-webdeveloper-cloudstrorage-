package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
    private final UserMapper userMapper;
    private final FileService fileService;
    private final NoteService noteService;

    private final CredentialService credentialService;

    private final EncryptionService encryptionService;

    public HomeController(UserMapper userMapper, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService){
        this.userMapper = userMapper;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }
    @GetMapping("/home")
    public String homeView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userMapper.getUser(username);
        System.out.println( credentialService.getCredentialByUserId(user.getUserId())+"  credentials");
        model.addAttribute("file", fileService.getFileByUser(user.getUserId()));
        model.addAttribute("note", noteService.getNoteByUserId(user.getUserId()));
        model.addAttribute("credential", credentialService.getCredentialByUserId(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";

    }

    @GetMapping("/result")
    public String resultView(){
        return "result";
    }
}
