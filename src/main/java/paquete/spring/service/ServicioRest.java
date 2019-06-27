package paquete.spring.service;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.*;
import paquete.spring.entity.InterfacePersonasDAO;
import paquete.spring.entity.Persona;

@Component
@Transactional

@Path("/clase-service")
public class ServicioRest {
	
	@Autowired
	InterfacePersonasDAO dao;

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
		
		// String var = headers.getHeaderString("message");
		
		collection.put("Headers:", headers.getRequestHeaders().toString());	// Se usa el metodo .getRequestHeaders() para extraer los headers, y el toString() para que los transforme a un String y que cada header no sea un objeto y le coloque un [] a cada valor de key. 

         	return collection;
	}
	
	@GET
	@Path("/response-header")
	@Produces({"application/json"})
	public Response responseHeaders() {

		Map<String, String> collection = new HashMap<String, String>();
		collection.put("Response", "Respuesta desde metodo GET que envie un Header de respuesta");
		
			//Response.status(Response.Status.ACCEPTED).header("Message", "Response Header").entity(collection).build();

         	return Response.status(202).header("Message", "Response Header").entity(collection).build();
	}
	
	@GET
	@Path("/personas")
	@Produces({"application/json"})
	public Map<String, List<Persona>> extraerAll() {
		
		Map<String, List<Persona>> collection = new HashMap<String, List<Persona>>();
	
		List<Persona> lista = dao.findAll();
		collection.put("Data", lista);

		return collection;
		
	}
	
	/*
	@GetMapping(path="/personas-procedure/{arg}", produces="application/json")
	public Map<String, Integer> extraerPorRut(@PathVariable int arg) {
		
		Map<String, Integer> collection = new HashMap<String, Integer>();
	
		int lista = dao.callProcedure(arg);
		collection.put("Data", lista);

		return collection;
	}
	*/
	
	@GET
	@Path("/personas-desc")
	@Produces({"application/json"})
	public List<Persona> extraerAllDesc() {
	
		List<Persona> lista = dao.findAllOrderDesc();

		return lista;
	}

	@GET
	@Path("/personas-asc")
	@Produces({"application/json"})
	public List<Persona> extraerAllAsc() {
	
		List<Persona> lista = dao.findAllOrderAsc();

		return lista;
	}
	
	@GET
	@Path("/contar-id")
	@Produces({"application/json"})
	public Map<String, Long> contarById() {
	
		long num = dao.count();
		
		Map<String, Long> collection = new HashMap<String, Long>();
		collection.put("Cantidad de entidades", num);

     	return collection;
	}
	
	@GET
	@Path("/contar-name/{name}")
	@Produces({"application/json"})
	public Map<String, Long> contarByName(@PathParam("name") String name) {
	
		long num = dao.countByNombre(name);
		
		Map<String, Long> collection = new HashMap<String, Long>();
		collection.put("Cantidad de entidades", num);

     	return collection;
	}
	
	@GET
	@Path("/personas-name/{name}")
	@Produces({"application/json"})
	public Map<String, List<Persona>> extraerPorName(@PathParam("name") String name) {
	
		List<Persona> per = dao.findByName(name);
		
		Map<String, List<Persona>> collection = new HashMap<String, List<Persona>>();
		collection.put("Data", per);

			return collection;
	}
	
	@GET
	@Path("/personas-id/{id}")
	@Produces({"application/json"})
	public Map<String, Persona> extraerPorId(@PathParam("id") long id) {
	
		Optional<Persona> per = dao.findById(id);
		
		Map<String, Persona> collection = new HashMap<String, Persona>();
		collection.put("Data", per.get());

			return collection;
	}
	
	@GET
	@Path("/personas-id")
	@Produces({"application/json"})
	public Map<String, Persona> extraerPorIdParam(@QueryParam("id") long id) {
	
		Optional<Persona> per = dao.findById(id);
		
		Map<String, Persona> collection = new HashMap<String, Persona>();
		collection.put("Data", per.get());

			return collection;
	}
	
	@POST
	@Path("/personas")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Map<String, String> ingresar(Persona per) {
		
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
	
	@PUT
	@Path("/personas")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Map<String, String> modificar(Persona per) {  // NOTA: No se puede modificar el ID de la persona, el resto de atributos si.
		
		Map<String, String> collection = new HashMap<String, String>();

		if(per.getPersonaId()==0){
			collection.put("Message", "Debe ingresar un ID a modificar");
		} else {
			dao.save(per);
			collection.put("Message", "Entidad modificada correctamente");
		}		
		return collection;
	}
	
	@DELETE
	@Path("/personas")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Map<String,String> eliminar(Persona per) {
		
		dao.delete(per);
		Map<String,String> col = new HashMap<String,String>();
		col.put("Respuesta", "Entidad eliminada correctamente");
		return col;
	}
	
	@DELETE
	@Path("/personas-apellido/{apellido}")
	@Produces({"application/json"})
	public Map<String,String> eliminarByApellido(@PathParam("apellido") String apellido) {
		
		dao.deleteByApellido(apellido);
		Map<String,String> col = new HashMap<String,String>();
		col.put("Respuesta", "Entidad eliminada correctamente");
		return col;
	}
	
	@DELETE
	@Path("/personas-id/{id}")
	@Produces({"application/json"})
	public Map<String,String> eliminarById(@PathParam("id") long id) {
	
		dao.deleteById(id);
		Map<String,String> col = new HashMap<String,String>();
		col.put("Respuesta", "Entidad eliminada correctamente");
		return col;
	}

}
