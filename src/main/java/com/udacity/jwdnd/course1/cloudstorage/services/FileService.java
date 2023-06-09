package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;

    }

    public void storeFile(MultipartFile fileUpload, int userid) throws IOException {
        File file;
        file = new File();
        file.setContenttype(fileUpload.getContentType());
        file.setFilename(fileUpload.getOriginalFilename());
        file.setFiledata(fileUpload.getBytes());
        file.setFilesize(fileUpload.getSize());
        file.setUserid(userid);
        fileMapper.insertFile(file);

    }

    public boolean isSameFileName(String filename, int userid ){
        File existingFile = fileMapper.getFileByUserIdAndFileName(userid, filename);
        if (existingFile != null){
            return true;
        }
        return false;

    }

    public List<File> getFileByUser(int userid){
        return fileMapper.getFileByUserId(userid);
    }

    public File  getFileById(int fileId){
        return fileMapper.getFileById(fileId);
    }


    public void deleteFile(int fileid){
        this.fileMapper.deleteFile(fileid);
    }





}
