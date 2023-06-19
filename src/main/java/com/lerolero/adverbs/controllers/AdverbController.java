package com.lerolero.adverbs.controllers;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.lerolero.adverbs.services.AdverbService;

@RestController
@RequestMapping("/adverbs")
public class AdverbController {

	@Autowired
	private AdverbService adverbService;

	@GetMapping
	public List<String> get(@RequestParam(defaultValue = "1") Integer size) {
		return adverbService.randomAdverbList(size);
	}

	@GetMapping("/events")
	public SseEmitter subscribe(@RequestParam(defaultValue = "200") Integer interval) {
		SseEmitter emitter = new SseEmitter(-1L);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> {
			try {
				while (true) {
					Thread.sleep(interval); //ms
					String adverb = adverbService.randomAdverb();
					emitter.send(adverb);
				}
			} catch (Exception e) {
				emitter.completeWithError(e);
			} finally {
				emitter.complete();
			}
		});
		executor.shutdown();
		return emitter;
	}

}
