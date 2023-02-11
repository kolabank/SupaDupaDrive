package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;
    private UserMapper userMapper;

    public NotesService(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void postConstruct(){System.out.println("Into the notes service");}


    public void addNote(String title, String description, String username){
       int userId = userMapper.getUserID(username);
       Notes newNote = new Notes(null, title, description,userId);
       notesMapper.insertNote(newNote);
    }

    public void editNote(Integer noteId, String title, String description){

        notesMapper.updateNote(noteId, title, description);
    }



    public List<Notes> getNotes(String username){
        int userId = userMapper.getUserID(username);
        return notesMapper.getNotes(userId);
    }

    public Notes getNotebyId(int id){
        return notesMapper.getNotes(id).get(0);
    }

    public void deleteNote(int noteid){
        notesMapper.deleteNote(noteid);
    }


    public List<Integer> getNoteIds(){
       return notesMapper.getNoteIds();
    }
}
