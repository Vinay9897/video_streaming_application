package com.stream.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stream.app.entities.Video;

@Service
public interface VideoInterface {
	
	Video save(Video video);
	Video get(String videoId);
	Video getByVideoTitle(String videoTitle);
	List<Video> getAll();
}
