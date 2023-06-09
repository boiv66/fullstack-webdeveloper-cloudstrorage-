package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFileByUserId(int userid);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileById(int fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename=#{filename}")
    File getFileByUserIdAndFileName(int userid, String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Update("UPDATE FILES set filename=#{filename}, contenttype=#{contenttype}, filesize=#{filesize}, filedata=#{filedata}) where fileId=#{fileId}")
    int updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteFile(Integer fileId);
}

