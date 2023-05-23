package servelet;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.compress.utils.IOUtils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import beanDto.BeanDtoGraficoSalarioUser;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLoguin;
import util.ReportUtil;

/**
 * Servlet implementation class ServletUsuarioController
 */

@MultipartConfig

@WebServlet(urlPatterns = { "/ServletUsuarioController" })
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// usado para consultar e deletar dados do formulário para o banco de dados.

		try {
			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);

				List<ModelLoguin> modelLoguins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLoguins", modelLoguins);
				request.setAttribute("msg", "excluído com sucesso !");
				
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);

				response.getWriter().write("EXCLUÍDO COM SUCESSO !!!");
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelLoguin> dadosJasonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca , super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJasonUser);
				response.addHeader("totalPagina",""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("Pagina");

				List<ModelLoguin> dadosJasonUser = daoUsuarioRepository.consultaUsuarioListOffset(nomeBusca , super.getUserLogado(request),Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJasonUser);
				response.addHeader("totalPagina",""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
				
			}
						
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String id = request.getParameter("id");

				ModelLoguin modelLoguin = daoUsuarioRepository.consultaUsuarioID(id , super.getUserLogado(request));
				List<ModelLoguin> modelLoguins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLoguins", modelLoguins);

				request.setAttribute("msg", "Usuario em edição !");
				request.setAttribute("modelLoguin", modelLoguin);// manter os dados na tela ou realizar edição de dados
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelLoguin> modelLoguins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("msg", "Usuarios carregados !");
				request.setAttribute("modelLoguins", modelLoguins);// manter os dados na tela ou realizar edição de
																	// dados
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser = request.getParameter("id");
				
				 ModelLoguin modelLogin =  daoUsuarioRepository.consultaUsuarioID(idUser, super.getUserLogado(request));
				 if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					 
					 response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					 response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				
			}
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset = Integer.parseInt( request.getParameter("pagina"));
				List<ModelLoguin> modelloguins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
				request.setAttribute("modelLoguins", modelloguins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")){
				
				 String dataInicial = request.getParameter("dataInicial");
				 String dataFinal = request.getParameter("dataFinal");
				 
				
				 if (dataInicial == null || dataInicial.isEmpty() 
						 && dataFinal == null || dataFinal.isEmpty()) {
					 
					 request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
											
			} else {
				
				 request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
				 
			 }
			 
			 
			 request.setAttribute("dataInicial", dataInicial);
			 request.setAttribute("dataFinal", dataFinal);
			 request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
			 
		 } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF") || 
			acao.equalsIgnoreCase("imprimirRelatorioExcel")){
			 
			 String dataInicial = request.getParameter("dataInicial");
			 String dataFinal = request.getParameter("dataFinal");
			 
			 List<ModelLoguin> modelLogins = null;
			 
			 if (dataInicial == null || dataInicial.isEmpty() 
					 && dataFinal == null || dataFinal.isEmpty()) {
				 
				 modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));
				 
			 }else {
				 
				 modelLogins = daoUsuarioRepository
						 .consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);
			 }
			 
			 
			 HashMap<String, Object> params = new HashMap<String, Object>();
			 params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);
			 
			 byte[] relatorio = null;
			 
			 String extensal = "";
			 
			 if(acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
			 
			 relatorio = new ReportUtil().geraReltorioPDF(modelLogins, "rel-user-jsp", params ,request.getServletContext());
			 extensal = "pdf";
			 }/*else if (acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
				 relatorio = new ReportUtil().geraReltorioExcel(modelLogins, "rel-user-jsp", params ,request.getServletContext());
				 extensal = "xls";	 
				 
			 }*/
			 
			 
			 response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf" + extensal);
			 response.getOutputStream().write(relatorio);
			 
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")){
		 
			 String dataInicial = request.getParameter("dataInicial");
			 String dataFinal = request.getParameter("dataFinal");
			 
			 
			 
			 if (dataInicial == null || dataInicial.isEmpty() 
					 && dataFinal == null || dataFinal.isEmpty()) {
				 
				 BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request));
					ObjectMapper mapper = new ObjectMapper();
					 String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					 response.getWriter().write(json);
				 
				
				 
			 }else {
				 
				 BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request),dataInicial, dataFinal);
					ObjectMapper mapper = new ObjectMapper();
					 String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					 response.getWriter().write(json);
				
				
			 }
			 
			 
			 
		 }
				 

			else {
				List<ModelLoguin> modelLoguin = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLoguin", modelLoguin);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		 }

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// usado para salvar e atualizar dados do formulário para o banco de dados.
		try {

			String msg = "operação realizada com sucesso !";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String loguin = request.getParameter("loguin");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String dataNascimento = request.getParameter("dataNascimento");
			String rendaMensal = request.getParameter("rendamensal");
		    rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
		
			

			ModelLoguin modelLoguin = new ModelLoguin();
			modelLoguin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLoguin.setNome(nome);
			modelLoguin.setEmail(email);
			modelLoguin.setLoguin(loguin);
			modelLoguin.setSenha(senha);
			modelLoguin.setPerfil(perfil);
			modelLoguin.setSexo(sexo);
			modelLoguin.setCep(cep);
			modelLoguin.setLogradouro(logradouro);
			modelLoguin.setBairro(bairro);
			modelLoguin.setLocalidade(localidade);
			modelLoguin.setUf(uf);
			modelLoguin.setNumero(numero);
			modelLoguin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
			modelLoguin.setRendamensal(Double.valueOf(rendaMensal));
			
			if(ServletFileUpload.isMultipartContent(request)) {
				Part part = request.getPart("filefoto");// aqui pega a foto da tela
				
				if(part.getSize() > 0) {
				byte[] foto = IOUtils.toByteArray(part.getInputStream());//converte a imagem para byte
				String imagemBase64 = "data:image/"+ part.getContentType().split("\\/")[1] +";base64," + new  Base64().encodeBase64String(foto);
			    
				modelLoguin.setFotouser(imagemBase64);
				modelLoguin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
				}
			}
			
			if (daoUsuarioRepository.validaLoguin(modelLoguin.getLoguin()) && modelLoguin.getId() == null) {
				msg = "já existe usuário com o mesmo loguin, informe outro ,loguin !;";
			} else {
				if (modelLoguin.isNovo()) {
					msg = "Gravado com sucesso";
				} else {

					msg = "Atualizado com sucesso !";
				}
				modelLoguin = daoUsuarioRepository.gravarUsuario(modelLoguin , super.getUserLogado(request));
			}

			List<ModelLoguin> modelLoguins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLoguins", modelLoguins);
			request.setAttribute("msg", msg);
			request.setAttribute("modelLoguin", modelLoguin);// manter os dados na tela ou realizar edição de dados
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		} catch (Exception e) {

			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}

	}

}
