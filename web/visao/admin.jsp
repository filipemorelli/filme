<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="modelo.FilmeModel"%>
<%@page import="modelo.UsuarioModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String nome = (String) session.getAttribute("name");
    String email = (String) session.getAttribute("email");
    String link = (String) session.getAttribute("url_social");
    String picture = (String) session.getAttribute("picture");
    String sexo = "";
    if (session.getAttribute("gender").equals("male")) {
        sexo = "Masculino";
    } else if (session.getAttribute("gender").equals("fame")) {
        sexo = "Feminino";
    } else {
        sexo = "Outro";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title><%= session.getAttribute("name")%> - Face Movie</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="shortcut icon" href="<c:url value='/template/img/favicon.png' />"/>

        <link href="<c:url value='/template/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/template/css/jquery-ui.min.css' />" rel="stylesheet">
        <link href="<c:url value='/template/css/admin.css' />" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <!-- HTML code from Bootply.com editor -->

    <body  >

        <div class="wrapper">
            <div class="box">
                <div class="row row-offcanvas row-offcanvas-left">

                    <!-- main right col -->
                    <div class="column col-xs-12" id="main">

                        <!-- top nav -->
                        <div class="navbar navbar-blue navbar-static-top">  
                            <div class="navbar-header">
                                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                                    <span class="sr-only">Toggle</span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                </button>
                                <a href="#" class="navbar-brand logo" data-toggle="tooltip" data-placement="bottom" title="Face Movie">m</a>
                            </div>
                            <nav class="collapse navbar-collapse" role="navigation">
                                <form class="navbar-form navbar-left" id="buscarPessoaPorFilme" action="buscar-pessoas-por-filme">
                                    <input type="hidden" name="filme" id="filme">
                                    <div class="input-group input-group-sm" style="max-width:360px;">
                                        <input type="text" class="form-control" placeholder="Pesquisar..." name="srch-term" id="srch-term">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                                        </div>
                                    </div>
                                </form>
                                <ul class="nav navbar-nav">
                                    <li>
                                        <a href="<c:url value='/admin' />"><i class="fa fa-home"></i> Início</a>
                                    </li>
                                    <li>
                                        <a href="<c:url value='/perfil' />"><i class="fa fa-user"></i> Perfil</a>
                                    </li>
                                    <li>
                                        <a href="<c:url value='/pessoas-comum' />"><i class="fa fa-users"></i> Gostos em comum</a>
                                    </li>
                                    <li>
                                        <a href="#postModal" role="button" data-toggle="modal"><i class="fa fa-plus"></i> Post</a>
                                    </li>
                                </ul>
                                <ul class="nav navbar-nav navbar-right">
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-list"></i></a>
                                        <ul class="dropdown-menu">
                                            <li><a href="<c:url value='/perfil' />"><i class="fa fa-user"></i> Perfil</a></li>
                                            <li><a href="#postModal" data-toggle="modal"><i class="fa fa-plus"></i> Post</a></li>
                                            <li><a href="<c:url value='/logout' />"><i class="fa fa-sign-out"></i> Sair</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                        <!-- /top nav -->

                        <div class="padding container">
                            <div class="full col-sm-9">

                                <!-- content -->                      
                                <div class="row">

                                    <!-- main col left --> 
                                    <div class="col-sm-3">

                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <p>
                                                    <img class="img-responsive img-rounded center-block" src="<%= picture%>">
                                                </p>
                                                <p class="lead"><%= nome%></p>
                                                <p>Sexo: <%= sexo%></p>
                                                <p>E-mail: <%= email%></p>
                                                <p>Link Facebook: <a href="<%= link%>" title="Link para facebook" target="_blank">clique aqui</a></p>
                                            </div>
                                        </div>

                                        <div class="panel panel-default">
                                            <div class="panel-heading"><h4>Seus Filmes</h4></div>
                                            <div class="panel-body">
                                                <ul class="list-group">
                                                    <%
                                                        //cria a lista de filmes que curti
                                                        UsuarioModel user = UsuarioModel.carregarPorIdSocial((String) session.getAttribute("id_social")); //busca o usuario

                                                        //caso a lista estiver vazia mostra um item falando que não tem filmes senao faz um loop e mostra todos
                                                        if (user.getFilmes() == null || user.getFilmes().isEmpty()) {
                                                            out.println("<a href=\"#\" class=\"list-group-item\">Sem Filmes</a>");
                                                        } else {

                                                            ArrayList<FilmeModel> filmes = new ArrayList<FilmeModel>(user.getFilmes()); // cria um lista com todos os filmes
                                                            for (FilmeModel filme : filmes) {
                                                                out.println("<a href=\"#\" class=\"list-group-item\">" + filme.getTitulo() + "</a>");
                                                            }
                                                        }
                                                    %>
                                                </ul>
                                            </div>
                                        </div>

                                    </div>

                                    <!-- main col right -->
                                    <div class="col-sm-6">

                                        <%@include file="adminlPost.jsp" %>

                                    </div>

                                    <div class="col-sm-3">
                                        <div class="panel panel-default">
                                            <div class="panel-thumbnail"><img src="<c:url value='/template/img/bg_5.jpg' />" class="img-responsive"></div>
                                            <div class="panel-body">
                                                <p class="lead">Mercha</p>
                                                <p>Descricao ...</p>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <hr>

                                <div class="row" id="footer">    
                                    <div class="col-sm-6 col-sm-offset-6">
                                        <p>
                                            <a href="#" class="pull-right">© Face Movie 2016</a>
                                        </p>
                                    </div>
                                </div>


                            </div><!-- /col-9 -->
                        </div><!-- /padding -->
                    </div>
                    <!-- /main -->

                </div>
            </div>
        </div>


        <!--post modal-->
        <div id="postModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        Novo Comentário
                    </div>
                    <div class="modal-body">
                        <form class="form center-block" id="novo-post">
                            <div class="form-group">
                                <input type="text" class="form-control input-lg nome-filme" autofocus placeholder="Digite o nome do filme" />
                            </div>
                            <hr>
                            <div class="form-group">
                                <textarea class="form-control input-lg comentario-filme" placeholder="Post sobre filme..."></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <div>
                            <button class="btn btn-primary btn-salva-post" type="button">Post</button>
                        </div>	
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="<c:url value='/template/js/jquery.min.js' />"></script>
        <script src="<c:url value='/template/js/jquery-ui.min.js' />"></script>
        <script src="<c:url value='/template/js/jquery-ui-touchpunch.min.js' />"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="<c:url value='/template/js/bootstrap.min.js' />"></script>

        <script src="<c:url value='/template/js/admin.js' />"></script>


    </body>
</html>