package com.lerolero.adverbs.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lerolero.adverbs.repositories.MongoAdverbRepository;
import com.lerolero.adverbs.models.Adverb;

@Service
public class AdverbService {

	@Autowired
	private MongoAdverbRepository repo;

	private String next() {
		return repo.pullRandom()
			.orElseThrow(() -> new RuntimeException("No adverb available"))
			.getString();
	}

	public String randomAdverb() {
		return next();
	}

	public List<String> randomAdverbList(Integer size) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < size; i++) list.add(next());
		return list;
	}

}
