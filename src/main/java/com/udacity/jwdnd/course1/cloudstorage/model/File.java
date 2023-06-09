package com.udacity.jwdnd.course1.cloudstorage.model;

import java.io.StringReader;

public class File {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private Long filesize;

    private Integer userid;

    private byte[] filedata;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(int fileId){
        this.fileId = fileId;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }



    public String getFilename() {
        return this.filename;
    }

    public void setContenttype(String contenttype){
        this.contenttype = contenttype;
    }

    public String getContenttype(){
        return this.contenttype;
    }

    public void setFilesize(long filesize){
        this.filesize = filesize;
    }

    public long getFilesize(){
        return this.filesize;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(int userid){
        this.userid = userid;
    }

    public void setFiledata(byte[] filedata){
        this.filedata = filedata;
    }

    public byte[] getFiledata(){
        return this.filedata;
    }




}
