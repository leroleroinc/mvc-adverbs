package com.lerolero.adverbs.repositories;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.DefaultResourceLoader;

public class AdverbRepository {

	private ResourceLoader loader = new DefaultResourceLoader();

	private String fileName = "adverbs.dat";

	public String pullRandom() {
		Resource resource = loader.getResource(fileName);
		try {
			String[] words = resource.getContentAsString(Charset.defaultCharset()).split("\n");
			Integer randindex = (int)(Math.random() * words.length);
			return words[randindex];
		} catch (IOException e) {
			return null;
		}
	}
	
}
