package com.example.Ejercios.Contoller;

import com.example.Ejercios.Persistence.entity.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControlerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar que ingresamos al controlador")
    @Test
    void helloLaptop() {

        ResponseEntity<String> response =
                testRestTemplate.getForEntity("/laptop", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("hola laptop", response.getBody());
    }

    @DisplayName("Comprobar que devuelve todos los elementos")
    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response =
                testRestTemplate.getForEntity("/laptop/all", Laptop[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Laptop> laptop = Arrays.asList(response.getBody());
        for (Laptop laptop1 : laptop
        ) {
            System.out.println(laptop1.getId());
        }
        System.out.println(laptop.toString());
        System.out.println(" lista tamaño " + laptop.size());
    }

    @DisplayName("Comprobar que devuelve un elemento segun Id")
    @Test
    void findOneById() {
        ResponseEntity<Laptop> response =
                testRestTemplate.getForEntity("/laptop/id/2", Laptop.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    void laptopController() {
        //armar cabecera
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        //armar el body
        String json = """
                {
                                    "name": "Libro creado desde Spring Test",
                                    "mark": "Yuval Noah"
                                    
                                }
                 """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> response= testRestTemplate.exchange("/laptop/create",HttpMethod.POST,request,Laptop.class); //direccionUrl - metodo http - lo que mandas - la clase que devuelve.

        Laptop laptop = response.getBody();
        assertEquals(6L,laptop.getId());
        assertEquals("Libro creado desde Spring Test",laptop.getName());


    }

    @Test
    void update() {

        //armar cabecera
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        //armar el body
        String json = """
                {                   "id": 12,
                                    "name": "Libro creado desde Spring Test",
                                    "mark": "camilo sesto"
                                    
                                }
                 """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> response= testRestTemplate.exchange("/laptop/update",HttpMethod.POST,request,Laptop.class); //direccionUrl - metodo http - lo que mandas - la clase que devuelve.

        Laptop laptop = response.getBody();
        assertEquals(12L,laptop.getId());
        assertEquals("camilo sesto",laptop.getMark());


    }

    @Test
    void delete() {
        ResponseEntity<Laptop> response =
                testRestTemplate.getForEntity("/laptop/delete/4", Laptop.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println(response.getBody());


    }

    @Test
    void deleteAll() {
    }
}