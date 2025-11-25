package com.social.media.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Non owning side should make use of this mappedBy method
    @OneToOne(mappedBy = "socialUser", cascade = CascadeType.ALL)
//    @JoinColumn(name = "social_profile_id")
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser")
    private List<Post>posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<SocialGroup> socialGroups = new HashSet<>();

    // Add flag to avoid recursion
    public void setSocialProfile(SocialProfile socialProfile) {
        setSocialProfile(socialProfile, true);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Custom setter method required here for maintaining the relationship on both side of bidirectional relationship
    // Explicitly setting social profile in social user - when social profile is assigned to user social profile also
    // should aware of the user.
    public void setSocialProfile(SocialProfile socialProfile, boolean updateOtherSide) {
        this.socialProfile = socialProfile;

        if (updateOtherSide && socialProfile != null && socialProfile.getSocialUser() != this) {
            socialProfile.setSocialUser(this);  // updates only once
        }
    }
}
