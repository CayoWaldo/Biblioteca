package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MyException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired

    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MyException {

        validar(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);

    }

    @Transactional
    public List<Editorial> listarEditorial() {
        List<Editorial> editorial = editorialRepositorio.findAll();

        return editorial;
    }

    @Transactional
    public void modicarEditorial(String nombre, String id) throws MyException {

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);

        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {

        Editorial editorial = editorialRepositorio.getById(id);

        editorialRepositorio.delete(editorial);

    }

    private void validar(String nombre) throws MyException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MyException("El nombre de la editorial no puede ser nulo o estar vacio");
        }
    }

    public Editorial buscarPorId(String id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
     
        if (respuesta.isPresent()) {
            return respuesta.get();
        }

        return null;
    }
    
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }

}
