package com.example.chatop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

	private final Path uploadDir = Paths.get("uploads");

	public FileStorageService() {
		try {
			if (Files.notExists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not create uploads directory", e);
		}
	}

	public String saveFile(MultipartFile file) {
		try {
			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			Path destination = uploadDir.resolve(filename);
			Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
			return filename;
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file", e);
		}
	}

	public Path loadFile(String filename) {
		return uploadDir.resolve(filename);
	}
}
