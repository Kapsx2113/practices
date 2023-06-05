package com.sigua.practices.services;

import com.sigua.practices.model.Role;
import com.sigua.practices.model.User;
import com.sigua.practices.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSrevice {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public boolean createUsers(User user){
        if (userRepository.findByEmail(user.getEmail())!= null)
            return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
