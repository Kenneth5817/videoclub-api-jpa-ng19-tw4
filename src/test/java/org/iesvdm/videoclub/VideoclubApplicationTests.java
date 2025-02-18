package org.iesvdm.videoclub;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Idioma;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.IdiomaRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class VideoclubApplicationTests {

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    IdiomaRepository idiomaRepository;

    @Test
    void contextLoads() {
        // Corregido el uso de findById. El método findById devuelve un Optional, por lo que debes usar orElse().
        Categoria categoria = this.categoriaRepository.findById(3L).orElse(null);

        // Corregido el uso de builder para Idioma
        Idioma idioma = Idioma.builder().id(4L).build();

        // Corregido el uso de HashSet para las categorías
        Pelicula pelicula = Pelicula.builder()
                .titulo("Misión Imposible")
                .descripcion("Fantasmada de acción")
                .idioma(idioma)
                .categorias(new HashSet<>()) // Crear un conjunto vacío para las categorías
                .build();

        // Añadir la categoría a la película
        if (categoria != null) {
            pelicula.getCategorias().add(categoria);
        }

        peliculaRepository.save(pelicula);
    }
    @Test
    void testAddPelicula() {
        // Crear una categoría
        Categoria categoria = new Categoria();
        categoria.setNombre("Acción");
        categoria.setUltimaActualizacion(new Date());
        categoriaRepository.save(categoria);

        // Crear un idioma
        Idioma idioma = Idioma.builder()
                .nombre("Español")
                .ultimaActualizacion(new Date())
                .build();
        idiomaRepository.save(idioma);

        // Crear la película
        Pelicula pelicula = Pelicula.builder()
                .titulo("Misión Imposible")
                .descripcion("Película de acción")
                .anyoLanzamiento(new Date())
                .idioma(idioma)
                .duracionAlquiler(7)
                .rentalRate(BigDecimal.valueOf(2.99))
                .replacementCost(BigDecimal.valueOf(9.99))
                .clasificacion("PG-13")
                .caracteristicasEspeciales("Ninguna")
                .ultimaActualizacion(LocalDateTime.now())
                .build();

        // Añadir la categoría a la película
        pelicula.getCategorias().add(categoria);

        // Guardar la película
        peliculaRepository.save(pelicula);

        // Verificar que la película ha sido guardada
        Pelicula savedPelicula = peliculaRepository.findById(pelicula.getIdPelicula()).orElse(null);
        assertNotNull(savedPelicula);
        assertEquals("Misión Imposible", savedPelicula.getTitulo());
        assertTrue(savedPelicula.getCategorias().contains(categoria));
    }
    @Test
    void testDeletePelicula() {
        // Crear y guardar una película
        Categoria categoria = new Categoria();
        categoria.setNombre("Acción");
        categoria.setUltimaActualizacion(new Date());
        categoriaRepository.save(categoria);

        Idioma idioma = Idioma.builder()
                .nombre("Español")
                .ultimaActualizacion(new Date())
                .build();
        idiomaRepository.save(idioma);

        Pelicula pelicula = Pelicula.builder()
                .titulo("Misión Imposible")
                .descripcion("Película de acción")
                .anyoLanzamiento(new Date())
                .idioma(idioma)
                .duracionAlquiler(7)
                .rentalRate(BigDecimal.valueOf(2.99))
                .replacementCost(BigDecimal.valueOf(9.99))
                .clasificacion("PG-13")
                .caracteristicasEspeciales("Ninguna")
                .ultimaActualizacion(LocalDateTime.now())
                .build();

        // Añadir la categoría a la película
        pelicula.getCategorias().add(categoria);
        peliculaRepository.save(pelicula);

        // Eliminar la película
        peliculaRepository.delete(pelicula);

        // Verificar que la película ha sido eliminada
        Pelicula deletedPelicula = peliculaRepository.findById(pelicula.getIdPelicula()).orElse(null);
        assertNull(deletedPelicula);
    }

    @Test
    void testUpdatePelicula() {
        // Crear una categoría
        Categoria categoria = new Categoria();
        categoria.setNombre("Comedia");
        categoria.setUltimaActualizacion(new Date());
        categoriaRepository.save(categoria);

        // Crear un idioma
        Idioma idioma = Idioma.builder()
                .nombre("Inglés")
                .ultimaActualizacion(new Date())
                .build();
        idiomaRepository.save(idioma);

        // Crear la película
        Pelicula pelicula = Pelicula.builder()
                .titulo("La gran comedia")
                .descripcion("Película divertida")
                .anyoLanzamiento(new Date())
                .idioma(idioma)
                .duracionAlquiler(5)
                .rentalRate(BigDecimal.valueOf(1.99))
                .replacementCost(BigDecimal.valueOf(7.99))
                .clasificacion("PG")
                .caracteristicasEspeciales("Comedia")
                .ultimaActualizacion(LocalDateTime.now())
                .build();

        // Añadir la categoría a la película
        pelicula.getCategorias().add(categoria);
        peliculaRepository.save(pelicula);

        // Actualizar la película
        pelicula.setTitulo("La gran comedia 2");
        pelicula.setDescripcion("Secuela divertida");
        peliculaRepository.save(pelicula);

        // Verificar la actualización
        Pelicula updatedPelicula = peliculaRepository.findById(pelicula.getIdPelicula()).orElse(null);
        assertNotNull(updatedPelicula);
        assertEquals("La gran comedia 2", updatedPelicula.getTitulo());
        assertEquals("Secuela divertida", updatedPelicula.getDescripcion());
    }
    @Test
    void testAddAndRemoveCategoria() {
        // Crear una categoría
        Categoria categoria = new Categoria();
        categoria.setNombre("Drama");
        categoria.setUltimaActualizacion(new Date());
        categoriaRepository.save(categoria);

        // Crear una película
        Idioma idioma = Idioma.builder()
                .nombre("Francés")
                .ultimaActualizacion(new Date())
                .build();
        idiomaRepository.save(idioma);

        Pelicula pelicula = Pelicula.builder()
                .titulo("El drama de la vida")
                .descripcion("Película dramática")
                .anyoLanzamiento(new Date())
                .idioma(idioma)
                .duracionAlquiler(7)
                .rentalRate(BigDecimal.valueOf(3.99))
                .replacementCost(BigDecimal.valueOf(10.99))
                .clasificacion("R")
                .caracteristicasEspeciales("Emocional")
                .ultimaActualizacion(LocalDateTime.now())
                .build();

        peliculaRepository.save(pelicula);

        // Añadir la categoría a la película
        pelicula.getCategorias().add(categoria);
        peliculaRepository.save(pelicula);

        // Verificar que la categoría ha sido añadida
        Pelicula savedPelicula = peliculaRepository.findById(pelicula.getIdPelicula()).orElse(null);
        assertNotNull(savedPelicula);
        assertTrue(savedPelicula.getCategorias().contains(categoria));

        // Eliminar la categoría
        pelicula.getCategorias().remove(categoria);
        peliculaRepository.save(pelicula);

        // Verificar que la categoría ha sido eliminada
        Pelicula updatedPelicula = peliculaRepository.findById(pelicula.getIdPelicula()).orElse(null);
        assertNotNull(updatedPelicula);
        assertFalse(updatedPelicula.getCategorias().contains(categoria));
    }
    @Test
    void testFindByTitle() {
        // Crear e insertar una película
        Idioma idioma = Idioma.builder().nombre("Inglés").ultimaActualizacion(new Date()).build();
        idiomaRepository.save(idioma);

        Categoria categoria = new Categoria();
        categoria.setNombre("Ciencia Ficción");
        categoria.setUltimaActualizacion(new Date());
        categoriaRepository.save(categoria);

        Pelicula pelicula = Pelicula.builder()
                .titulo("Star Wars")
                .descripcion("Ciencia Ficción épica")
                .idioma(idioma)
                .categorias(new HashSet<>())
                .build();
        pelicula.getCategorias().add(categoria);
        peliculaRepository.save(pelicula);

        // Buscar la película por título
        Pelicula foundPelicula = peliculaRepository.findByTitulo("Star Wars");
        assertNotNull(foundPelicula);
        assertEquals("Star Wars", foundPelicula.getTitulo());
    }

    @Test
    void testCrearCategoria() {
        // Crear una nueva categoría
        Categoria categoria = Categoria.builder()
                .nombre("Acción")
                .build();

        // Guardar la categoría
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        // Verificar que se ha guardado correctamente
        assertNotNull(categoriaGuardada.getId());
        assertEquals("Acción", categoriaGuardada.getNombre());
    }


    @Test
    void testActualizarCategoria() {
        // Crear una nueva categoría y guardarla
        Categoria categoria = Categoria.builder()
                .nombre("Acción")
                .build();
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        // Actualizar el nombre de la categoría
        categoriaGuardada.setNombre("Aventura");
        Categoria categoriaActualizada = categoriaRepository.save(categoriaGuardada);

        // Verificar que la categoría se ha actualizado correctamente
        assertEquals("Aventura", categoriaActualizada.getNombre());
    }
    @Test
    void testEliminarCategoria() {
        // Crear una nueva categoría y guardarla
        Categoria categoria = Categoria.builder()
                .nombre("Acción")
                .build();
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        // Obtener el ID de la categoría guardada
        Long categoriaId = categoriaGuardada.getId();

        // Eliminar la categoría
        categoriaRepository.delete(categoriaGuardada);

        // Verificar que la categoría ha sido eliminada
        assertFalse(categoriaRepository.findById(categoriaId).isPresent());
    }


    @Test
    void testListarCategorias() {
        // Crear algunas categorías
        Categoria categoria1 = Categoria.builder().nombre("Acción").build();
        Categoria categoria2 = Categoria.builder().nombre("Comedia").build();
        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);

        // Obtener la lista de categorías
        List<Categoria> categorias = categoriaRepository.findAll();

        // Verificar que se han guardado las categorías
        assertTrue(categorias.size() >= 2);
        assertTrue(categorias.stream().anyMatch(c -> c.getNombre().equals("Acción")));
        assertTrue(categorias.stream().anyMatch(c -> c.getNombre().equals("Comedia")));
    }
}

