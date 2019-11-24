package paquete.spring.entity;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StoreProcedureDAO {
	
	@Autowired
	EntityManager entityManager;
	
	public String callProcedure(String rut) {
		
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("EXTRAERDATES_BY_RUT");

	    storedProcedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
	    storedProcedureQuery.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

	    storedProcedureQuery.setParameter(1, rut);

	    String outputValue2 = (String) storedProcedureQuery.getOutputParameterValue(2);
	    
	    return outputValue2;
	}
}