package paquete.spring.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Numero implements Serializable {
	
	private static final long serialVersionUID = -3363800667371284799L;
	
	@Id
	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Numero [result=" + result + "]";
	}
}