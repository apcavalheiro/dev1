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
import br.edu.ifrs.restinga.dev1.lista3.modelo.dao.UsuarioDAO;
import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.Usuario;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class Usuarios {
    
    @Autowired
    UsuarioDAO usuarioDAO;
    
    @RequestMapping(path = "/usuarios/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Usuario> listar() {
        return usuarioDAO.findAll();        
    }
    
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Usuario buscar(@PathVariable int id) {
        final Optional<Usuario> findById = usuarioDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {
        if (usuarioDAO.existsById(id)) {
            usuarioDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    @RequestMapping(path = "/usuarios/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        usuario.setId(0);
        Usuario usuarioBanco = usuarioDAO.save(usuario);
        return usuarioBanco;
    }
    
}
