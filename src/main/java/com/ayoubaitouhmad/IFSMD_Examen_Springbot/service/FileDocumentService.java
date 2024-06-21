package com.ayoubaitouhmad.IFSMD_Examen_Springbot.service;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.FileDocument;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.FileDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileDocumentService  {

    protected static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";


    protected final FileDocumentRepository fileDocumentRepository;



    @Autowired
    public FileDocumentService(FileDocumentRepository fileDocumentRepository) {
        this.fileDocumentRepository = fileDocumentRepository;
    }

    public List<FileDocument> getAllFiles() {
        return fileDocumentRepository.findAll();
    }


    public FileDocument createFileDocument(FileDocument fileDocument) {
        return fileDocumentRepository.save(fileDocument);
    }


    protected String storeFile(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        Path destinationFile = Paths.get(UPLOAD_DIRECTORY).resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();
        Files.createDirectories(destinationFile.getParent());
        Files.copy(file.getInputStream(), destinationFile);
        return uniqueFileName;
    }


    protected String getFileExtension(String filename) {
        return filename.lastIndexOf('.') > 0 ? filename.substring(filename.lastIndexOf('.')) : "";
    }


    public boolean deleteFileDocument(Long id) {
        Optional<FileDocument> fileDocumentOptional = fileDocumentRepository.findById(id);
        if (fileDocumentOptional.isPresent()) {
            FileDocument fileDocument = fileDocumentOptional.get();
            Path filePath = Paths.get(UPLOAD_DIRECTORY).resolve(fileDocument.getFileName()).normalize().toAbsolutePath();
            try {
                Files.deleteIfExists(filePath);
                fileDocumentRepository.deleteById(id);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
