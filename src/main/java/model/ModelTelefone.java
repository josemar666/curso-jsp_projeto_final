package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelTelefone implements Serializable {

private static final long serialVersionUID = 1L;

private Long id;
private String numero;
private ModelLoguin usuario_pai_id;
private ModelLoguin usuario_cad_id;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNumero() {
	return numero;
}
public void setNumero(String numero) {
	this.numero = numero;
}
public ModelLoguin getUsuario_pai_id() {
	return usuario_pai_id;
}
public void setUsuario_pai_id(ModelLoguin usuario_pai_id) {
	this.usuario_pai_id = usuario_pai_id;
}
public ModelLoguin getUsuario_cad_id() {
	return usuario_cad_id;
}
public void setUsuario_cad_id(ModelLoguin usuario_cad_id) {
	this.usuario_cad_id = usuario_cad_id;
}
@Override
public int hashCode() {
	return Objects.hash(id);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ModelTelefone other = (ModelTelefone) obj;
	return Objects.equals(id, other.id);
}


}
