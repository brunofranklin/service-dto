package br.com.senai.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senai.exception.EmailException;
import br.com.senai.model.Usuario;
import br.com.senai.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar(){
		List<Usuario> usuarios = usuarioService.listar();
		return ResponseEntity.ok(usuarios);
	}
	@PostMapping
	public ResponseEntity<Object> inserir(@RequestBody Usuario usuario){
		try {
			usuario = usuarioService.inserir(usuario);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(usuario.getId()).toUri();
			return ResponseEntity.created(uri).body(usuario);
		} catch (EmailException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
	}
}
