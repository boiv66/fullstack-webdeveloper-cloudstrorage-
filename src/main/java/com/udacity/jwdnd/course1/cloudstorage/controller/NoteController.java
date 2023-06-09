package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/home/note")
public class NoteController {
    private final NoteService noteService;
    private final UserMapper userMapper;

    public NoteController(NoteService noteService, UserMapper userMapper){
        this.noteService = noteService;
        this.userMapper = userMapper;



    }

    @PostMapping
    public String addNoteHandler( Note note) throws IOException {
        System.out.println("pass through this addnotehandler");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userMapper.getUser(username);
        int userid = user.getUserId();

        if(note.getNoteid() != null){
            this.noteService.updateNote(note);
        }else{
            this.noteService.storeNote(note, userid);
        }


       return "redirect:/result?success";
   }
//    @PostMapping
//    public String handleAddUpdateNote(Authentication authentication, Note note){
//        String loggedInUserName = (String) authentication.getPrincipal();
//        User user = userMapper.getUser(loggedInUserName);
//        Integer userId = user.getUserId();
//
//        if (note.getNoteId() != null) {
//            noteService.updateNote(note);
//        } else {
//            noteService.storeNote(note, userId);
//        }
//
//        return "redirect:/result?success";
//    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") int noteid){
        if(noteid > 0){
            this.noteService.deleteNote(noteid);
        }
        return"home";
    }
}