package com.social.media.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Non owning side should make use of this mappedBy method
    @OneToOne
    @JoinColumn(name = "social_user")
    @JsonIgnore // To avoid circular references in bidirectional mapping
    private SocialUser socialUser;

    private String description;

    // Manually maintaining consistencies on both the sides.
    // Lombok generated methods are simple they generates a value to the field and do nothing else they do not
    // automatically handle the setting of the relationships on both sides of bidirectional relationships which is why
    // it is necessary to maintaining relationships and cascading operation to work as expected and this custom setter
    // ensures the relationship is established on both the sides and which is why it is needed in addition when lombok
    // generates. In simple lombok does not manage bidirectional relationships so custom setter required here.
    public void setSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
        if (socialUser != null && socialUser.getSocialProfile() != this) {
            socialUser.setSocialProfile(this, false); // prevent infinite loop
        }
    }
}
