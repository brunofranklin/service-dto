package br.com.senai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.senai.exception.EmailException;
import br.com.senai.model.Usuario;
import br.com.senai.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	}

	public Usuario inserir (Usuario usuario) {
		Usuario user = usuarioRepository.findByEmail(usuario.getEmail());
		if(user!= null) {
			throw new EmailException("Esse e-mail já está cadastrado");
		}
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
}
