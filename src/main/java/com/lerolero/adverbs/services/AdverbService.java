package com.lerolero.adverbs.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lerolero.adverbs.repositories.MongoAdverbRepository;
import com.lerolero.adverbs.repositories.AdverbCache;
import com.lerolero.adverbs.models.Adverb;

@Service
public class AdverbService {

	@Autowired
	private MongoAdverbRepository repo;

	@Autowired
	private AdverbCache cache;

	private String next() {
		Adverb adverb;
		try {
			adverb = cache.next();
		} catch (AdverbCache.CacheMissException e) {
			adverb = repo.findById(e.getKey())
				.orElseThrow(() -> new RuntimeException("No adverb available"));
			cache.add(adverb);
		}
		return adverb.getString();
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
