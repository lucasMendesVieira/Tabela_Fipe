package br.com.tabelafipe.Fipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteVeiculos implements IConverteDados {

    ObterDados obterDados = new ObterDados();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T converteDados(String json, Class<T> classe) {
        try{
            return objectMapper.readValue(json,classe);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> ObterLista(String json, Class<T> classe) {
        CollectionType lista = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class,classe);
        try {
            return objectMapper.readValue(json,lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
