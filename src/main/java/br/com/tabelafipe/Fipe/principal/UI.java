package br.com.tabelafipe.Fipe.principal;

import br.com.tabelafipe.Fipe.model.EspecificacaoVeiculo;
import br.com.tabelafipe.Fipe.model.Modelos;
import br.com.tabelafipe.Fipe.services.ConverteVeiculos;
import br.com.tabelafipe.Fipe.services.ObterDados;
import br.com.tabelafipe.Fipe.model.Veiculo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    Scanner sc = new Scanner(System.in);
    private final String urlBase = "https://parallelum.com.br/fipe/api/v1/";
    private final String urlFinal = "/marcas";
    private ObterDados obterDados = new ObterDados();
    private ConverteVeiculos converteVeiculos = new ConverteVeiculos();

    public void escolheVeiculo(){
                String menu = """
                **OPÇÕES DE VEICULO**
                - CARROS
                - MOTOS
                - CAMINHOES
                """;
        System.out.println(menu);
        String opcao = sc.nextLine();
        String endereco;
        if(opcao.toLowerCase().contains("carr")){
            endereco = urlBase + opcao+ urlFinal;
        } else if(opcao.toLowerCase().contains("mot")){
            endereco = urlBase + opcao+ urlFinal;
        } else if(opcao.toLowerCase().contains("caminao")){
            endereco = urlBase + opcao+ urlFinal;
        }else {
            endereco = urlBase + opcao+ urlFinal;
        }

        String json = obterDados.pegaDados(endereco);
        List<Veiculo> veiculo = converteVeiculos.ObterLista(json,Veiculo.class);
        veiculo.stream()
                .sorted(Comparator.comparing(Veiculo::codigo))
                .forEach(System.out::println);


        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = sc.nextLine();
        endereco = endereco+"/"+codigoMarca+"/modelos";
        json = obterDados.pegaDados(endereco);
        var modeloLista = converteVeiculos.converteDados(json, Modelos.class);
        System.out.println("\nMODELOS DA MARCA:");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Veiculo::codigo))
                .forEach(System.out::println);

        System.out.println("\nDIGITE UM TRECHO DO NOME DO CARRO DESEJADO: ");
            var nomeVeiculo = sc.nextLine();
            List<Veiculo> modeloFiltrado = modeloLista.modelos().stream()
                    .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                    .collect(Collectors.toList());
        System.out.println("\nMODELO FILTRADO: ");
            modeloFiltrado.forEach(System.out::println);

        System.out.println("\nDIGITE O CÓDIGO DO MODELO PARA AVALIAÇÃO: ");
        var codigoModelo = sc.nextLine();
        endereco= endereco+"/"+codigoModelo+"/anos";
        json = obterDados.pegaDados(endereco);
        List<Veiculo> anos = converteVeiculos.ObterLista(json, Veiculo.class);
        List<EspecificacaoVeiculo> especificacaoVeiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco+"/"+anos.get(i).codigo();
            json = obterDados.pegaDados(enderecoAnos);
            EspecificacaoVeiculo especificacaoVeiculo = converteVeiculos.converteDados(json,EspecificacaoVeiculo.class);
            especificacaoVeiculos.add(especificacaoVeiculo);
        }
        System.out.println("\nTODOS OS VEICULOS FILTRADOS POR ANOS:");
        especificacaoVeiculos.forEach(System.out::println);
    }





}
