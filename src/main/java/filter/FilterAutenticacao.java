package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) /* intercepta todas as requisições que vierem do projeto ou mapeamentos */
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {

	}

	/*
	 * encerra os processos quando o servidor é parado - mataria os processos de
	 * conexão com o banco
	 */
	public void destroy() {
		try {
			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/*
	 * intercepta as requisições e as respostas no sitema - tudo que for feito no
	 * sistema vai passar por ele
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlparaAutenticar = req.getServletPath();// url que esta sendo acessada
			/* validar se estar logado senão direcionar para tela de loquin */

			if (usuarioLogado == null && !urlparaAutenticar.equalsIgnoreCase("/principal/ServeletLoguin")) {// não está
																											// logado

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlparaAutenticar);
				request.setAttribute("msg", "por favor realize o loguin !");
				redireciona.forward(request, response);
				return;/* Parar a execução e redirecionar para o loguin */
			} else {
				chain.doFilter(request, response);// deixa o processo do software continuar
			}
			connection.commit();// deu tudo certo entaão commmita alterações no banco

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}

	}

	/* é executado quando incia o sistemas - servidor sobe o projeto */
	public void init(FilterConfig fConfig) throws ServletException {

		connection = SingleConnectionBanco.getConnection();
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
		File[] filesSql = new File(caminhoPastaSQL).listFiles();

		try {
			for (File file : filesSql) {

				boolean arquivoJarodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				if (!arquivoJarodado) {
					FileInputStream entradaArquivo = new FileInputStream(file);

					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");

					StringBuilder sql = new StringBuilder();

					while (lerArquivo.hasNext()) {

						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}

					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());

					connection.commit();
					lerArquivo.close();
				}
			}

		} catch (Exception e) {

			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();

		}

	}

}
