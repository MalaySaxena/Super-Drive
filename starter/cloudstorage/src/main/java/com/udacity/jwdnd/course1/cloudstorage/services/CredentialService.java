package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;
    private final AuthenticatedUserService authenticatedUser;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService, AuthenticatedUserService authenticatedUser) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
        this.authenticatedUser = authenticatedUser;
    }

    public int addCredential(CredentialForm credentialForm){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        return credentialsMapper.addCredential(new Credentials(credentialForm.getUrl(),
                encodedKey,
                credentialForm.getUserName(),
                credentialForm.getPassword(),
                authenticatedUser.getLoggedInUserId()));
    }

    public List<Credentials> getCredentialsEncoded(){
        return credentialsMapper.getCredentials(authenticatedUser.getLoggedInUserId());
    }

    public List<Credentials> getCredentialsDecoded(){
        List<Credentials> credentials = credentialsMapper.getCredentials(authenticatedUser.getLoggedInUserId());

        for(Credentials credential : credentials){
            String encryptedPassword = credential.getPassword();
            String encodedKey = credential.getKey();
            credential.setPassword(encryptionService.decryptValue(encryptedPassword, encodedKey));
        }
        return credentials;
    }

    public void updateCredential(CredentialForm credentialForm){
        Credentials credential = credentialsMapper.getCredential(credentialForm.getCredentialId());
        credential.setUserName(credentialForm.getUserName());
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(),credential.getKey());
        credential.setPassword(encryptedPassword);
        credential.setUrl(credentialForm.getUrl());
        credentialsMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId){
        credentialsMapper.deleteCredential(credentialId);
    }
}
