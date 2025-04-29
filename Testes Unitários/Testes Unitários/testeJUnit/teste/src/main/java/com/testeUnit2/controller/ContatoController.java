package com.testeUnit2.controller;


import com.testeUnit2.model.Contato;
import com.testeUnit2.services.ContatoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/contato")
public class ContatoController {

    @Autowired
    private ContatoServices contatoServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato salvar(@RequestBody Contato contato){
        return contatoServices.salvar(contato);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Contato> listarContatos(){
        return contatoServices.listarContatos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Contato buscarContatoPorId(@PathVariable("id") Long id){
        return contatoServices.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCliente(@PathVariable("id") Long id){
        contatoServices.buscarPorId(id)
                .map(contato -> {
                    contatoServices.removerPorId(contato.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarContatoAtualizar(@PathVariable("id") Long id, @RequestBody Contato contatoAtualizado) {
        contatoServices.atualizarContato(id, contatoAtualizado);
    }

}
