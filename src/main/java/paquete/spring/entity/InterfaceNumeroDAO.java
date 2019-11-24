package paquete.spring.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterfaceNumeroDAO extends JpaRepository<Numero, Integer> {
	
	@Query(nativeQuery = true, value = "SELECT FUNCTION_PRUEBA(:var1, :var2) FROM dual")
    int callHelloWorld(@Param("var1") int x, @Param("var2") int y);
}