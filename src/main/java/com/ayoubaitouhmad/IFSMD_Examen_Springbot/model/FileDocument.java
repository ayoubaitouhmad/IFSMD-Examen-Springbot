package com.ayoubaitouhmad.IFSMD_Examen_Springbot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_document", schema = "ifsmd_examen_springbot")
public class FileDocument {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "file_name")
    private String fileName;

    @Getter
    @Setter
    @Column(name = "file_type")
    private String fileType;


    @Getter
    @Setter
    @Column(name = "path")
    private String path;



    @CreationTimestamp
    @Column(updatable = false)
    @Getter
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Getter
    private LocalDateTime updatedAt;




    public FileDocument(String fileName, String fileType, String path) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.path = path;
    }

    public FileDocument() {

    }
}