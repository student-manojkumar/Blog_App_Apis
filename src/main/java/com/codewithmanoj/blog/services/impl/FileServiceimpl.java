package com.codewithmanoj.blog.services.impl;

import com.codewithmanoj.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import java.nio.file.StandardCopyOption;


@Service
public class FileServiceimpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // file name
        String name = file.getOriginalFilename();
        //abc.jpg

        //random name generated file name
        String randomId = UUID.randomUUID().toString();
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));

        //full path
        //String filePath= File.pathSeparator+fileName1;
        String filePath = path + File.separator + fileName1;

        //create folder if not crate
        File f = new File(path);
        //String fileName = f.getName();
        if(!f.exists()){
            f.mkdirs();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath=path + File.separator + fileName;
        InputStream is =new FileInputStream(fullPath);

        return is;
    }
}
