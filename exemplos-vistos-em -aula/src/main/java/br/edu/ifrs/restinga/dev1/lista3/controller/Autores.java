/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.lista3.controller;

import br.edu.ifrs.restinga.dev1.lista3.erro.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Autor;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.AutorDAO;

@RestController
@RequestMapping("/api")
public class Autores {
    
    @Autowired
    AutorDAO autorDAO;
    
    @RequestMapping(path = "/autores/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Autor> listar() {
        return autorDAO.findAll();    
    }
    
    @RequestMapping(path = "/autores/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Autor buscar(@PathVariable int id) {
        final Optional<Autor> findById = autorDAO.findById(id);
        if(findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path="/autores/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {
        if(autorDAO.existsById(id)) {
            autorDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }

    @RequestMapping(path = "/autores/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Autor cadastrar(@RequestBody Autor autor) {
        autor.setId(0);
        Autor autorBanco = autorDAO.save(autor);
        return autorBanco;
    }
        
}
