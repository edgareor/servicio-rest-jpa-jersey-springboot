package paquete.spring.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="PERSONAS")
@NamedQueries({
	@NamedQuery(name="Persona.findAllOrderDesc", query="SELECT p FROM Persona p ORDER BY p.personaId DESC"),
	@NamedQuery(name="Persona.findAllOrderAsc", query="SELECT p FROM Persona p ORDER BY p.personaId ASC")
})
//@NamedStoredProcedureQuery(name = "Persona.plus1", procedureName = "plusinout", parameters = {
	//	  @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
		//  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class) })
public class Persona implements Serializable {

	private static final long serialVersionUID = 1733449340474000687L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PERSONA_ID")
	private long personaId;

	@Column(name = "APELLIDO")
	private String apellido;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "RUT")
	private String rut;

	public Persona() {
	}

	public long getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(long personaId) {
		this.personaId = personaId;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	@Override
	public String toString() {
		return "Persona [personaId=" + personaId + ", apellido=" + apellido + ", nombre=" + nombre + ", rut=" + rut
				+ "]";
	}

}