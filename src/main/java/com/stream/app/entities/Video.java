package com.stream.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="video_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
	
	@Id
	private String videoId;
	
	private String videoTitle;
	
	private String videoDesc;
	
	private String contentType;
	
	private String filePath;
	
	@ManyToOne
	Course course;
}
