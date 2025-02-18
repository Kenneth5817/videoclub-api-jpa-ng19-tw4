package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Idioma;
import org.iesvdm.videoclub.domain.Idioma;
import org.iesvdm.videoclub.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public class IdiomaRepository {
    public interface idiomaRepository extends JpaRepository<Idioma, Long> {

    }
    public void save(Idioma idioma) {
    }
}

