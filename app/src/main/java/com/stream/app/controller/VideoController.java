package com.stream.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stream.app.entities.Video;
import com.stream.app.payload.CustomMessage;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.service.VideoInterface;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(value = "/app")
@RestController
@CrossOrigin("*")
public class VideoController {

	@Autowired
	VideoInterface videoInterface;

	@Autowired
	VideoRepository videoRepository;

	@PostMapping("/videos")
	public ResponseEntity<CustomMessage> create(@RequestParam("file") MultipartFile file,
			@RequestParam("title") String title, @RequestParam("description") String description) {
		Video video = new Video();
		video.setVideoTitle(title);
		video.setVideoDesc(description);
		video.setVideoId(UUID.randomUUID().toString());
		video.setContentType(file.getContentType());
		video.setFilePath(file.getOriginalFilename());
		Video response = videoInterface.save(video, file);
		Video savedVideo = videoRepository.save(response);
		System.out.println("hi video transfered");
		if (savedVideo != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(CustomMessage.builder().message("video has uploaded").success(true).build());
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(CustomMessage.builder().message("video has not uploaded").success(false).build());

		}
	}

	@GetMapping("/stream/{videoId}")
	public ResponseEntity<Resource> stream(@PathVariable String videoId) {
		Video video = videoInterface.get(videoId);

		String contentType = video.getContentType();
		String filePath = video.getFilePath();
		Path path = Paths.get("video//" + filePath);
		Resource resource = new FileSystemResource(path);

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}

	@GetMapping("/allVideos")
	public List<Video> getAll() {
		return videoInterface.getAll();
	}

	@DeleteMapping("/deleteAll")
	public String deleteAll() {
		return videoInterface.deleteAll();
	}

	@GetMapping("/stream/range/{videoId}")
	public ResponseEntity<Resource> streamVideoRange(@PathVariable String videoId,
			@RequestHeader(value = "Range", required = false) String range, HttpServletResponse response) {

		Video video = videoInterface.get(videoId);
		Path path = Paths.get("video//" +video.getFilePath());

		Resource resource = new FileSystemResource(path);

		String contentType = video.getContentType();

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		// file length
		long fileLength = path.toFile().length();

		if (range == null) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
		}
		
		//calculate start and end range

		long rangeStart;
		long rangeEnd;

		String[] ranges = range.replace("bytes=", "").split("-");

		rangeStart = Long.parseLong(ranges[0]);

		if (ranges.length > 1)
			rangeEnd = Long.parseLong(ranges[1]);
		else
			rangeEnd = fileLength - 1;
		
		if(rangeEnd > fileLength-1)
			rangeEnd = fileLength - 1;
		
		System.out.println("startrange = " + rangeStart);
		System.out.println("Endrange = " + rangeEnd);
		InputStream inputStream = null;
		
		try {
			 inputStream = Files.newInputStream(path);
			 inputStream.skip(rangeStart);
		}
//		try {
//			inputStream = Files.newInputStream(path);
//			     OutputStream outputStream = response.getOutputStream();
//
//			    inputStream.skip(rangeStart);
//			    byte[] buffer = new byte[10240];  // Adjust buffer size if needed
//			    long bytesRemaining = rangeEnd - rangeStart + 1;
//			    int bytesRead;
//
//			    while ((bytesRead = inputStream.read(buffer, 0, (int)Math.min(buffer.length, bytesRemaining))) != -1 && bytesRemaining > 0) {
//			        outputStream.write(buffer, 0, bytesRead);
//			        bytesRemaining -= bytesRead;
//			    }
//			}
		catch(Exception ex) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		long contentLength = rangeEnd - rangeStart+1;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Range", "bytes "+rangeStart + "-"+rangeEnd + "/" + fileLength);
		httpHeaders.add("Cache-Control","no-cache, no-store, must-revalidate");
		httpHeaders.add("Pragma", "no-cache");
		httpHeaders.add("Expires", "0");
		httpHeaders.add("X-Content-Type-Options","nosniff");
		httpHeaders.setContentLength(contentLength);

		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.headers(httpHeaders)
				.contentType(MediaType.parseMediaType(contentType))
				.body(new InputStreamResource(inputStream));
	}
}
