package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@Controller
@RequestMapping("/home/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserMapper userMapper;

    public CredentialController(CredentialService credentialService, UserMapper userMapper){
        this.credentialService = credentialService;
        this.userMapper = userMapper;

    }

    @PostMapping
    public String addCredentialHandler( Credential credential) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userMapper.getUser(username);
        int userid = user.getUserId();
        if(credential.getCredentialid() != null){
            this.credentialService.updateCredential(credential);
        }else{
            this.credentialService.storeCredential(credential, userid);
        }
        return "redirect:/result?success";
        // checkFileUpload

    }



    @GetMapping("/delete")
    public String deleteCredential (@RequestParam("id") int credentialid, Authentication authentication){


        if(credentialid > 0){
            this.credentialService.deleteCredential(credentialid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";

    }
}
