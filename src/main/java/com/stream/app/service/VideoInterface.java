package com.stream.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stream.app.entities.Video;

@Service
public interface VideoInterface {
	
	Video save(Video video, MultipartFile file);
	Video get(String videoId);
	Video getByVideoTitle(String videoTitle);
	List<Video> getAll();
}
