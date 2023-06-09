package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserMapper userMapper;
    public NoteService(NoteMapper noteMapper, UserMapper userMapper){
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;

    }

    public void storeNote(Note note, int userid) {
        Note newNote = new Note();
        System.out.println(userid);
        newNote.setNotedescription(note.getNotedescription());
        newNote.setNotetitle(note.getNotetitle());
        newNote.setUserid(userid);
        this.noteMapper.insertNote(newNote);

    }

    public List<Note> getNoteByUserId(int userid){
        return this.noteMapper.getNoteByUserId(userid);
    }

    public void updateNote(Note note){
        this.noteMapper.updateNote(note);
    }

    public void deleteNote(int noteid){
        this.noteMapper.deleteNote(noteid);
    }

    public List<Note> getNoteByUser(int userid){
        return this.noteMapper.getNoteByUserId(userid);
    }
}
