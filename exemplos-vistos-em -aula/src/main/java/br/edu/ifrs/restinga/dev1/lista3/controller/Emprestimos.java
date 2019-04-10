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
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.EmprestimoDAO;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Emprestimo;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class Emprestimos {
    
    @Autowired
    EmprestimoDAO emprestimoDAO;
    
    @RequestMapping(path = "/emprestimos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Emprestimo> listar() {
        return emprestimoDAO.findAll();        
    }
    
    @RequestMapping(path = "/emprestimos/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Emprestimo buscar(@PathVariable int id) {
        final Optional<Emprestimo> findById = emprestimoDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/emprestimos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {
        if (emprestimoDAO.existsById(id)) {
            emprestimoDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/emprestimos/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo cadastrar(@RequestBody Emprestimo emprestimo) {
        emprestimo.setId(0);
        Emprestimo emprestimoBanco = emprestimoDAO.save(emprestimo);
        return emprestimoBanco;
    }
    
}
