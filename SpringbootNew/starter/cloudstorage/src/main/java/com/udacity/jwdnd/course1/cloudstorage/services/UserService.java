package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;
public int SaveUser(User user) {
    String encodedSalt = generateEncodedSalt();
    String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
    User userToSave = new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName());
    return userMapper.insert(userToSave);
}

    private String generateEncodedSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public Optional<User> getUserByUserName(String userName) {
        return userMapper.getUserByUsername(userName);
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUserByUsername(username).isEmpty();
    }
}
