package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beanDto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLoguin;
import model.ModelTelefone;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
	
		connection = SingleConnectionBanco.getConnection();
	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		String sql = "select avg(rendamensal) as media_salarial, perfil from modelloguin where usuario_id  = ? and datanascimento >= ? and datanascimento <= ? group by perfil";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception {
		
		String sql = "select avg(rendamensal) as media_salarial, perfil from modelloguin where usuario_id  = ? group by perfil";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil =  resultSet.getString("perfil") ;
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
	
	
	
	public ModelLoguin gravarUsuario(ModelLoguin objeto ,Long userLogado) throws Exception {
		
		if(objeto.isNovo()) {//grava um novo cadastro no banco
		
		
		String sql = "INSERT INTO modelloguin(loguin,senha,nome,email,usuario_id,perfil,sexo,cep,logradouro,bairro,localidade,uf,numero,datanascimento ,rendamensal) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ;";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setString(1, objeto.getLoguin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		preparedSql.setLong(5, userLogado);
		preparedSql.setString(6,objeto.getPerfil());
		preparedSql.setString(7, objeto.getSexo());
		preparedSql.setString(8, objeto.getCep());
		preparedSql.setString(9, objeto.getLogradouro());
		preparedSql.setString(10, objeto.getBairro());
		preparedSql.setString(11, objeto.getLocalidade());
		preparedSql.setString(12, objeto.getUf());
		preparedSql.setString(13, objeto.getNumero());
		preparedSql.setDate(14, objeto.getDataNascimento());
		preparedSql.setDouble(15, objeto.getRendamensal());
		
		preparedSql.execute();
	
		connection.commit();
		
		if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
			
			sql="update modelloguin set fotouser=?,extensaofotouser=? where loguin=?";
			preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1,objeto.getFotouser());
			preparedSql.setString(2, objeto.getExtensaofotouser());
			preparedSql.setString(3, objeto.getLoguin());
			
			preparedSql.execute();
			
			connection.commit();
		}
		
		}else {
			
			String sql = "UPDATE modelloguin SET loguin=?, senha=?, nome=?, email=?, perfil=?, sexo=? , cep=?, logradouro=?,bairro=?,localidade=?, uf=?,numero=?,datanascimento=?,rendamensal=?  WHERE id ="+objeto.getId()+";";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			preparedSql.setString(1, objeto.getLoguin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setString(5,objeto.getPerfil());
			preparedSql.setString(6, objeto.getSexo());
			preparedSql.setString(7, objeto.getCep());
			preparedSql.setString(8, objeto.getLogradouro());
			preparedSql.setString(9, objeto.getBairro());
			preparedSql.setString(10, objeto.getLocalidade());
			preparedSql.setString(11, objeto.getUf());
			preparedSql.setString(12, objeto.getNumero());
			preparedSql.setDate(13, objeto.getDataNascimento());
			preparedSql.setDouble(14, objeto.getRendamensal());
			
			
			preparedSql.executeUpdate();
		
			connection.commit();
			
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				
				sql="update modelloguin set fotouser=?,extensaofotouser=? where id=?";
				preparedSql = connection.prepareStatement(sql);
				
				preparedSql.setString(1,objeto.getFotouser());
				preparedSql.setString(2, objeto.getExtensaofotouser());
				preparedSql.setLong(3, objeto.getId());
				
				preparedSql.execute();
			
				connection.commit();
			}
			
			
		}
		return this.consultaUsuario(objeto.getLoguin(),userLogado);
		
	}
