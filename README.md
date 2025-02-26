# Separador CSV

## Descrição
Programa simples que permite dividir arquivos de extensão **.csv** ou **.txt** em partes menores. O programa lê o arquivo inteiro e gera novos arquivos de acordo com as opções escolhidas. O arquivo original não é alterado.

## Requisitos

*	Java 21 - Consulte <https://www.oracle.com/java/technologies/downloads/> para fazer a instalação.
*	JavaFX SDK 21 (Opcional) - Consulte <https://openjfx.io/> para fazer a instalação e criar a variável de ambiente.

## Como usar
Em um sistema Windows / Linux / MacOS basta baixar o arquivo [csv-splitter.jar](https://github.com/Hilan-CP/csv-splitter/releases/download/v1.0.1/csv-splitter.jar) e dar um duplo clique.

Para usar a versão modular é necessário realizar a instalação do JavaFX SDK. Com a instalação concluída, baixe o arquivo [csv-splitter-1.0.1.jar](https://github.com/Hilan-CP/csv-splitter/releases/download/v1.0.1/csv-splitter-1.0.1.jar) e use seguinte comando para executar a aplicação:

	java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp csv-splitter-1.0.1.jar com.example.csv_splitter.Main

## Opções

*	Arquivo: insira o caminho completo do arquivo ou clique em "Procurar" para escolher o arquivo. Apenas as extensões **.csv** e **.txt** são suportadas.
*	Quantidade: quantidade X de linhas que cada arquivo dividido terá. Se a opção de "primeira linha de cabeçalho" estiver marcada, cada arquivo dividido terá um total de linhas X + 1, caso contrário terá X linhas.
*	Primeira linha de cabeçalho: marque se deseja que a primeira linha do arquivo original seja replicada em cada arquivo dividido.
*	Processar: executa o processamento de divisão do arquivo.
