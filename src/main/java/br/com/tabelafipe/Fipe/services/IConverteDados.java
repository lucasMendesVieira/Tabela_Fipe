package br.com.tabelafipe.Fipe.services;

import java.util.List;

public interface IConverteDados {

    <T> T converteDados(String json,Class<T> classe );

    <T> List<T> ObterLista(String json, Class<T> classe);

}
