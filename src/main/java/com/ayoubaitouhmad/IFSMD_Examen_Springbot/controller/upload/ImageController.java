package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.upload;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.FileDocument;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.FileDocumentService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;

import java.io.IOException;


import java.util.List;

@Controller
public class ImageController extends BaseController {

    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";
    private final FileDocumentService fileDocumentService;
    private final ImageService imageService;

    @Autowired
    public ImageController(FileDocumentService fileDocumentService, ImageService imageService) {
        this.fileDocumentService = fileDocumentService;
        this.imageService = imageService;
    }


    @GetMapping("/images")
    public String listUploadedFiles(Model model) {
        List<FileDocument> files = imageService.getAllImages();
        model.addAttribute("images", files);
        return "images";
    }

    @GetMapping("/stream/{filename:.+}")
    public ResponseEntity<Resource> stream(Model model, @PathVariable String filename) throws IOException {
        return imageService.streamImage(filename);
    }


    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/upload";
        }

        try {
            imageService.createImage(file);
            attributes.addFlashAttribute("message", "Successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            logger.info("getMessage:" + e.getMessage());
            attributes.addFlashAttribute("message", "Failed to upload. Error: " + e.getMessage());
        }

        return "redirect:/images";  // Redirect to avoid double posting
    }


}
