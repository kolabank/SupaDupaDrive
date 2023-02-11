package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE userid =#{userid}")
    public List<Notes> getNotes(int userid);

    //To find noteIds already in the database
    @Select("SELECT noteid FROM NOTES")
    public List<Integer> getNoteIds();

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}," +
            " #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    public int insertNote (Notes note);


    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    public int deleteNote(int noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE  noteid = #{noteid}")
    public int updateNote(Integer noteid, String notetitle, String notedescription);
}
