package com.bolsadeideas.springboot.web.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.web.app.models.dao.IUsuario;
import com.bolsadeideas.springboot.web.app.models.entity.Usuario;

@Controller

@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuario usuarioDAO;
	
	@GetMapping("/listar")
	public String listarUsuarios(Model model) {
		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios", usuarioDAO.findAll());
		return "listarUsuarios";
	}
	
	@GetMapping("/form")
	public String crearUsuario(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Formulario de creacion de Usuarios");
		return "form";
	}
	
	@PostMapping("/form")
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model) {	
		//System.out.println(usuarioDAO.findByCedula(usuario.getCedula()));		
		String strMensaje = null;
		
		if(usuarioDAO.valUsuarioRepiteCedula(usuario.getCedula(), usuario.getId())) {
			strMensaje = "La cedula digitada ya esta registrada";
			model.addAttribute("titulo", "Formulario de creacion de Usuarios");		
			model.addAttribute("errorCedulaYaExiste", strMensaje);
			return "form";
		}
		
		if(usuarioDAO.valUsuarioRepiteCorreo(usuario.getCorreo(), usuario.getId() )) {
			strMensaje = "El correo digitado ya esta registrado";
			model.addAttribute("titulo", "Formulario de creacion de Usuarios");		
			model.addAttribute("errorCorreoYaExiste", strMensaje);
			return "form";
		}		
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de creacion de Usuarios");
			return "form";
		}
		
		usuarioDAO.save(usuario);  		
		return "redirect:/usuarios/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarUsuario(@PathVariable(name = "id") Long id, Model model) {
		Usuario usuario = null;
		
		if(id > 0) {
			usuario = usuarioDAO.findOneById(id);
		} else {
			return "redirect:/usuarios/listar";
		}
		if (usuario == null) {
			return "redirect:/usuarios/listar";		
		}
		model.addAttribute("usuario", usuario);		
		model.addAttribute("titulo", "Formulario de creacion de Usuarios");
		return "form";
	}	
	
	@GetMapping("/delete/{id}")
	public String borrarUsuario(@PathVariable(name = "id") Long id, Model model) {		
		usuarioDAO.delete(id);
		return "redirect:/usuarios/listar";			
	}	
		
}
