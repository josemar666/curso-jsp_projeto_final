package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	private static String banco = "jdbc:postgresql://localhost:5432/curso_jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "4546vitoria";
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {//de qualquer lugar que chamar a classe vai conectar
		conectar();
	}
	
	
	public SingleConnectionBanco() {
		conectar();//quando tiver uma instância vai conectar
	}
	
	private static void conectar() {
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver");//carrega o drive de conexão do banco
				connection = DriverManager.getConnection(banco,user,senha);
				connection.setAutoCommit(false);//para não efetuar alterações no banco sem nosso comando
			}
			
		}catch(Exception e) {
			e.printStackTrace();//mostrar qualquer erro no momento de conectar
		}
	}

}
