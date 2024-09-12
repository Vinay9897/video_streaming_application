package com.stream.app.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stream.app.entities.Video;
import com.stream.app.payload.CustomMessage;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.service.VideoInterface;


@RequestMapping(value = "/app")
@RestController
@CrossOrigin("*")
public class VideoController {
	
	@Autowired
	VideoInterface videoInterface;
	
	@Autowired
	VideoRepository videoRepository;
	
	@PostMapping("/videos")
	public ResponseEntity<CustomMessage> create(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("description") String description) {
		Video video = new Video();
		video.setVideoTitle(title);
		video.setVideoDesc(description);
		video.setVideoId(UUID.randomUUID().toString());
		video.setContentType(file.getContentType());
		video.setFilePath(file.getOriginalFilename());
		Video response = videoInterface.save(video, file);
		Video savedVideo = videoRepository.save(response);
		System.out.println("hi video transfered");
		if(savedVideo != null)
		{
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(CustomMessage.builder()
							.message("video has uploaded")
							.success(true)
							.build());
		}
		else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(CustomMessage.builder()
							.message("video has not uploaded")
							.success(false)
							.build());
					
		}
	}
	
	
	@GetMapping("/stream/{videoId}")
	public ResponseEntity<Resource> stream(@PathVariable String videoId){
		Video video = videoInterface.get(videoId);
		
		
		String contentType =  video.getContentType();
		String filePath = video.getFilePath();
		Path path = Paths.get("video//" +filePath); 
		Resource resource =  new FileSystemResource(path);
		
		if(contentType == null)
		{
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.body(resource);
	}

	@GetMapping("/allVideos")
	public List<Video> getAll(){
		return videoInterface.getAll();
	}
	
	@DeleteMapping("/deleteAll")
	public String deleteAll() {
		return videoInterface.deleteAll();
	}
}
