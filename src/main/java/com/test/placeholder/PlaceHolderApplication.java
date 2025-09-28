package com.test.placeholder;

import com.github.javafaker.Faker;
import com.test.placeholder.models.Author;
import com.test.placeholder.repositories.AuthorRepository;
import com.test.placeholder.repositories.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlaceHolderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceHolderApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthorRepository repository, VideoRepository videoRepository){

		return  args -> {
			for (int i=0; i<50;i++){
				Faker faker = new Faker();
				var author = Author.builder()
					.firstName(faker.name().firstName())
					.lastName(faker.name().lastName())
					.age(faker.number().numberBetween(19,75))
					.email("redeem"+faker.name().firstName()+"saar@mikrosof.in.com")
					.build();
			repository.save(author);}
		};
	}
}
