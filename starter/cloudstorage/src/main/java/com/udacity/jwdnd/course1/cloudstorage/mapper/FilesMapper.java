package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

@Select("SELECT * FROM FILES WHERE userid = #{userid}")
    public List<Files> getFiles(int userid);

@Select("SELECT filename FROM FILES WHERE userid = #{userid}")
    public List<String> getFileNames(int userid);

    @Select("SELECT*FROM FILES WHERE fileid = #{fileid}")
public Files getFileById(int fileid);




@Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
@Options(useGeneratedKeys = true, keyProperty = "fileid")
    public int insertFile (Files file);

@Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    public int deleteFile (int fileid);

}
