/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.lista3.modelo.dao;

import br.edu.ifrs.restinga.dev1.lista3.modelo.entidade.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneDAO extends CrudRepository<Telefone, Integer> {
    
}
