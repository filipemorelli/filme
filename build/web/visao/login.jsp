<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="<c:url value='/template/img/favicon.png' />"/>

        <title>Face Movie - Login</title>

        <link href="<c:url value='/template/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/template/css/login.css' />" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">


        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <body>

        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Login</h3>
                        </div>
                        <div class="panel-body">
                            <form role="form">
                                <fieldset>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="E-mail" name="email" type="email" autofocus required>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Password" name="password" type="password" value="" required>
                                    </div>
                                    <!-- Change this to a button or input when using this as a form -->
                                    <button type="submit" class="btn btn-lg btn-success btn-block">Login</button>
                                    <hr>
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <a href="loginfb" class="btn btn-lg btn-primary btn-block"><i class="fa fa-facebook-official"></i></a>
                                        </div>
                                        <div class="col-xs-4">
                                            <a href="loginfb" class="btn btn-lg btn-info btn-block"><i class="fa fa-twitter"></i></a>
                                        </div>
                                        <div class="col-xs-4">
                                            <a href="loginfb" class="btn btn-lg btn-danger btn-block"><i class="fa fa-google-plus"></i></a>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="<c:url value='/template/js/jquery.min.js' />"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="<c:url value='/template/js/bootstrap.min.js' />"></script>


    </body>

</html>
