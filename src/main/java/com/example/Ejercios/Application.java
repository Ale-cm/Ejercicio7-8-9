package com.example.Ejercios;

import com.example.Ejercios.Persistence.entity.Laptop;
import com.example.Ejercios.Persistence.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext context = (ApplicationContext)SpringApplication.run(Application.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		//crear un libro
		Laptop laptop = new Laptop(null,"casa","asdf");

		Laptop laptop1 = new Laptop(null,"hogar","Windows");

		//almacenar un libro
		System.out.println(repository.findAll().size());

		repository.save(laptop);
		repository.save(laptop1);
		System.out.println(repository.findAll().size());


		//recuperar todos los libros
		System.out.println(repository.findAll().size());


		//borrar un libro
		//repository.deleteById(1L);
		//System.out.println(repository.findAll().size());

	}
}
