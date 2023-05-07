package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MyException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired

    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MyException {

        validar(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);

        autorRepositorio.save(autor);

    }

    @Transactional
    public List<Autor> listarAutores() {
        List<Autor> autores = autorRepositorio.findAll();

        return autores;
    }

    public void modificarAutor(String nombre, String id) throws MyException {

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);

        }

    }

    @Transactional
    public void eliminar(String id) throws MyException {

        Autor autor = autorRepositorio.getById(id);

        autorRepositorio.delete(autor);

    }

    private void validar(String nombre) throws MyException {
        if (nombre == null || nombre.trim().isEmpty()) {

            throw new MyException("El nombre no puede ser nulo o estar vacio ");
        }

    }

    public Autor buscarPorId(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            return respuesta.get();
        }

        return null;
    }
    
    public Autor getOne (String id){
        
        return autorRepositorio.getOne(id);
    }
}
