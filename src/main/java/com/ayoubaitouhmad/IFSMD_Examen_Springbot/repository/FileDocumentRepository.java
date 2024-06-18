package com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDocumentRepository extends JpaRepository<FileDocument, Long> {

    List<FileDocument> findByFileType(String contentType);

    @Query("SELECT f FROM FileDocument f WHERE f.fileType LIKE :contentTypePattern")
    List<FileDocument> findByContentTypePattern(@Param("contentTypePattern") String contentTypePattern);

}
