package com.example.literAlura.model.livro;

import com.example.literAlura.model.autor.Autor;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    @ManyToOne()
    private Autor autor;
    private String idioma;
    private int downloads;

    public Livro() {}

    public Livro(DadosLivro dados) {
        this.titulo = dados.titulo();
        this.autor = new Autor(dados.autores());
        this.idioma = dados.idiomas().get(0);
        this.downloads = dados.downloads();
    }

    public Livro(Livro dados, Autor autor) {
        this.titulo = dados.titulo;
        setAutor(autor);
        this.idioma = dados.idioma;
        this.downloads = dados.downloads;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idioma='" + idioma + '\'' +
                ", downloads='" + downloads + '\'';
    }
}
