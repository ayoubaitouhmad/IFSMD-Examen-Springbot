package com.ayoubaitouhmad.IFSMD_Examen_Springbot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "users" , schema = "ifsmd_examen_springbot")
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter
    private String name;

    @Setter
    private String email;

    @Setter
    private String password;

    @Setter
    private String username;


    @Setter
    private String role;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Eager fetching
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    private FileDocument profileImage;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;








    public User() {
    }




    @Override
    public String toString() {
        return "name=" + name + ", email=" + email + ", password=" + password + ", username=" + username + ", id=" + id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }


    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
