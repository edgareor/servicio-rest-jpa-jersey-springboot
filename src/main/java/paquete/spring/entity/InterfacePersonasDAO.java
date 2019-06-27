package paquete.spring.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfacePersonasDAO extends JpaRepository<Persona, Long> {
	
	List<Persona> findAllOrderDesc();
	
	List<Persona> findAllOrderAsc();
	
	@Query("SELECT p FROM Persona p WHERE p.nombre = :nombre")
	List<Persona> findByName(String nombre);
	
	long countByNombre(String name);
	
	void deleteByApellido(String apellido);
	
	//@Procedure(procedureName="plusinout")
	//Integer callProcedure(@Param("arg") Integer arg);
}