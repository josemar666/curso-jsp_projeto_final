package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLoguin;

public class DAOLoquinRepository {
	
	private Connection connection;

	public DAOLoquinRepository() {
		connection = SingleConnectionBanco.getConnection();
		
	}
	public boolean validarAutenticacao(ModelLoguin modelLoguin) throws Exception {
		String sql = "select * from ModelLoguin where upper(loguin) = upper(?) and upper(senha) =upper(?)";
		
		PreparedStatement stament = connection.prepareStatement(sql);
		stament.setString(1, modelLoguin.getLoguin());
		stament.setString(2, modelLoguin.getSenha());
		
		ResultSet resultSet = stament.executeQuery();
		
		if(resultSet.next()) {
			return true;// autenticado
		}
		
		return false; //não autenticado
		
	}
	
	

}
