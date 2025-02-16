package org.iesvdm.videoclub.exception;

public class CategoriaNotFoundException extends RuntimeException {

    public CategoriaNotFoundException(Long id) {
        super("No se encontró la categoría con el id: " + id);
    }
}
