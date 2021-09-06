  
package com.informatorio.info.service;

import com.informatorio.info.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UsuarioService {

    @Autowired
    private static UsuarioRepository usuarioRepository;
    public static Date creacion(){
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    };
}