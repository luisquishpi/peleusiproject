package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "usuario")

public class Usuario {
	public Usuario(){
		
	}
	
	public Usuario(String nombres, String apellidos, String usuario, String contrasenia, String tipousuario){
		super();
		this.id=null;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.tipousuario= tipousuario;
		
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column (name = "nombres", nullable = false)
	private String nombres;
	
	@Column (name = "apellidos", nullable = false)
	private String apellidos;
	
	@Column (name = "usuario", nullable = false)
	private String usuario;
	
	@Column (name = "contrasenia", nullable = false)
	private String contrasenia;
	
	@Column (name = "tipousuario", nullable = false)
	private String tipousuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getTipousuario() {
		return tipousuario;
	}

	public void setTipousuario(String tipousuario) {
		this.tipousuario = tipousuario;
	}
	
	
	@Override
	public String toString(){
		return "[id= " + id + ", nombres " +nombres+ "]";
	}
	

}

