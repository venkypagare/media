package com.social.media.config;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialProfile;
import com.social.media.models.SocialUser;
import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialGroupRepository;
import com.social.media.repositories.SocialProfileRepository;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private final SocialUserRepository socialUserRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final PostRepository postRepository;

    public DataInitializer(PostRepository postRepository, SocialProfileRepository socialProfileRepository,
                           SocialGroupRepository socialGroupRepository,
                           SocialUserRepository socialUserRepository) {
        this.postRepository = postRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.socialGroupRepository = socialGroupRepository;
        this.socialUserRepository = socialUserRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create Some users
            SocialUser socialUser1 = new SocialUser();
            SocialUser socialUser2 = new SocialUser();
            SocialUser socialUser3 = new SocialUser();

            // Save users to the database
            socialUserRepository.save(socialUser1);
            socialUserRepository.save(socialUser2);
            socialUserRepository.save(socialUser3);

            // Create some groups
            SocialGroup socialGroup1 = new SocialGroup();
            SocialGroup socialGroup2 = new SocialGroup();

            // Add users to groups
            socialGroup1.getSocialUsers().add(socialUser1);
            socialGroup1.getSocialUsers().add(socialUser2);

            socialGroup2.getSocialUsers().add(socialUser2);
            socialGroup2.getSocialUsers().add(socialUser3);

            // Save groups to the database
            socialGroupRepository.save(socialGroup1);
            socialGroupRepository.save(socialGroup2);

            // Associate users with groups (Bidirectional mapping)
            socialUser1.getSocialGroups().add(socialGroup1);
            socialUser2.getSocialGroups().add(socialGroup2);
            socialUser2.getSocialGroups().add(socialGroup1);
            socialUser3.getSocialGroups().add(socialGroup2);

            // Save users back to database to update associations
            socialUserRepository.save(socialUser1);
            socialUserRepository.save(socialUser2);
            socialUserRepository.save(socialUser3);

            // Create some posts
            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            // Associate posts with users
            post1.setSocialUser(socialUser1);
            post2.setSocialUser(socialUser2);
            post3.setSocialUser(socialUser3);

            // Save posts to the database (Assuming you have a PostRepository)
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            // Create some social profiles
            SocialProfile socialProfile1 = new SocialProfile();
            SocialProfile socialProfile2 = new SocialProfile();
            SocialProfile socialProfile3 = new SocialProfile();

            // Associate profiles with users
            socialProfile1.setSocialUser(socialUser1);
            socialProfile2.setSocialUser(socialUser2);
            socialProfile3.setSocialUser(socialUser3);

            // Save social profiles to the database (Assuming you have a SocialProfileRepository)
            socialProfileRepository.save(socialProfile1);
            socialProfileRepository.save(socialProfile2);
            socialProfileRepository.save(socialProfile3);
        };
    }
}
