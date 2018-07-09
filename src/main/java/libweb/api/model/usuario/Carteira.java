package libweb.api.model.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carteira")
public class Carteira {
	@Id
	@Column(name = "id_carteira")
	private Long id;
	
	@Column(name = "saldo")
	private double saldo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}
