package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;
    static final String KEY = "myKeyString@$123";
    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredential(String url, String username, String key, String password, String user){
        int userId = userMapper.getUserID(user);
        Credentials newCredential = new Credentials(null, url, username, key, password, userId, null);
        credentialsMapper.insertCredential(newCredential);
    }

    public List<Credentials> getCredentials(String username){
        int userId = userMapper.getUserID(username);

        List<Credentials> credentialsList = credentialsMapper.getCredential(userId);

        for (int i=0; i<credentialsList.size(); i++){
            credentialsList.get(i).setPlainPassword( encryptionService.decryptValue(credentialsList.get(i).getPassword(), KEY));
        }

         return credentialsList;
    }



    public void updateCredentials(Integer credentialId, String url, String username, String key, String password){
        credentialsMapper.updateCredentials(credentialId, url, username, key, password);
    }

    public void deleteCredential(Integer credentialid){
        credentialsMapper.deleteCredential(credentialid);
    }

}
