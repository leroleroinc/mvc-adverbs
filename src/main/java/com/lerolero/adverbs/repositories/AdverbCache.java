package com.lerolero.adverbs.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.redisson.api.RedissonClient;
import org.redisson.api.RBucket;

import com.lerolero.adverbs.repositories.MongoAdverbRepository;
import com.lerolero.adverbs.models.Adverb;

@Repository
public class AdverbCache {

	@Autowired
	private RedissonClient redis;

	@Autowired
	private MongoAdverbRepository repo;

	private List<String> ids;

	public Adverb next() throws CacheMissException {
		if (ids == null) {
			ids = repo.findAll().stream().map(Adverb::getId).collect(Collectors.toList());
		}
		String id = ids.get((int)(Math.random() * ids.size()));
		RBucket<Adverb> bucket = redis.getBucket("/adverb/" + id);
		if (bucket.get() == null) throw new CacheMissException(id);
		return bucket.get();
	}

	public void add(Adverb adverb) {
		RBucket<Adverb> bucket = redis.getBucket("/adverb/" + adverb.getId());
		bucket.set(adverb);
		ids.add(adverb.getId());
	}

	public static class CacheMissException extends Exception {
		private String key;
		public CacheMissException(String key) {
			this.key = key;
		}
		public String getKey() {
			return key;
		}
	}

}
