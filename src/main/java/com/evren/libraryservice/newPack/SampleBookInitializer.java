package com.evren.libraryservice.newPack;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Slf4j
@Component
public class SampleBookInitializer implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(SampleBookInitializer.class);
    private final BookRepository bookRepository;

    SampleBookInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Flux.just("a|a1","b|b1").map(t->t.split("\\|")).map(tup->tup[0]).flatMap()


        // @formatter:off
        this.bookRepository
                .deleteAll()
                .thenMany(
                        Flux.just("Professional Java Development with the Spring Framework|rjohnson",
                                "Cloud Native Java|jlong", "Spring Security 3.1|rwinch", "Spring in Action|cwalls"))
                .map(t -> t.split("\\|"))
                .map(tuple -> new Book(null, tuple[0], tuple[1]))
                .flatMap(this.bookRepository::save)
                .thenMany(this.bookRepository.findAll())
                .subscribe(book -> log.info(book.toString()));
        // @formatter:on
    }
}