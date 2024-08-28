package com.stream.app.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stream.app.entities.Video;
import com.stream.app.payload.CustomMessage;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.service.VideoInterface;
@RequestMapping("/app")
@RestController
public class VideoController {
	
	@Autowired
	VideoInterface videoInterface;
	
	@Autowired
	VideoRepository videoRepository;
	
	@RequestMapping("/videos")
	public ResponseEntity<CustomMessage> create(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("description") String description) {
		Video video = new Video();
		video.setVideoTitle(title);
		video.setVideoDesc(description);
		video.setVideoId(UUID.randomUUID().toString());
		video.setContentType(file.getContentType());
		video.setFilePath(file.getOriginalFilename());
		Video savedVideo = videoRepository.save(video);
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

}
