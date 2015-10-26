# ocr-project
Implementei e desenvolvi essa aplicação durante meu estudo com processamento de imagens e OCR(Reconhecimento ótico de caracteres).
Por enquanto são reconhecidas apenas palavras isoladas, ainda não sendo possivel reconhecer espaço e quebras de linha.

Para executar a aplicação e realizar testes de reconhecimento, basta acessar o diretorio exemplo e executar o ocr-project.jar, esta compilação ja possui um arquivo serializado com dados de treinamento.

O projeto contem um subdiretorio com a seguintes estrutura:
* test-resource //Diretorio base de recursos.
* -> learm //Subdiretorio com imagens a serem usadas no treinamento.
* -> test //Subdiretorio com imagens a serem testadas.
* -> data.ser //Dados de treinamento serializados.

![screenshot](screenshots/1.png)

## Treinamento de novas letras
Processo de treinamento de novas imagens:

![screenshot](screenshots/5.png)

Selecione a imagem por meio do botão Selecionar, digite no campo abaixo o texto correspondente a imagem e precione o botão treinar para realizar o treinamento inicial, realize esse procedimento para toda e qualquer nova fonte ou estilo de letra que deseje ser suportada pelo ocr-project.	

![screenshot](screenshots/4.png)

## Testando o reconhecimento de letras
Selecione a imagem por meio do botão Selecionar e precione o botão testar para realizar o reconhecimento do texto na imagem selecionada.

![screenshot](screenshots/8.png)

Na caixa de texto logo abaixo da imagem ira aparecer o texto reconhecido e mais abaixo o console com o resultado da analise dos caracteres da imagem.

## Mais exemplos...
![screenshot](screenshots/9.png)

![screenshot](screenshots/7.png)

## Observações:
É possivel notar a dificuldade em reconhecimento de determinadas letras, como por exemplo O e 0, L e I entre outras que possui maior semelhança, futuramente planejo aplicar um classificador mais eficiente afim de melhorar os resultados de reconhecimento.