public List<ModelLoguin> consultaUsuarioListPaginada(Long userLogado , Integer offset) throws Exception{
		
		List<ModelLoguin> retorno = new ArrayList<ModelLoguin>();
		
		String sql = "select * from  modelloguin where useradmin is false and usuario_id = "+ userLogado + " order by nome offset "+ offset  +" limit 5";
		
		PreparedStatement stament = connection.prepareStatement(sql);
		
		
		ResultSet resultado = stament.executeQuery();
		
		while(resultado.next()) {//percorrer as linhas de resultado do SQL
			ModelLoguin modelLoguin = new ModelLoguin();
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			
			
			
			//modelLoguin.setSenha(sql);
			
			retorno.add(modelLoguin);
						
		}
		return retorno;
		
		} public int totalPagina(Long useLogado)throws Exception {
        	 
        	String sql = "select count(1) as total from modelloguin where usuario_id = " + useLogado;
     		PreparedStatement stament = connection.prepareStatement(sql);	     		
     		ResultSet resultado = stament.executeQuery(); 
     		resultado.next();
     		
     		Double cadastro = resultado.getDouble("total");
     		
     		Double porpagina = 5.0;
     		Double pagina = cadastro/porpagina;
     		Double resto = pagina % 2;
     		
     		if(resto > 0) {
     			pagina ++;
     		}
     		
     		return pagina.intValue();
        	 
         } public List<ModelLoguin> consultaUsuarioListRel(Long userLogado) throws Exception {
     		
     		List<ModelLoguin> retorno = new ArrayList<ModelLoguin>();
     		
     		String sql = "select * from modelloguin where useradmin is false and usuario_id = " + userLogado;
     		PreparedStatement statement = connection.prepareStatement(sql);
     		
     		ResultSet resultado = statement.executeQuery();
     		
     		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
     			
     			ModelLoguin modelLogin = new ModelLoguin();
     			
     			modelLogin.setEmail(resultado.getString("email"));
     			modelLogin.setId(resultado.getLong("id"));
     			modelLogin.setLoguin(resultado.getString("loguin"));
     			modelLogin.setNome(resultado.getString("nome"));
     			//modelLogin.setSenha(resultado.getString("senha"));
     			modelLogin.setPerfil(resultado.getString("perfil"));
     			modelLogin.setSexo(resultado.getString("sexo"));
     			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
     			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
     			
     			retorno.add(modelLogin);
     		}
     		
     		
     		return retorno;
     	}
     	
     	
     	public List<ModelLoguin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {
     		
     		List<ModelLoguin> retorno = new ArrayList<ModelLoguin>();
     		
     		String sql = "select * from modelloguin where useradmin is false and usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ?";
     		PreparedStatement statement = connection.prepareStatement(sql);
     		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
     		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
     		
     		ResultSet resultado = statement.executeQuery();
     		
     		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
     			
     			ModelLoguin modelLogin = new ModelLoguin();
     			
     			modelLogin.setEmail(resultado.getString("email"));
     			modelLogin.setId(resultado.getLong("id"));
     			modelLogin.setLoguin(resultado.getString("loguin"));
     			modelLogin.setNome(resultado.getString("nome"));
     			//modelLogin.setSenha(resultado.getString("senha"));
     			modelLogin.setPerfil(resultado.getString("perfil"));
     			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
     			modelLogin.setSexo(resultado.getString("sexo"));
     			
     			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
     			
     			retorno.add(modelLogin);
     		}
     		
     		
     		return retorno;
     	}
		
		
		
		
		
		
	
public List<ModelLoguin> consultaUsuarioList(Long userLogado) throws Exception{
		
		List<ModelLoguin> retorno = new ArrayList<ModelLoguin>();
		
		String sql = "select * from  modelloguin where useradmin is false and usuario_id = "+ userLogado + "limit 5";
		
		PreparedStatement stament = connection.prepareStatement(sql);
		
		
		ResultSet resultado = stament.executeQuery();
		
		while(resultado.next()) {//percorrer as linhas de resultado do SQL
			ModelLoguin modelLoguin = new ModelLoguin();
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			
			
			
			//modelLoguin.setSenha(sql);
			
			retorno.add(modelLoguin);
						
		}
		return retorno;
		
		}

