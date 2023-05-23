package servelet;

import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLoguin;
import model.ModelTelefone;


@WebServlet("/ServletTelefone")
public class ServletTelefone extends  ServletGenericUtil {
	private static final long serialVersionUID = 1L;
    private DAOUsuarioRepository daoUsuary = new DAOUsuarioRepository(); 
    private DAOTelefoneRepository daotelefone = new DAOTelefoneRepository();
   
    public ServletTelefone() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
					
			String acao = request.getParameter("acao");
			
			if (acao != null && !acao.isEmpty() && acao.equals("excluir")) {
				
				String idfone = request.getParameter("id");
				
				daotelefone.deleteFone(Long.parseLong(idfone));
				
				String userpai = request.getParameter("userpai");
				
				ModelLoguin modelLogin = daoUsuary.consultarUsuarioID(Long.parseLong(userpai));
				
				List<ModelTelefone> modelTelefones = daotelefone.listFone(modelLogin.getId());
				request.setAttribute("modelTelefone", modelTelefones);
				
				request.setAttribute("msg", "Telefone Excluido");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
				return;
			}	
			
		
	    String idu = request.getParameter("iduser");
	    
	    if(idu !=null && !idu.isEmpty()) {
	    
	    
			ModelLoguin modelLoguin = daoUsuary.consultarUsuarioID(Long.parseLong(idu));
			List<ModelTelefone> modeltelefone =daotelefone.listFone(modelLoguin.getId());
			request.setAttribute("modelTelefone", modeltelefone);
			
			request.setAttribute("modelLoguin", modelLoguin);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
		
	    }else {
	    	
	    	List<ModelLoguin> modelLoguin = daoUsuary.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLoguin", modelLoguin);
			request.setAttribute("totalPagina", daoUsuary.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
	    	
	    }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");
			
			if (!daotelefone.existeFone(numero, Long.valueOf(usuario_pai_id))) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setNumero(numero);
			modelTelefone.setUsuario_pai_id(daoUsuary.consultarUsuarioID(Long.parseLong(usuario_pai_id)));
			modelTelefone.setUsuario_cad_id(super.getUserLogadoObj(request));
			
			daotelefone.gravaTelefone(modelTelefone);
			
	        request.setAttribute("msg", "Salvo com sucesso");
			
			}else {
				request.setAttribute("msg", "Telefone já existe");
			}
			
			List<ModelTelefone> modelTelefones = daotelefone.listFone(Long.parseLong(usuario_pai_id));
			
			ModelLoguin modelLogin = daoUsuary.consultarUsuarioID(Long.parseLong(usuario_pai_id));
			
			request.setAttribute("modelLoguin", modelLogin);
			request.setAttribute("modelTelefone", modelTelefones);
			request.setAttribute("msg", "Salvo com sucesso");
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
		 
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
