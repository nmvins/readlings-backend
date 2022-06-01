package com.example.server.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Noemi on 01.05.2022
 */
public interface StorageService {

    String  store(MultipartFile file);
    public Resource loadFile(String filename);
    public void deleteAll();
    public void init();
}
