package com.ayoubaitouhmad.IFSMD_Examen_Springbot.service;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.FileDocument;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.FileDocumentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService extends FileDocumentService {

    private static final String IMAGE_UPLOAD_DIRECTORY = "images";





    public ImageService(FileDocumentRepository fileDocumentRepository) {
        super(fileDocumentRepository);
        ImageService.UPLOAD_DIRECTORY += File.separator + IMAGE_UPLOAD_DIRECTORY;
    }



    public List<FileDocument> getAllImages() {
        return fileDocumentRepository.findByContentTypePattern("image/%");
    }



    public FileDocument createImage(MultipartFile file) throws IOException {
        String filename = storeFile(file);
        FileDocument fileDocument = new FileDocument(file.getOriginalFilename(), file.getContentType(), filename);
       return this.createFileDocument(fileDocument);
    }


    public ResponseEntity<Resource> streamImage(String imageName) throws IOException {
        try {
            Path imagePath = Paths.get(ImageService.UPLOAD_DIRECTORY).resolve(imageName).normalize();
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(imagePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }






    public void deleteImage(){


    }
}
