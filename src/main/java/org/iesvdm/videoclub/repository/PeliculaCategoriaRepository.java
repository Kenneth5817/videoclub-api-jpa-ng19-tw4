package org.iesvdm.videoclub.repository;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaCategoriaRepository extends JpaRepository<PeliculaCategoriaRepository,PeliculaRepository> {
}
