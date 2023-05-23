package servelet;

import java.io.IOException;

import dao.DAOLoquinRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLoguin;

@WebServlet(urlPatterns = { "/principal/ServeletLoguin", "/ServeletLoguin" }) /* mapeamento da URL que vem da tela */
public class ServeletLoguin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOLoquinRepository daoLoguinRepository = new DAOLoquinRepository();
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServeletLoguin() {

	}

	/* recebe os dados pela URL em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			
			request.getSession().invalidate();//invalida a sessão
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}else {
		
		doPost(request, response);

		}

	}

	/* recebe os dados enviados por um formulário */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loguin = request.getParameter("loguin");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {

			if (loguin != null && !loguin.isEmpty() && senha != null && !senha.isEmpty()) {
				
				
				ModelLoguin modeloLoguin = new ModelLoguin();
                modeloLoguin.setLoguin(loguin);
				modeloLoguin.setSenha(senha);
			
				if (daoLoguinRepository.validarAutenticacao(modeloLoguin)) {
					
					modeloLoguin = daoUsuarioRepository.consultaUsuarioLogado(loguin);
					/* simulando loguin */
					request.getSession().setAttribute("usuario", modeloLoguin.getLoguin());
					request.getSession().setAttribute("perfil", modeloLoguin.getPerfil());
					request.getSession().setAttribute("imagemUser",modeloLoguin.getFotouser());

					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
				} else {

					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "informe o loguin e senha corretamente !");
					redirecionar.forward(request, response);

				}

			} else {

				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "informe o loguin e senha corretamente !");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
