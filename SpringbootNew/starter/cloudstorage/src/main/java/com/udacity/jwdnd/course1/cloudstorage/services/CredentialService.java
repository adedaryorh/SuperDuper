package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.ConstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;

    private final EncryptionService encryptionService;

    public void deleteCredentialById(Integer credentialId) {
        credentialMapper.deleteCredentialBuId(credentialId);
    }

   public int createCredential(Credential credential, String currentUser) {
       Optional<User> optionalUser = userService.getUserByUserName(currentUser);
       optionalUser.ifPresent(user -> credential.setUserId(user.getUserId()));
       credential.setKey(ConstantUtil.KEY);
       String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
       credential.setPassword(encryptedPassword);
       return credentialMapper.insertCredential(credential);
   }

    public List<Credential> getCredentialAssociatedWithCurrentUser(Integer userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }
}
