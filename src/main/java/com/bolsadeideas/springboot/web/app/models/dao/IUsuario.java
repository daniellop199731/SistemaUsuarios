package com.bolsadeideas.springboot.web.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.web.app.models.entity.Usuario;

public interface IUsuario {
	
	public List<Usuario> findAll();
	
	public List<Usuario> findByCedula(String cedula);
	
	public List<Usuario> findByCorreo(String correo);	
	
	public boolean valUsuarioRepiteCorreo(String correo, Long id);
	
	public boolean valUsuarioRepiteCedula(String cedula, Long id);
		
	public Usuario findOneById(Long id);
		
	public void save(Usuario usuario);
	
	public void delete(Long id);
	
		
}
