<%@page import="model.ModelLoguin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">



<jsp:include page="Head.jsp"></jsp:include>

<body>


	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">
			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmaimenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<jsp:include page="pager-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">


										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">

													<div class="card-block">
														<h4 class="sub-title">DADOS DO USUÁRIO</h4>

														<form class="form-material" enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLoguin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID
																	:</label>
															</div>
															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																	<c:if
																		test="${modelLoguin.fotouser !='' && modelLoguin.fotouser != null}">
																		<a
																			href="<%=request.getContextPath()%>/ServletUsuarioController?acao=downloadFoto&id=${modelLoguin.id}">
																			<img alt="Imagem User" id="fotoembase64"
																			src="${modelLoguin.fotouser}" width="70px">
																		</a>
																	</c:if>

																	<c:if
																		test="${modelLoguin.fotouser =='' || modelLoguin.fotouser == null }">
																		<img alt="Imagem User" id="fotoembase64"
																			src="assets/images/índice.png" width="70px">
																	</c:if>

																</div>
																<input type="file" id="filefoto" name="filefoto"
																	accept="image/*"
																	onchange="visualizarImg('fotoembase64','filefoto');"
																	class="form-control-file"
																	style="margin-top: 15px; margin-left: 5px;">
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLoguin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">NOME
																	:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="dataNascimento"
																	id="dataNascimento" class="form-control"
																	required="required"
																	value="${modelLoguin.dataNascimento}"> <span
																	class="form-bar"></span> <label class="float-label">DATA
																	DE NASCIMENTO :</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="rendamensal" id="rendamensal"
																	class="form-control" required="required"
																	value="${modelLoguin.rendamensal}"> <span
																	class="form-bar"></span> <label class="float-label">RENDA
																	MENSAL :</label>
															</div>




															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="" autocomplete="off"
																	value="${modelLoguin.email}"> <span
																	class="form-bar"></span> <label class="float-label">EMAIL
																	:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="perfil">
																	<option disabled="disabled">SELECIONE O PERFIL</option>


																	<option value="Admin"
																		<%ModelLoguin model = (ModelLoguin) request.getAttribute("modelLoguin");

if (model != null && model.getPerfil().equals("Admin")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Admin</option>

																	<option value="Secretaria"
																		<%model = (ModelLoguin) request.getAttribute("modelLoguin");

if (model != null && model.getPerfil().equals("Secretaria")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Secretaria</option>
																	<option value="Auxiliar"
																		<%model = (ModelLoguin) request.getAttribute("modelLoguin");

if (model != null && model.getPerfil().equals("Auxiliar")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Auxiliar</option>
																	<option value="Admin2"
																		<%model = (ModelLoguin) request.getAttribute("modelLoguin");

if (model != null && model.getPerfil().equals("Admin2")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Admin2</option>
																</select> <span class="form-bar"></span> <label
																	class="float-label">Perfil</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input onblur="pesquisarCep();" type="text" name="cep"
																	id="cep" class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.cep}">
																<span class="form-bar"></span> <label
																	class="float-label">CEP :</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="logradouro" id="logradouro"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.logradouro}">
																<span class="form-bar"></span> <label
																	class="float-label">LOGRADOURO :</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.bairro}">
																<span class="form-bar"></span> <label
																	class="float-label">BAIRRO :</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.localidade}">
																<span class="form-bar"></span> <label
																	class="float-label">LOCALIDADE :</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.uf}"> <span
																	class="form-bar"></span> <label class="float-label">ESTADO
																	:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.numero}">
																<span class="form-bar"></span> <label
																	class="float-label">NÚMERO :</label>
															</div>



															<div class="form-group form-default form-static-label">
																<input type="password" name="loguin" id="loguin"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.loguin}">
																<span class="form-bar"></span> <label
																	class="float-label">LOGUIN :</label>
															</div>


															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLoguin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">SENHA :</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="radio" name="sexo" checked="checked"
																	value="Masculino"
																	<%model = (ModelLoguin) request.getAttribute("modelLoguin");
