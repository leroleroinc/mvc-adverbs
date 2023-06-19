package com.lerolero.adverbs.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

import com.lerolero.adverbs.models.Adverb;

public interface MongoAdverbRepository extends MongoRepository<Adverb,String> {

	@Aggregation("{ $sample: { size: 1 } }")
	public Optional<Adverb> pullRandom();

}
