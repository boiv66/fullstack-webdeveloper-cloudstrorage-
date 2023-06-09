package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;

    private final HashService hashService;
    private final EncryptionService encryptionService;
    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, HashService hashService, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.hashService = hashService;
    this.encryptionService = encryptionService;
    }

    public void storeCredential(Credential credential, int userid) throws IOException {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        Credential newCredential = new Credential();
        newCredential.setUsername(credential.getUsername());
        newCredential.setKey(encodedSalt);
        newCredential.setPassword(hashedPassword);
        newCredential.setUserid(userid);
        newCredential.setUrl(credential.getUrl());
        this.credentialMapper.insertCredential(newCredential);
        System.out.println(getCredentialByUserId(userid) + "after inserting to database");
        System.out.println("debug mapper credential");
    }



    public List<Credential> getCredentialByUserId(int userid){
        return this.credentialMapper.getCredentialByUserId(userid);
    }

    public void updateCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);



        credential.setPassword(hashedPassword);

        credential.setKey(encodedSalt);


        this.credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialid){
        this.credentialMapper.deleteCredential(credentialid);
    }



}

