package com.example.literAlura.main;

import com.example.literAlura.model.autor.Autor;
import com.example.literAlura.model.dados.Dados;
import com.example.literAlura.model.livro.Livro;
import com.example.literAlura.repository.AutorRepository;
import com.example.literAlura.repository.LivroRepository;
import com.example.literAlura.service.ConsumoApi;
import com.example.literAlura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados converte = new ConverteDados();

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public Main(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar nossos autores
                    4 - Listar autores em determinado ano
                    5 - Listar livros em determinado idioma
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    listAuthor();
                    break;
                case 4:
                   searchAuthorByYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private Dados obterDadosLivro(String busca) {
        var json = consumo.obterDados(ENDERECO + busca.toLowerCase().replace(" ", "%20"));
       return converte.obterDados(json, Dados.class);
    }

    private void validarDados(Dados dados) {
        Livro livro = new Livro(dados.livros().get(0));
        Autor autor = new Autor(livro.getAutor());

        Optional<Autor> autoDb = autorRepository.findByNome(autor.getNome());
        Optional<Livro> livroDb = livroRepository.findByTituloContainingIgnoreCase(livro.getTitulo());
        if (livroDb.isPresent()) {
            Livro livroOptional = livroDb.get();
            if (livro.getTitulo().equalsIgnoreCase(livroOptional.getTitulo())) {
                showBook(livroOptional);
                return;
            }
        }

        if (autoDb.isPresent()) {
            Autor autorOptional = autoDb.get();
            System.out.println("Salvando novo livro para o(a) autor(a)" + autorOptional.getNome());
            Livro novoLivro = new Livro(livro, autorOptional);
            showBook(novoLivro);
        }
        else {
            System.out.println("\nNovo livro e autor cadastrados\n");

            Livro novoLivro = new Livro(livro, autor);
            showBook(novoLivro);
            autorRepository.save(autor);
            livroRepository.save(novoLivro);
        }

    }

    public void searchBook() {
        System.out.println("Digite o nome da livro para busca:");
        var bookName = leitura.nextLine();
        var conversao = obterDadosLivro(bookName);
        validarDados(conversao);
    }

    public void listBooks() {
        List<Livro> livroList = livroRepository.findAll();
        livroList.forEach(this::showBook);
    }

    public void showBook(Livro livro) {
        var book = "\nTitulo" + livro.getTitulo() +
                "\nAutor" + livro.getAutor().getNome() +
                "\nIdioma" + livro.getIdioma() +
                "\nDownloads" + livro.getDownloads();
        System.out.println(book);
    }

    public void listAuthor() {
        List<Autor> authorList = autorRepository.findAll();
        authorList.forEach(this::showAuthor);
    }

    public void showAuthor(Autor autor) {
        var author = "\nNome" + autor.getNome() +
                "\nNascimento" + autor.getNascimento() +
                "\nFalecimento" + autor.getFalecimento();
        System.out.println(author);
    }

    public void listBooksByLanguage(){
        System.out.println("Digite o idioma para busca:");
        var language = leitura.nextLine();
        List<Livro> livroList = livroRepository.findByIdiomaContainingIgnoreCase(language);
        livroList.forEach(this::showBook);
    }

    public void searchAuthorByYear() {
        System.out.println("Digite o ano para busca");
        var year = leitura.nextInt();
        List<Autor> autorList = autorRepository.searchAuthorByYear(year);
        autorList.forEach(this::showAuthor);
    }

}
