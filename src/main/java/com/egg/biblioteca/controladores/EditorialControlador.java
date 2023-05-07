
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MyException;
import com.egg.biblioteca.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping ("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping ("/registrar")
    public String registrar () {
    
        return "editorial_form.html";
    }
    
    @PostMapping ("/registro")
    
    public String registro (@RequestParam String nombre, ModelMap modelo) {
        try {
            
            editorialServicio.crearEditorial(nombre);
            
            modelo.put("Exito", "La editorial fue registrada correctamente!.");
            
            
        } catch (MyException e) {
            
            modelo.put("Error", e.getMessage());
            
            return "editorial_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List <Editorial> editoriales = editorialServicio.listarEditorial();
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialServicio.getOne(id));
        
        return "editorial_modificar.html";
    }
    
}
