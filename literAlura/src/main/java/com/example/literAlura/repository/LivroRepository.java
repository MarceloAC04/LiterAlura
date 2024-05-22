package com.example.literAlura.repository;

import com.example.literAlura.model.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByIdiomaContainingIgnoreCase(String idioma);

    List<Livro> findAll();
}
