package com.bolsadeideas.springboot.web.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.web.app.models.entity.Usuario;

@Repository
public class UsuarioDaoImp implements IUsuario{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@ReadOnlyProperty
	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Usuario").getResultList(); //IMPORTANTE despues del from es el nombre de la clase, no el de la tabla
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		if(usuario.getId() != null && usuario.getId() > 0) {
			em.merge(usuario);
		} else {
			em.persist(usuario);
		}
		
	}

	@Override
	@ReadOnlyProperty
	public Usuario findOneById(Long id) {
		return em.find(Usuario.class, id);
	}
	
	
	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOneById(id));
	}

	@SuppressWarnings("unchecked")
	@ReadOnlyProperty
	public List<Usuario> findByCedula(String cedula) {
		return 	em.createQuery("from Usuario where cedula = '" + cedula + "'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@ReadOnlyProperty
	public List<Usuario> findByCorreo(String correo) {
		return 	em.createQuery("from Usuario where correo = '" + correo + "'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@ReadOnlyProperty
	@Override
	public boolean valUsuarioRepiteCedula(String cedula, Long id) {
		List<Usuario> usuarios = em.createQuery("from Usuario where cedula = '" + cedula + "' AND id <> " + id).getResultList();
		if(usuarios.size() > 0) {
			//Significa que alguien mas tiene ese mismo cedula
			return true;
		}		
		//Significa que este id es el unico que tiene este cedula
		return false;
	}	

	@SuppressWarnings("unchecked")
	@ReadOnlyProperty
	@Override
	public boolean valUsuarioRepiteCorreo(String correo, Long id) {		
		List<Usuario> usuarios = em.createQuery("from Usuario where correo = '" + correo + "' AND id <> " + id).getResultList();
		if(usuarios.size() > 0) {
			//Significa que alguien mas tiene ese mismo correo
			return true;
		}		
		//Significa que este id es el unico que tiene este correo
		return false;
	}

}
