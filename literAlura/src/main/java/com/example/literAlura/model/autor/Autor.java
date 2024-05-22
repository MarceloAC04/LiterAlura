package com.example.literAlura.model.autor;

import com.example.literAlura.model.livro.Livro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private int nascimento;
    private int falecimento;

    @Column(unique = true)
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livro = new ArrayList<>();

    public Autor() {}

    public Autor(List<DadosAutor> dados) {
        this.nome = dados.get(0).nome();
        this.nascimento = dados.get(0).nascimento();
        this.falecimento = dados.get(0).falecimento();
    }

    public Autor(Autor dados) {
        this.nome = dados.nome;
        this.nascimento = dados.nascimento;
        this.falecimento = dados.falecimento;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public int getFalecimento() {
        return falecimento;
    }

    public void setFalecimento(int falecimento) {
        this.falecimento = falecimento;
    }

    public List<Livro> getLivro() {
        return livro;
    }

    public void setLivro(List<Livro> livro) {
        this.livro = livro;
    }
}
