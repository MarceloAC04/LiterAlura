package com.example.literAlura.repository;

import com.example.literAlura.model.autor.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Author a WHERE a.Falecimento >= :year AND :year => a.Nascimento")
    List<Autor> searchAuthorByYear(int year);
}