public int consultaUsuarioListTotalPaginaPaginacao(String nome , Long userLogado) throws Exception{
	
	
	
	String sql = "select count(1) as total from  modelloguin where upper(nome) like upper(?)and useradmin is false and usuario_id = ? ";
	
	PreparedStatement stament = connection.prepareStatement(sql);
	stament.setString(1, "%"+ nome +"%");
	stament.setLong(2, userLogado );
	
	ResultSet resultado = stament.executeQuery();
	
	resultado.next();
		
		Double cadastro = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		Double pagina = cadastro/porpagina;
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
	 	
	
}public List<ModelLoguin> consultaUsuarioListOffset(String nome , Long userLogado, int offset) throws Exception{
	
	List<ModelLoguin> retorno = new ArrayList<ModelLoguin>();
	
	String sql = "select * from  modelloguin where upper(nome) like upper(?)and useradmin is false and usuario_id = ? offset "+ offset + "  limit 5";
	
	PreparedStatement stament = connection.prepareStatement(sql);
	stament.setString(1, "%"+ nome +"%");
	stament.setLong(2, userLogado );
	
	
	ResultSet resultado = stament.executeQuery();
	
	while(resultado.next()) {//percorrer as linhas de resultado do SQL
		ModelLoguin modelLoguin = new ModelLoguin();
		modelLoguin.setLoguin(resultado.getString("loguin"));
		modelLoguin.setId(resultado.getLong("id"));
		modelLoguin.setNome(resultado.getString("nome"));
		modelLoguin.setEmail(resultado.getString("email"));
		modelLoguin.setPerfil(resultado.getString("perfil"));
		modelLoguin.setSexo(resultado.getString("sexo"));
		
		
		//modelLoguin.setSenha(sql);
		
		retorno.add(modelLoguin);
				
		
	}
	
	return retorno;
	
}




		
	
	
	public List<ModelLoguin> consultaUsuarioList(String nome , Long userLogado) throws Exception{
		
		List<ModelLoguin> retorno = new ArrayList<ModelLoguin>();
		
		String sql = "select * from  modelloguin where upper(nome) like upper(?)and useradmin is false and usuario_id = ? limit 5";
		
		PreparedStatement stament = connection.prepareStatement(sql);
		stament.setString(1, "%"+ nome +"%");
		stament.setLong(2, userLogado );
		
		ResultSet resultado = stament.executeQuery();
		
		while(resultado.next()) {//percorrer as linhas de resultado do SQL
			ModelLoguin modelLoguin = new ModelLoguin();
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			
			
			//modelLoguin.setSenha(sql);
			
			retorno.add(modelLoguin);
					
			
		}
		
		return retorno;
		
	}