if (model != null && model.getPerfil().equals("MASCULINO")) {
	out.print(" ");
	out.print("checked=\"checked\"");
	out.print(" ");
}%>>Masculino</>
																<input type="radio" name="sexo" value="FEMENINO"
																	<%model = (ModelLoguin) request.getAttribute("modelLoguin");
if (model != null && model.getPerfil().equals("FEMENINO")) {
	out.print(" ");
	out.print("checked=\"checked\"");
	out.print(" ");
}%>>Feminino</>
															</div>

															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm();">NOVO</button>
															<button type="submit"
																class="btn btn-success waves-effect waves-light">SALVAR</button>
															<button type="button"
																class="btn btn-info waves-effect waves-light"
																onclick="criaDeleteComAjax();">EXCLUIR</button>
															<c:if test="${modelLoguin.id > 0}">
																<a
																	href="<%= request.getContextPath() %>/ServletTelefone?iduser=${modelLoguin.id}"
																	class="btn btn-success waves-effect waves-light">TELEFONE</a>
															</c:if>
															<button type="button" class="btn btn-secondary"
																data-toggle="modal" data-target="#exampleModalUsuario">PESQUISAR</button>



														</form>

													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>
										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaresultadosview">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">NOME</th>
														<th scope="col">VER</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items='${modelLoguins}' var='ml'>
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-sucesss"
																href="<%=request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">VER</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>


										</div>

										<nav aria-label="Page navigation example">
											<ul class="pagination">
												<%
												int totalPagina = (int) request.getAttribute("totalPagina");

												for (int p = 0; p < totalPagina; p++) {
													String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);
													out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
												}
												%>
												<!-- <li class="page-item"><a class="page-link" href="#"></a></li>-->

											</ul>
										</nav>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<!-- Modal -->
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">PESQUISA DE
						USUÁRIO</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control"
							placeholder="digite o nome" aria-label="nome" id="nomeBusca"
							aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-primary" type="button"
								onclick="buscarUsuario();">BUSCAR</button>
						</div>
					</div>

					<div style="height: 300px; overflow: scroll;">
						<table class="table" id="tabelaresultados">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">NOME</th>
									<th scope="col">VER</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>


					</div>

					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">
						</ul>
					</nav>

					<span id="totalresultados"> </span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">FECHAR</button>

				</div>
			</div>
		</div>
	</div>




	<script type="text/javascript">

	$("#rendamensal").maskMoney({showSymbol:true,symbol:"R$ ",decimal:",",thousands:"."});


	const formatter = new Intl.NumberFormat('pt-BR', {
	    currency : 'BRL',
	    minimumFractionDigits : 2
	});

	$("#rendamensal").val(formatter.format($("#rendamensal").val()));

	$("#rendamensal").focus();
	
	
	var dataNascimento = $("#dataNascimento").val();

	if(dataNascimento != null && dataNascimento !=''){

	var dateFormat = new Date(dataNascimento);

	$("#dataNascimento").val(dateFormat.toLocaleDateString('pt-BR',{timeZone: 'UTC'}));

	}
	
	$("#nome").focus();



	
	

	$( function() {
		  
		  $("#dataNascimento").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Próximo',
			    prevText: 'Anterior'
			});
	} );

	
	

        $("#numero").keypress(function(event){
         return /\d/.test(String.fromCharCode(event.keyCode));<!--expressão usada para a os campos da tela só aceitarem números-->

            });

        $("#cep").keypress(function(event){
            return /\d/.test(String.fromCharCode(event.keyCode));<!--expressão usada para a os campos da tela só aceitarem números-->

               });


	
		function pesquisarCep() {
			var cep = $('#cep').val();

			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#cep").val(dados.cep);
							$("#logradouro").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#localidade").val(dados.localidade);
							$("#uf").val(dados.uf);
						}

					});

		}

		function visualizarImg(fotoembase64, filefoto) {

			var preview = document.getElementById(fotoembase64)/*campo img html*/
			var fileUser = document.getElementById(filefoto).files[0];
			var reader = new FileReader();

			reader.onloadend = function() {
				preview.src = reader.result;/*carrega a foto na tela*/

			};
			if (fileUser) {
				reader.readAsDataURL(fileUser);/* preview da imagem*/
			} else {
				preview.src = '';
			}
		}

		function verEditar(id) {
			var urlAction = document.getElementById('formUser').action;
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
		}


		function buscarUserPagAjax(url){
			var nomeBusca = document.getElementById('nomeBusca').value;
			var urlAction = document.getElementById('formUser').action;
			
			$.ajax(
					{

						method : "get",
						url : urlAction,
						data :url,						
						success : function(response , textStatus, xhr) {

							var json = JSON.parse(response);

							$('#tabelaresultados > tbody > tr')
									.remove();
							$("#ulPaginacaoUserAjax > li").remove();

							for (var p = 0; p < json.length; p++) {
								$('#tabelaresultados > tbody')
										.append(
												'<tr> <td> '
														+ json[p].id
														+ ' </td><td>'
														+ json[p].nome
														+ '</td><td> <button onclick="verEditar('
														+ json[p].id
														+ ')" type="button" class="btn btn-info"> VER</button>  </td> </tr>');

							}

							document
									.getElementById('totalresultados').textContent = 'Resultados:'
									+ json.length;

                               var totalPagina2 = xhr.getResponseHeader("totalPagina");
                              for(var p= 0 ; p < totalPagina2;p++){
                                  
                            	  
                                  
                                  var url =  'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&Pagina='+ (p*5);
                                  $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUserPagAjax(\''+ url +'\')">'+ (p + 1) +'</a></li>');
                                 
                                    
                                  }
                                 
                               
						}

					}).fail(
					function(xhr, status, errorThrow) {
						alert('Erro ao buscar usuário por nome : '
								+ xhr.responseText)
					});

			}

		function buscarUsuario() {

			var nomeBusca = document.getElementById('nomeBusca').value;
			if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
				/*validando que tem que ter valor pra buscar no banco */
				var urlAction = document.getElementById('formUser').action;
				$.ajax({method : "get",
									url : urlAction,
									data : "nomeBusca=" + nomeBusca
											+ '&acao=buscarUserAjax',
									success : function(response , textStatus, xhr) {

										var json = JSON.parse(response);

										$('#tabelaresultados > tbody > tr')
												.remove();
										$("#ulPaginacaoUserAjax > li").remove();

										for (var p = 0; p < json.length; p++) {
											$('#tabelaresultados > tbody')
													.append(
															'<tr> <td> '
																	+ json[p].id
																	+ ' </td><td>'
																	+ json[p].nome
																	+ '</td><td> <button onclick="verEditar('
																	+ json[p].id
																	+ ')" type="button" class="btn btn-info"> VER</button>  </td> </tr>');

										}

										document
												.getElementById('totalresultados').textContent = 'Resultados:'
												+ json.length;

	                                       var totalPagina2 = xhr.getResponseHeader("totalPagina");
	                                      for(var p= 0 ; p < totalPagina2;p++){
		                                      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&Pagina='+ (p*5);
		                                      $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUserPagAjax(\''+ url +'\')">'+ (p + 1) +'</a></li>');
                                             
                                                
		                                      }
                                             
	                                       
									}

								}).fail(
								function(xhr, status, errorThrow) {
									alert('Erro ao buscar usuário por nome : '
											+ xhr.responseText)
								});

			}
		}

		function criaDeleteComAjax() {
			if (confirm('deseja realmente excluir os dados ?')) {
				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;
				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + '&acao=deletarajax',
					success : function(response) {

						limparForm();
						document.getElementById('msg').textContent = response;

					}

				}).fail(
						function(xhr, status, errorThrow) {
							alert('Erro ao deletar usuário por id : '
									+ xhr.responseText)
						});

			}

		}

		function criarDelete() {

			if (confirm('deseja realmente excluir os dados')) {

				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		function limparForm() {
			var elementos = document.getElementById("formUser").elements; /*retorna os elementos html dentro do form */

			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	</script>



</body>

</html>
