package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String getFiles(Authentication authentication, Model model){
        model.addAttribute("files",fileService.loadFiles());
        return "redirect:/";
    }

    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName, Authentication authentication){
        Files file = fileService.loadFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFileName() + "\"").body(new
                ByteArrayResource(file.getFileData()));
    }

    @DeleteMapping("/file/{fileId:.+}")
    public String deleteFile(@PathVariable long fileId, Authentication authentication){
        fileService.deleteFile(fileId);
        return "redirect:/";
    }

    @PostMapping("file/upload")
    public String uploadFile(@RequestParam("fileName")MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message","Please upload a file!");
            return "redirect:/";
        }

        fileService.uploadNewFile(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getName() + '!');

        return "redirect:/";
    }

}
