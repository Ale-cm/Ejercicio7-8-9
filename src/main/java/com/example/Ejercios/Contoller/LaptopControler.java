package com.example.Ejercios.Contoller;

import com.example.Ejercios.Persistence.entity.Laptop;
import com.example.Ejercios.Persistence.repository.LaptopRepository;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/laptop")
public class LaptopControler {
    private final Logger log = LoggerFactory.getLogger(LaptopControler.class);
    private final LaptopRepository laptopRepository;


    public LaptopControler(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }



    @GetMapping
    public String helloLaptop() {
     log.info("Entramos al controler");
        return "hola laptop";
    }

    @GetMapping("/all")
    @ApiOperation("Buscar todas las laptos")
    public List<Laptop> findAll() {
        return this.laptopRepository.findAll();
    }

    @GetMapping("/id/{id}")
    @ApiOperation("Buscar una laptop por clave primaria id long")
    public Laptop findOneById(@PathVariable("id") Long id) {
        Optional<Laptop> laptopOpt = this.laptopRepository.findById(id);
        return laptopOpt.orElse(null);
    }

    @PostMapping("/create")
    public ResponseEntity<Laptop> LaptopController(@RequestBody Laptop laptop,@RequestHeader HttpHeaders httpHeaders) {
     System.out.println(httpHeaders.get("User-Agent"));

     if(laptop.getId()!=null){
      log.warn("Trying to create a laptop with id");
      return ResponseEntity.badRequest().build();
     }
      Laptop result= laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {
     if(laptop.getId()==null){
      log.warn("Trying to update a non existent laptop");
      return ResponseEntity.badRequest().build();
     }
     if(!laptopRepository.existsById(laptop.getId())){
      log.warn("Trying to update a non existent laptop");
      return ResponseEntity.notFound().build();

     }
     Laptop result= laptopRepository.save(laptop);
     return ResponseEntity.ok(result);
    }

    @ApiIgnore// ignorar este método para que no aparezca en la documentación de la api Swagger

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Laptop> delete(@PathVariable Long id) {
        if(!laptopRepository.existsById(id)){
            log.warn("Trying to delete a non existent laptop");
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    @Transactional
    public ResponseEntity<Laptop> deleteAll() {
        log.info("Rest Request for delete all books");
        if(laptopRepository.findAll().size()!=0){
            log.warn("Trying to delete a non existent laptops");
            return ResponseEntity.noContent().build();
        }
        this.laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
