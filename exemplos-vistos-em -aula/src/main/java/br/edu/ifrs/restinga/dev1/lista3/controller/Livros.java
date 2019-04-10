/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.lista3.controller;

import br.edu.ifrs.restinga.dev1.lista3.erro.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.lista3.erro.RequisicaoInvalida;
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.AutorDAO;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.LivroDAO;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Autor;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Livro;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class Livros {
    
    @Autowired
    LivroDAO livroDAO;
    
    @Autowired
    AutorDAO autorDAO;
    
    @RequestMapping(path = "/livros/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Livro> listar() {
        return livroDAO.findAll();        
    }
    
    @RequestMapping(path = "/livros/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Livro buscar(@PathVariable int id) {
        final Optional<Livro> findById = livroDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/livros/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {
        if (livroDAO.existsById(id)) {
            livroDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/livros/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Livro cadastrar(@RequestBody Livro livro) {
        livro.setId(0);
        Livro livroBanco = livroDAO.save(livro);
        return livroBanco;
    }
    
    @RequestMapping(path = "/livros/{idLivro}/autores/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarAutor(@PathVariable int idLivro, @RequestBody Autor autor) {
        Livro livro = this.buscar(idLivro);
               
        if(livro.getAutores().size()>=3) {
            throw new RequisicaoInvalida("Livro não pode ter mais de 3 autores");
            }
        if(!autorDAO.existsById(autor.getId())) {
            throw new NaoEncontrado("ID do autor não econtrado!");
        }
        livro.getAutores().add(autor);
        livroDAO.save(livro);
    }
    
    @RequestMapping(path = "/livros/{idLivro}/autores/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Autor> listarAutores(@PathVariable int idLivro) {
        return this.buscar(idLivro).getAutores();
    }

    @RequestMapping(path = "/livros/{idLivro}/autores/{idAutor}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarAutor(@PathVariable int idLivro, @PathVariable int idAutor) {
        Livro livro = this.buscar(idLivro);
        Autor autorEncontrado= null;
        List<Autor> autores = livro.getAutores();
        for (Autor autor : autores) {
            if(autor.getId()==idAutor) {
                autorEncontrado=autor;
            }
        }
        if(autorEncontrado==null) {
            throw new NaoEncontrado("ID do autor não econtrado!");
        }
        
        livro.getAutores().remove(autorEncontrado);
        livroDAO.save(livro);
        
    }

    
}
