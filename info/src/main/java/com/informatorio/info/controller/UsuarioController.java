package com.informatorio.info.controller;


import com.informatorio.info.entity.Usuario;
import com.informatorio.info.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuarioById (@PathVariable ("id") Long id )  {return usuarioRepository.getById(id);}

    @RequestMapping(value = "/usuario", method = RequestMethod.POST)
    public Usuario createUsuario(@RequestBody Usuario usuario)  {return usuarioRepository.save(usuario);
    }
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public List<Usuario> getAll(){return usuarioRepository.findAll();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Usuario edit (@PathVariable ("id") Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).get();
        usuarioExistente.setDireccion(usuario.getDireccion());
        return usuarioRepository.save(usuarioExistente);}
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void borrarPorId (@PathVariable ("id") Long id ){usuarioRepository.deleteById(id);}
}

