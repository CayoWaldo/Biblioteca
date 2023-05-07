package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MyException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MyException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorServicio.buscarPorId(idAutor);
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
        
    }

    @Transactional
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();

        return libros;
    }

    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MyException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Libro libro = buscarPorId(isbn);
        Autor respuestaAutor = autorServicio.buscarPorId(idAutor);
        Editorial respuestaEditorial = editorialServicio.buscarPorId(idEditorial);
        

        libro.setTitulo(titulo);
        libro.setAutor(respuestaAutor);
        libro.setEditorial(respuestaEditorial);
        libro.setEjemplares(ejemplares);
        libroRepositorio.save(libro);

    }
    
    public Libro getOne(Long isbn){
       return libroRepositorio.getOne(isbn);
    }
    

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MyException {

        if (isbn == null) {

            throw new MyException("El isbn no puede ser nulo");
        }

        if (titulo.isEmpty() || titulo == null) {

            throw new MyException("El titulo no puede ser nulo o estar vacio ");
        }

        if (ejemplares == null) {

            throw new MyException("El ejemplar no puede ser nulo");
        }

        if (idAutor.isEmpty() || idAutor == null) {

            throw new MyException("El Id del autor no puede ser nulo o estar vacio ");
        }

        if (idEditorial.isEmpty() || idEditorial == null) {

            throw new MyException("El Id de la editorial no puede ser nulo o estar vacio ");
        }

    }

    public Libro buscarPorId(Long id) {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        
        return null;
    }
}
