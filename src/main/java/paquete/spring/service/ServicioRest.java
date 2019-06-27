package paquete.spring.service;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import paquete.spring.entity.InterfacePersonasDAO;
import paquete.spring.entity.Persona;

@Component
@Transactional
//@Api(tags="gestion-personas")
@Path("/clase-service")
public class ServicioRest {
	
	@Autowired
	InterfacePersonasDAO dao;

	//@ApiOperation(value="Retorna Fecha Actual")
	//@ApiResponses( value = {
	//			@ApiResponse(code=201, message="Aceptado"),
	//			@ApiResponse(code=432, message="Inventado"),
	//		})
	@GET
	@Path("/timenow")
	@Produces({"application/json"})
	public Map<String, Object> getStatus() {
		
		Date fecha = new Date ();
		Locale currentLocale = new Locale("EN");
		DateFormat formato = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, currentLocale);
		String output = formato.format(fecha);
		
		Map<String, Object> collection = new HashMap<String, Object>();
		collection.put("GetStatus", output);

         	return collection;
	}

	@GET
	@Path("/imprime-header")
	@Produces({"application/json"})
	public Map<String, Object> getHeader(@Context HttpHeaders headers) {
		
		Map<String, Object> collection = new HashMap<String, Object>();
		
		collection.put("Header", headers.getVary());

         	return collection;
	}
	/*
	@GetMapping(value="/httpentity", produces="application/json")
	public ResponseEntity<Map<String, String>> getEntity() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("value1", "Hola");
		headers.add("value2", "Mundo");
		
		Map<String, String> collection = new HashMap<String, String>();
		collection.put("Respuesta", "Respuesta desde metodo GET http-entity");

         	return ResponseEntity.status(HttpStatus.OK).headers(headers).body(collection);
	}

	@GetMapping(path="/personas", produces="application/json")
	public Map<String, List<Persona>> extraerAll() {
		
		Map<String, List<Persona>> collection = new HashMap<String, List<Persona>>();
	
		List<Persona> lista = dao.findAll();
		collection.put("Data", lista);

		return collection;
		
	}
	*/
	
	/*
	@GetMapping(path="/personas-procedure/{arg}", produces="application/json")
	public Map<String, Integer> extraerPorRut(@PathVariable int arg) {
		
		Map<String, Integer> collection = new HashMap<String, Integer>();
	
		int lista = dao.callProcedure(arg);
		collection.put("Data", lista);

		return collection;
	}
	*/
	
	/*
	@GetMapping(path="/personas-desc", produces="application/json")
	public List<Persona> extraerAllDesc() {
	
		List<Persona> lista = dao.findAllOrderDesc();

		return lista;
	}
	
	@GetMapping(path="/personas-asc", produces="application/json")
	public List<Persona> extraerAllAsc() {
	
		List<Persona> lista = dao.findAllOrderAsc();

		return lista;
	}
	
	@GetMapping(path="/contar-id", produces="application/json")
	public Map<String, Long> contarById() {
	
		long num = dao.count();
		
		Map<String, Long> collection = new HashMap<String, Long>();
		collection.put("Cantidad de entidades", num);

     	return collection;
	}
	
	@GetMapping(path="/contar-name/{name}", produces="application/json")
	public Map<String, Long> contarByName(@PathVariable String name) {
	
		long num = dao.countByNombre(name);
		
		Map<String, Long> collection = new HashMap<String, Long>();
		collection.put("Cantidad de entidades", num);

     	return collection;
	}
	
	@GetMapping(path="/personas-name/{name}", produces="application/json")
	public Map<String, List<Persona>> extraerPorName(@PathVariable String name) {
	
		List<Persona> per = dao.findByName(name);
		
		Map<String, List<Persona>> collection = new HashMap<String, List<Persona>>();
		collection.put("Data", per);

			return collection;
	}
	
	@GetMapping(path="/personas-id/{id}", produces="application/json")
	public Map<String, Persona> extraerPorId(@PathVariable long id) {
	
		Optional<Persona> per = dao.findById(id);
		
		Map<String, Persona> collection = new HashMap<String, Persona>();
		collection.put("Data", per.get());

			return collection;
	}
	
	@GetMapping(path="/personas-id", produces="application/json")
	public Map<String, Persona> extraerPorIdParam(@RequestParam long id) {
	
		Optional<Persona> per = dao.findById(id);
		
		Map<String, Persona> collection = new HashMap<String, Persona>();
		collection.put("Data", per.get());

			return collection;
	}
	
	@PostMapping(path="/personas", consumes="application/json", produces="application/json")
	public Map<String, String> ingresar(@RequestBody Persona per) {
		
		if (per.getPersonaId()==0) {
			long count = dao.count();  // Tener en cuenta que si existe un registro con id = 0, no se deberia incrementar en uno, debido a que arrojaria uno de mas porque cuenta el registro 0.
			long id = count + 1;
			per.setPersonaId(id);
		}
		
		Map<String, String> collection = new HashMap<String, String>();
		dao.save(per);
		collection.put("Message", "Entidad introducida correctamente");
				
		return collection;
	}
	
	@PutMapping(path="/personas", consumes="application/json", produces="application/json")
	public Map<String, String> modificar(@Valid @RequestBody Persona per) {  // NOTA: No se puede modificar el ID de la persona, el resto de atributos si.
		
		Map<String, String> collection = new HashMap<String, String>();

		if(per.getPersonaId()==0){
			collection.put("Message", "Debe ingresar un ID a modificar");
		} else {
			dao.save(per);
			collection.put("Message", "Entidad modificada correctamente");
		}		
		return collection;
	}
	
	@DeleteMapping(path="/personas", produces="application/json")
	public Map<String,String> eliminar(@RequestBody Persona per) {
		
		dao.delete(per);
		Map<String,String> col = new HashMap<String,String>();
		col.put("Respuesta", "Entidad eliminada correctamente");
		return col;
	}
	
	@DeleteMapping(path="/personas-apellido/{apellido}", produces="application/json")
	public Map<String,String> eliminarByApellido(@PathVariable String apellido) {
		
		dao.deleteByApellido(apellido);
		Map<String,String> col = new HashMap<String,String>();
		col.put("Respuesta", "Entidad eliminada correctamente");
		return col;
	}
	
	@DeleteMapping(path="/personas-id/{id}", produces="application/json")
	public Map<String,String> eliminarById(@PathVariable long id) {
	
		dao.deleteById(id);
		Map<String,String> col = new HashMap<String,String>();
		col.put("Respuesta", "Entidad eliminada correctamente");
		return col;
	}
	*/
}
