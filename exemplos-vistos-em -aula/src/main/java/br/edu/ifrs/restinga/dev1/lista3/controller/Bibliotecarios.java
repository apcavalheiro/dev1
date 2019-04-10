/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.lista3.controller;

import br.edu.ifrs.restinga.dev1.lista3.erro.NaoEncontrado;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.BibliotecarioDAO;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Bibliotecario;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class Bibliotecarios {
    
    @Autowired
    BibliotecarioDAO bibliotecarioDAO;
    
    @RequestMapping(path = "/bibliotecarios/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Bibliotecario> listar() {
        return bibliotecarioDAO.findAll();        
    }
    
    @RequestMapping(path = "/bibliotecarios/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Bibliotecario buscar(@PathVariable int id) {
        final Optional<Bibliotecario> findById = bibliotecarioDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/bibliotecarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {
        if (bibliotecarioDAO.existsById(id)) {
            bibliotecarioDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/bibliotecarios/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Bibliotecario cadastrar(@RequestBody Bibliotecario bibliotecario) {
        bibliotecario.setId(0);
        Bibliotecario bibliotecarioBanco = bibliotecarioDAO.save(bibliotecario);
        return bibliotecarioBanco;
    }
    
}
