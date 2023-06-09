package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/home/file")
public class FileUploadController {
    private final FileService fileService;
    private final UserMapper userMapper;

    public FileUploadController(FileService fileService, UserMapper userMapper){
        this.fileService = fileService;
        this.userMapper = userMapper;



    }

    @PostMapping()
    public String fileUploadHandler(@RequestParam("fileUpload")MultipartFile fileUpload) throws IOException {
        String uploaderror = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userMapper.getUser(username);
        int userid = user.getUserId();


        // checkFileUpload
        if(fileUpload.isEmpty()){
            uploaderror = "The file is empty. Please try again";
        }
        if(this.fileService.isSameFileName(fileUpload.getOriginalFilename(), userid)){
            uploaderror = "This file already existed";
        }
        if(uploaderror != null){
//            model.addAttribute("uploadError", uploaderror);
            return "redirect:/result?error";
        }
        this.fileService.storeFile(fileUpload, userid);
//        model.addAttribute("successChange", true);
        return "redirect:/result?success";

    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") int fileid, Authentication authentication){

        String username = authentication.getName();
        User user = this.userMapper.getUser(username);
        if(fileid > 0){
            this.fileService.deleteFile(fileid);
        }
        return "home";
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId)  throws IOException{

        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFilename()+"\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }




}
