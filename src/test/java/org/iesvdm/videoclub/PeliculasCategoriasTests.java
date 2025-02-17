package org.iesvdm.videoclub;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Idioma;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.IdiomaRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class )
@SpringBootTest
class PeliculasCategoriasApplicationTests {

    @Autowired
    PeliculaRepository peliculaRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }


    @Test
    void peliculaCategoriasTest(){
        Categoria categoria=Categoria.builder().nombre("Accion").build();
        this.categoriaRepository.save(categoria);

/**
        Pelicula pelicula=Pelicula.builder()
                .titulo("Pelicula 1"),
                .idioma("iif"),
                .build();
    }
    @Test
    @Order(1)
    void grabarMultiplesPeliculasCategoriasIdioma() {

        Idioma idioma1 = new Idioma(0, "español", new HashSet<>());
        idiomaRepository.save(idioma1);

        Categoria categoria1 = new Categoria(0, "Categoria1", idioma1);
        idioma1.getCategorias().add(categoria1);
        categoriaRepository.save(categoria1);

        Categoria categoria2 = new Categoria(0, "Categoria2", idioma1);
        idioma1.getCategorias().add(categoria2);
        categoriaRepository.save(categoria2);

        Idioma idioma2 = new Idioma(0, "inglés", new HashSet<>());
        idiomaRepository.save(idioma2);

        Categoria categoria3 = new Categoria(0, "Categoria3", idioma2);
        idioma2.getCategorias().add(categoria3);
        categoriaRepository.save(categoria3);
    }

    @Test
    @Order(2)
    void actualizarIdiomaPeliculaNull() {

        Categoria categoria1 = categoriaRepository.findById(1L).orElse(null);
        pelicula1.setIdioma(null);
        peliculaRepository.save(pelicula1);

    }

    @Test
    void contextLoads() {
    }

}
