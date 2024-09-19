package com.stream.app.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.stream.app.entities.Video;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.service.VideoInterface;

import jakarta.annotation.PostConstruct;

@Service
public class VideoImpl implements VideoInterface {

	@Value("${files.video}")
	String DIR;
	
	@Autowired
	VideoRepository videoRepository;
	
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
		Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("video not found"));
		return video;
	}

	@Override
	public Video getByVideoTitle(String videoTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> getAll() {
		return videoRepository.findAll();
	}

	@Override
	public String deleteAll() {
		try {
            // Delete all video files in the directory
            Files.walkFileTree(Paths.get("video/"), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Check if the file is a video based on its extension (e.g., .mp4, .avi)
                    
                        Files.delete(file);	
                        System.out.println("Deleted video: " + file.getFileName());
                    
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("All video files deleted from directory: " + "/video/");
        } catch (IOException e) {
            e.printStackTrace();
        }
		videoRepository.deleteAll();
		return " all videos deleted";
	}

}
