package com.sigua.practices.services;

import com.sigua.practices.model.Role;
import com.sigua.practices.model.User;
import com.sigua.practices.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSrevice {
    private final UserRepository userRepository;
    public boolean createUsers(User user){
        if (userRepository.findByEmail(user.getEmail())!= null)
            return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }
}
