package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FilesMapper filesMapper;
    private UserMapper userMapper;


    public FileService(FilesMapper filesMapper, UserMapper userMapper) {
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
    }

   public void addFile(MultipartFile newFile, String username) throws IOException {
      int userId = userMapper.getUserID(username);
      Files file = new Files(null, newFile.getOriginalFilename(), newFile.getContentType(),Long.toString(newFile.getSize()), userId, newFile.getBytes());
      filesMapper.insertFile(file);
   }
   public List<Files> getFiles(String username){
        int userid = userMapper.getUserID(username);
        return filesMapper.getFiles(userid);
   }

    public List<String> getFilesNames(String username){
        int userid = userMapper.getUserID(username);
        return filesMapper.getFileNames(userid);

    }

   public Files getFileById(Integer fileid){

        return filesMapper.getFileById(fileid);
   }

   public void deleteFile(Integer fileid){
        filesMapper.deleteFile(fileid);
   }

}
