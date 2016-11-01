$(document).ready(function () {

    $('[data-toggle="tooltip"]').tooltip();

    //https://api.themoviedb.org/3/search/movie?api_key=782df2733ee983d5f7e96fe06429d2c0&language=pt-BR

    var filme;
    //funcao no campo de auto complete
    $(".pesquisa-filme").autocomplete({
        minLength: 1, //comeca apesquisar quando for mais de 1
        //quando selecionado adiciona a classe has-success
        select: function (event, ui) {
            $(".campo-filme").addClass("has-success").removeClass("has-warning");
            filme = ui.item;
        },
        search: function (event, ui) {
            $(".campo-filme").addClass("has-warning").removeClass("has-success");
            filme = false;
        },
        source: function (request, response) {
            // request.term is the term searched for.
            // response is the callback function you must call to update the autocomplete's 
            // suggestion list.
            $.ajax({
                url: "https://api.themoviedb.org/3/search/movie?&language=pt-BR",
                data: {
                    api_key: '782df2733ee983d5f7e96fe06429d2c0',
                    query: request.term,
                    language: 'pt-BR'
                },
                dataType: "json",
                success: function (data) {
                    var dados = $.map(data.results, function (item) {
                        return {
                            id: item.id,
                            titulo_original: item.original_title,
                            titulo: item.title,
                            descricao: item.overview,
                            poster: item.poster_path,
                            data_lancamento: item.release_date,
                            idioma: item.original_language,
                            label: item.title,
                            value: item.title
                        };
                    });
                    return response(dados);
                },
                error: function () {
                    response([]);
                }
            });
        },
        create: function () {
            var $this = $(this);
            $(this).data('ui-autocomplete')._renderItem = function (ul, item) {
                var width = $this.width();
                return $('<li>')
                        .append('<div> <img src="https://image.tmdb.org/t/p/w185_and_h278_bestv2' + item.poster + '" class="img-autocomplete-render"><p class="titulo-autocomplete-render lead">' + item.titulo + '</p><p class="descricao-autocomplete-render" style="width:' + (width - 5) + 'px">' + item.descricao + '</p></div>')
                        .appendTo(ul);
            };
        }
    });

    $(".pesquisa-filme").keyup(function () {
        if ($(this).val().length === 0) {
            $(".campo-filme").removeClass("has-success").removeClass("has-warning");
        }
    });

    $(document).on("click", ".exclui-filme", function (e) {
        e.preventDefault();
        var indice = $(this).attr("data-indice");
        $.get("excluir-filme-perfil", {indice: indice}, function (data) {
            if (data.status) {
                alert(data.msg);
                BuscaFilmes();
            } else {
                alert(data.msg);
            }
        }, "json");
    });

    //envia para o servidor
    $("#adiciona-filme").submit(function (e) {
        e.preventDefault();

        //se nao tiver nada em filme nao manda ajax
        if (filme) {
            $.post("add-filme-ajax", filme, function (data) {
                if (data.status) {
                    //status for true limpa o campo
                    $(".pesquisa-filme").val("");
                    $(".campo-filme").removeClass("has-success").removeClass("has-warning");
                    BuscaFilmes();
                } else {
                    console.log("Erro");
                }
            }, "json");
        }

        return false;
    });

    var buscarPessoaPorFilme;
    $("#srch-term").autocomplete({
        minLength: 1, //comeca apesquisar quando for mais de 1
        //quando selecionado adiciona a classe has-success
        select: function (event, ui) {
            buscarPessoaPorFilme = ui.item;
        },
        search: function (event, ui) {
            buscarPessoaPorFilme = false;
        },
        source: function (request, response) {
            // request.term is the term searched for.
            // response is the callback function you must call to update the autocomplete's 
            // suggestion list.
            $.ajax({
                url: "https://api.themoviedb.org/3/search/movie?&language=pt-BR",
                data: {
                    api_key: '782df2733ee983d5f7e96fe06429d2c0',
                    query: request.term,
                    language: 'pt-BR'
                },
                dataType: "json",
                success: function (data) {
                    var dados = $.map(data.results, function (item) {
                        return {
                            id: item.id,
                            titulo_original: item.original_title,
                            titulo: item.title,
                            descricao: item.overview,
                            poster: item.poster_path,
                            data_lancamento: item.release_date,
                            idioma: item.original_language,
                            label: item.title,
                            value: item.title
                        };
                    });
                    return response(dados);
                },
                error: function () {
                    response([]);
                }
            });
        },
        create: function () {
            var $this = $(this);
            $(this).data('ui-autocomplete')._renderItem = function (ul, item) {
                var width = $this.width();
                var desc = item.descricao.length > 100 ? item.descricao.substring(0, 100) + "..." : item.descricao;
                return $('<li>')
                        .append('<div> <img src="https://image.tmdb.org/t/p/w185_and_h278_bestv2' + item.poster + '" class="img-autocomplete-nav"><p class="titulo-autocomplete-render">' + item.titulo + '</p><p class="descricao-autocomplete-render" style="width:' + (width - 20) + 'px">' + desc + '</p></div>')
                        .appendTo(ul);
            };
        }
    });

    $("#buscarPessoaPorFilme").submit(function (e) {
        $("#filme").val();
        window.location.href = "http://localhost:8080/facemovie/buscar-pessoas-por-filme/?" + $.param(buscarPessoaPorFilme)
        return false;
    });

    var nomeFilme;
    //funcao no campo de auto complete
    $(".nome-filme").autocomplete({
        minLength: 1, //comeca apesquisar quando for mais de 1
        //quando selecionado adiciona a classe has-success
        select: function (event, ui) {
            $(".nome-filme").addClass("has-success").removeClass("has-warning");
            nomeFilme = ui.item;
        },
        search: function (event, ui) {
            $(".nome-filme").addClass("has-warning").removeClass("has-success");
            nomeFilme = false;
        },
        source: function (request, response) {
            // request.term is the term searched for.
            // response is the callback function you must call to update the autocomplete's 
            // suggestion list.
            $.ajax({
                url: "https://api.themoviedb.org/3/search/movie?&language=pt-BR",
                data: {
                    api_key: '782df2733ee983d5f7e96fe06429d2c0',
                    query: request.term,
                    language: 'pt-BR'
                },
                dataType: "json",
                success: function (data) {
                    var dados = $.map(data.results, function (item) {
                        return {
                            id: item.id,
                            titulo_original: item.original_title,
                            titulo: item.title,
                            descricao: item.overview,
                            poster: item.poster_path,
                            data_lancamento: item.release_date,
                            idioma: item.original_language,
                            label: item.title,
                            value: item.title
                        };
                    });
                    return response(dados);
                },
                error: function () {
                    response([]);
                }
            });
        },
        create: function () {
            var $this = $(this);
            $(this).data('ui-autocomplete')._renderItem = function (ul, item) {
                var width = $this.width();
                return $('<li>')
                        .append('<div> <img src="https://image.tmdb.org/t/p/w185_and_h278_bestv2' + item.poster + '" class="img-autocomplete-render"><p class="titulo-autocomplete-render lead">' + item.titulo + '</p><p class="descricao-autocomplete-render" style="width:' + (width - 20) + 'px">' + item.descricao + '</p></div>')
                        .appendTo(ul);
            };
        }
    });

    $(".btn-salva-post").click(function (e) {
        e.preventDefault();

        if (nomeFilme && $(".comentario-filme").val().length > 0) {
            nomeFilme.postDescricao = $(".comentario-filme").val();
            $.post("novo-post", nomeFilme, function (data) {
                if (data.status) {
                    $("#novo-post")[0].reset();//limpa formulario
                    $("#postModal").modal("hide");//fecha modal
                    window.location.reload();
                } else {
                    alert(data.msg);
                }
            }, "json");
        }

        return false;
    });

    $(".form-comentario").submit(function (e) {
        e.preventDefault();
        var $this = $(this);
        if ($this.children().children("input").val().length !== 0) {
            $.post("novo-comentario", {
                idPost: $this.parents(".post-content").attr("data-id"),
                comentario: $this.children().children("input").val()
            }, function (data) {
                if (data.status) {
                    $this.children().children("input").val("");
                    window.location.reload();
                } else {
                    alert(data.msg);
                }
            }, "json");
        }
        return false;
    });

    $(".exclui-post").click(function (e) {
        var $this = $(this);
        e.preventDefault();
        $.post("excluir-post", {
            idPost: $this.parents(".post-content").attr("data-id")
        }, function (data) {
            if (data.status) {
                window.location.reload();
            } else {
                alert(data.msg);
            }
        }, "json");

    });

});

// funcao busca filmes que curtir cadastrados
function BuscaFilmes() {
    $.get("buscar-filmes-perfil", {}, function (data) {
        if (data.status) {
            filmes = JSON.parse(data.msg);
            if (filmes.length === 0) {
                $(".lista-filme").html("<a href=\"#\" class=\"list-group-item\">Sem Filmes</a>");
            } else {
                $(".lista-filme").empty();
                for (var i in filmes) {
                    $(".lista-filme").append("<a href=\"#\" class=\"list-group-item\">" + filmes[i].titulo + "<div class=\"pull-right exclui-filme\" data-indice=\"" + i + "\"><i class=\"fa fa-times\"></i></div></a></a>");
                }
            }
        }
    }, "json");
}