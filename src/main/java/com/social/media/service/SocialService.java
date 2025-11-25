package com.social.media.service;

import com.social.media.models.SocialUser;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {
    @Autowired
    private SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {
        return socialUserRepository.findAll();
    }

    public SocialUser saveUser(SocialUser socialUser) {
        return socialUserRepository.save(socialUser);
    }

    public void deleteUser(Long id) {
        SocialUser socialUser = socialUserRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found")
        );
        socialUserRepository.delete(socialUser);
    }
}
