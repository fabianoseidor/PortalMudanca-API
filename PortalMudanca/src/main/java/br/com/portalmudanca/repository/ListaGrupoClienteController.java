package br.com.portalmudanca.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.portalmudanca.ExceptionCustomizada;
import br.com.portalmudanca.model.ListaGrupoCliente;

@RestController
public class ListaGrupoClienteController {

	@Autowired
	private ListaGrupoClienteRepository listaGrupoClienteRepository;
	
	
	@ResponseBody // Retorno da API 
	@PostMapping("**/salvarListaGrupoCliente") // Mepeando a url para receber um JSON
	public ResponseEntity<List<ListaGrupoCliente>> salvarListaGrupoCliente( @RequestBody List<ListaGrupoCliente> listaGrupoCliente ) throws ExceptionCustomizada {// @RequestBody ==> Recebe um JSON e converte para Objeto. 
		if(listaGrupoCliente == null) {
			throw new ExceptionCustomizada("ERRO ao tentar cadastrar o 'Grup oCliente'. Valores vazios!");
		}
		List<ListaGrupoCliente> ListaGC = new ArrayList<ListaGrupoCliente>();
		for (ListaGrupoCliente listaGrupoCli : listaGrupoCliente) {
			ListaGrupoCliente lista = listaGrupoClienteRepository.save(listaGrupoCli);
			ListaGC.add(lista);
		}
		return new ResponseEntity<List<ListaGrupoCliente>>( ListaGC, HttpStatus.OK );
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteListaGrupoCliente/{id}")
	public ResponseEntity<String> deleteListaGrupoCliente( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		listaGrupoClienteRepository.deleteListaGrupoCliente(id);
		return new ResponseEntity<String>("Lista do Grupo Cliente removido com sucesso!", HttpStatus.OK);		
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteListaGrupoClienteById/{id}")
	public ResponseEntity<String> deleteListaGrupoClienteById( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		listaGrupoClienteRepository.deleteById(id);
		return new ResponseEntity<String>("Cliente removido do Grugo com sucesso!", HttpStatus.OK);		
	}

		
	@ResponseBody
	@GetMapping(value = "**/listaGrupoClientePorGrupo/{id}")
	public ResponseEntity<List<ListaGrupoCliente>> listaGrupoClientePorGrupo( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		List<ListaGrupoCliente> listaGrupoCliente = listaGrupoClienteRepository.ListaGrupoClientePorGrupo(id);
		return new ResponseEntity<List<ListaGrupoCliente>>(listaGrupoCliente, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/isExitsClienteGrupo/{idGrupo}/{idCliente}")
	public ResponseEntity<Boolean> isExitsClienteGrupo( @PathVariable("idGrupo") Long idGrupo, @PathVariable("idCliente") Long idCliente ) throws ExceptionCustomizada{
		Integer result = listaGrupoClienteRepository.isExitsClienteGrupo(idGrupo, idCliente);
		
//		String isExits = result > 0 ? "EXISTE":"OK";
		
		Boolean isExits1 = result > 0 ? true: false;
		
//		return new ResponseEntity<String>(isExits, HttpStatus.OK);
		return new ResponseEntity<Boolean>(isExits1, HttpStatus.OK);
		
	}

	@ResponseBody
	@GetMapping(value = "**/listaGrupoClientePorCliente/{id}")
	public ResponseEntity<List<ListaGrupoCliente>> listaGrupoClientePorCliente( @PathVariable("id") Long id ) throws ExceptionCustomizada{
		List<ListaGrupoCliente> listaGrupoCliente = listaGrupoClienteRepository.ListaGrupoClientePorCliente(id);
		return new ResponseEntity<List<ListaGrupoCliente>>(listaGrupoCliente, HttpStatus.OK);
	}
	
}
