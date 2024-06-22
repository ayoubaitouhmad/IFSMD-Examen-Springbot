package com.ayoubaitouhmad.IFSMD_Examen_Springbot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "users" , schema = "ifsmd_examen_springbot")
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    @NotBlank(message = "Name is mandatory")
    @Setter
    @Column(name = "name")
    private String name;

    @Email(message = "Email should be valid")
    @Setter
    private String email;

    @Setter
    private String password;

    @Size(min = 3, max = 20, message = "name must be between 3  and 20 characters")
    @NotBlank(message = "Username is mandatory")
    @Setter
    @Column(name = "username")
    private String username;


    @Setter
    private String role;

    @Setter
    @Getter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Eager fetching
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    private FileDocument profileImage;


    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("createdAt asc")
    private List<Article> articles;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;





    public String getAccountDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return createdAt.format(formatter);
    }





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
