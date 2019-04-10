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
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.EditoraDAO;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Editora;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class Editoras {
    
    @Autowired
   EditoraDAO editoraDAO;
    
    @RequestMapping(path = "/editoras/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Editora> listar() {
        return editoraDAO.findAll();    
    }
    
    @RequestMapping(path = "/editoras/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Editora buscar(@PathVariable int id) {
        final Optional<Editora> findById = editoraDAO.findById(id);
        if(findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path="/editoras/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {
        if(editoraDAO.existsById(id)) {
            editoraDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }

    @RequestMapping(path = "/editoras/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Editora cadastrar(@RequestBody Editora editora) {
       Editora editoraBanco = editoraDAO.save(editora);
        return editoraBanco;
    }
        
}
