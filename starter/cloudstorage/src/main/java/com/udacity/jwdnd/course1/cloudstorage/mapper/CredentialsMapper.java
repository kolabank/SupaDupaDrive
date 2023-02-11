package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    public List<Credentials> getCredential(int userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
        @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public int insertCredential (Credentials credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
    public int updateCredentials(Integer credentialid, String url, String username, String key, String password);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public int deleteCredential (int credentialid);
}
