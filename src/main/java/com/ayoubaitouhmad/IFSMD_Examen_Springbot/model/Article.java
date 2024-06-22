package com.ayoubaitouhmad.IFSMD_Examen_Springbot.model;

import groovy.lang.GString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "articles", schema = "ifsmd_examen_springbot")
public class Article {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "description is mandatory")
    @Setter
    @Getter
    private String description;


    @NotBlank(message = "content is mandatory")
    @Setter
    @Getter
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;




    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.EAGER) // ManyToOne relationship
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user;


    @Getter
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @Getter
    @Setter
    @UpdateTimestamp
    private LocalDateTime updatedAt;



    public String getArticleUpdatedAt() {

        if (this.getUpdatedAt() != null) {
            return this.getUpdatedAt().toString();
        } else {
            return "Update date not available";
        }
    }

    public String getArticleCreatedAt() {
        if (this.getUpdatedAt() != null) {
            return this.getCreatedAt().toString();
        } else {
            return "Update date not available";
        }
    }

    public Article() {

    }

    public Article(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    /***
     * substring the content of the article
     * @return
     */
    public String getShortContent() {
        return this.getContent().substring(0, 150) + " ... ";
    }


}
