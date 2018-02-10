package com.evren.libraryservice.newPack;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book,String> {
    Flux<Book> findByAuthor(String author);
}