public ModelLoguin consultaUsuarioLogado(String loguin ) throws Exception {
		
		ModelLoguin modelLoguin = new ModelLoguin();
		String consulta = "SELECT * FROM modelloguin where upper(loguin) = upper('"+loguin+"')";
		
		PreparedStatement stament = connection.prepareStatement(consulta);
		
		ResultSet resultado = stament.executeQuery();
		
		while (resultado.next()) {
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setSenha(resultado.getString("senha"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setUserAdmin(resultado.getBoolean("useradmin"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			modelLoguin.setFotouser(resultado.getString("fotouser"));
			modelLoguin.setCep(resultado.getString("cep"));
			modelLoguin.setLogradouro(resultado.getString("logradouro"));
			modelLoguin.setBairro(resultado.getString("bairro"));
			modelLoguin.setLocalidade(resultado.getString("localidade"));
			modelLoguin.setUf(resultado.getString("uf"));
			modelLoguin.setNumero(resultado.getString("numero"));
			modelLoguin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLoguin.setRendamensal(resultado.getDouble("rendamensal"));
			
			
			
		}
		
		return modelLoguin;
		
		
	}
	
	
	
	
	
public ModelLoguin consultaUsuario(String loguin ) throws Exception {
		
		ModelLoguin modelLoguin = new ModelLoguin();
		String consulta = "SELECT * FROM modelloguin where upper(loguin) = upper('"+loguin+"') and useradmin is false ";
		
		PreparedStatement stament = connection.prepareStatement(consulta);
		
		ResultSet resultado = stament.executeQuery();
		
		while (resultado.next()) {
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setSenha(resultado.getString("senha"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setUserAdmin(resultado.getBoolean("useradmin"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			modelLoguin.setFotouser(resultado.getString("fotouser"));
			modelLoguin.setCep(resultado.getString("cep"));
			modelLoguin.setLogradouro(resultado.getString("logradouro"));
			modelLoguin.setBairro(resultado.getString("bairro"));
			modelLoguin.setLocalidade(resultado.getString("localidade"));
			modelLoguin.setUf(resultado.getString("uf"));
			modelLoguin.setNumero(resultado.getString("numero"));
			modelLoguin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLoguin.setRendamensal(resultado.getDouble("rendamensal"));
			
			
		}
		
		return modelLoguin;
		
		
	}
	
	
	public ModelLoguin consultaUsuario(String loguin , Long userLogado) throws Exception {
		
		ModelLoguin modelLoguin = new ModelLoguin();
		String consulta = "SELECT * FROM modelloguin where upper(loguin) = upper('"+loguin+"') and useradmin is false and usuario_id=" + userLogado;
		
		PreparedStatement stament = connection.prepareStatement(consulta);
		
		ResultSet resultado = stament.executeQuery();
		
		while (resultado.next()) {
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setSenha(resultado.getString("senha"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			modelLoguin.setFotouser(resultado.getString("fotouser"));
			modelLoguin.setCep(resultado.getString("cep"));
			modelLoguin.setLogradouro(resultado.getString("logradouro"));
			modelLoguin.setBairro(resultado.getString("bairro"));
			modelLoguin.setLocalidade(resultado.getString("localidade"));
			modelLoguin.setUf(resultado.getString("uf"));
			modelLoguin.setNumero(resultado.getString("numero"));
			modelLoguin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLoguin.setRendamensal(resultado.getDouble("rendamensal"));
			
			
		}
		
		return modelLoguin;
		
		
	}
	
public ModelLoguin consultarUsuarioID(Long id ) throws Exception {
		
		ModelLoguin modelLoguin = new ModelLoguin();
		String consulta = "SELECT * FROM modelloguin where id = ? and useradmin is false";
		
		PreparedStatement stament = connection.prepareStatement(consulta);
		stament.setLong(1, id);
		
		
		ResultSet resultado = stament.executeQuery();
		
		while (resultado.next()) {
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setSenha(resultado.getString("senha"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			modelLoguin.setFotouser(resultado.getString("fotouser"));
			modelLoguin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLoguin.setCep(resultado.getString("cep"));
			modelLoguin.setLogradouro(resultado.getString("logradouro"));
			modelLoguin.setBairro(resultado.getString("bairro"));
			modelLoguin.setLocalidade(resultado.getString("localidade"));
			modelLoguin.setUf(resultado.getString("uf"));
			modelLoguin.setNumero(resultado.getString("numero"));
			modelLoguin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLoguin.setRendamensal(resultado.getDouble("rendamensal"));
			
			
		}
		
		return modelLoguin;
		
		
	}
	
	
	
public ModelLoguin consultaUsuarioID(String id , Long userLogado) throws Exception {
		
		ModelLoguin modelLoguin = new ModelLoguin();
		String consulta = "SELECT * FROM modelloguin where id = ? and useradmin is false and usuario_id =?";
		
		PreparedStatement stament = connection.prepareStatement(consulta);
		stament.setLong(1, Long.parseLong(id));
		stament.setLong(2, userLogado);
		
		ResultSet resultado = stament.executeQuery();
		
		while (resultado.next()) {
			modelLoguin.setId(resultado.getLong("id"));
			modelLoguin.setEmail(resultado.getString("email"));
			modelLoguin.setLoguin(resultado.getString("loguin"));
			modelLoguin.setSenha(resultado.getString("senha"));
			modelLoguin.setNome(resultado.getString("nome"));
			modelLoguin.setPerfil(resultado.getString("perfil"));
			modelLoguin.setSexo(resultado.getString("sexo"));
			modelLoguin.setFotouser(resultado.getString("fotouser"));
			modelLoguin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLoguin.setCep(resultado.getString("cep"));
			modelLoguin.setLogradouro(resultado.getString("logradouro"));
			modelLoguin.setBairro(resultado.getString("bairro"));
			modelLoguin.setLocalidade(resultado.getString("localidade"));
			modelLoguin.setUf(resultado.getString("uf"));
			modelLoguin.setNumero(resultado.getString("numero"));
			modelLoguin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLoguin.setRendamensal(resultado.getDouble("rendamensal"));
			
			
		}
		
		return modelLoguin;
		
		
	}
	
	
	
	
	
	
	public boolean validaLoguin(String loguin)throws Exception {
		String sql = "select count(1)>0  as existe from modelloguin where upper(loguin) = upper('"+loguin+"');";
		
        PreparedStatement stament = connection.prepareStatement(sql);
		
		ResultSet resultado = stament.executeQuery();
		
		   resultado.next();/*pra ele entrar nos resultados do sql*/
			return resultado.getBoolean("existe");
		
	}
	
	public void deletarUser(String idUser) throws Exception {
		
		String sql =  "DELETE FROM modelloguin WHERE id = ? and useradmin is false ;";
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setLong(1, Long.parseLong(idUser));
		
		prepareSql.executeUpdate();
		
		connection.commit();
	}
public List<ModelTelefone> listFone(Long idUserPai) throws Exception {
		
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUserPai);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		while (rs.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultarUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultarUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
			
		}
		
		return retorno;
	}
	

}
