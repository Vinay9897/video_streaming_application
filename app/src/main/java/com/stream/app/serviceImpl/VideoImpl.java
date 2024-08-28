package com.stream.app.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.stream.app.entities.Video;
import com.stream.app.service.VideoInterface;

import jakarta.annotation.PostConstruct;

@Service
public class VideoImpl implements VideoInterface {

	@Value("${files.video}")
	String DIR;
	
	@PostConstruct
	public void init() {
		File file = new  File(DIR);
		
		if(!file.exists())
		{
			file.mkdir();
			System.out.println("Folder created");
		}
	}
	
	@Override
	public Video save(Video video, MultipartFile file) {
		
		try {
		String fileName = file.getOriginalFilename();
		String contentType = file.getContentType();
		InputStream inputStream = file.getInputStream();
			String cleanFileName = StringUtils.cleanPath(fileName);
			String cleanFolder = StringUtils.cleanPath(DIR);
			
			Path path = Paths.get(cleanFolder, cleanFileName);
			
			System.out.println(contentType);
			System.out.println(path);
			
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return video;
	}

	@Override
	public Video get(String videoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video getByVideoTitle(String videoTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
