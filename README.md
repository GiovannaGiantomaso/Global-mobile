# Projeto de Monitoramento de Consumo de Energia

## Descrição do Projeto

O Projeto de Monitoramento de Consumo de Energia é uma solução que permite aos usuários registrar manualmente o consumo mensal de energia elétrica e acompanhar sua evolução ao longo do tempo. 
O objetivo principal é promover a conscientização sobre o uso responsável da energia elétrica, incentivando hábitos sustentáveis por meio de uma interface amigável e intuitiva.
--

### Funcionalidades Principais

### 1. Registro Mensal de Consumo
   
- Os usuários podem inserir manualmente o consumo mensal de energia.

- Os dados são armazenados no Firebase Firestore, garantindo persistência e integridade.

- O consumo é organizado por mês e ano, facilitando a consulta e gestão do histórico.


### 2. Visualização do Histórico de Consumo
   
- O aplicativo exibe gráficos interativos que mostram a evolução do consumo ao longo do tempo.

- Os gráficos ajudam os usuários a identificar padrões, como aumento ou redução no consumo.


### 3. Feedback sobre o Consumo
Compara o consumo atual com o mês anterior.

- Exibe mensagens de feedback motivacionais:

- Caso o consumo tenha aumentado, o aplicativo alerta para a necessidade de economizar.

- Caso o consumo tenha diminuído, parabeniza o usuário e incentiva a continuidade do hábito.


### Tecnologias Utilizadas


- Backend

- Firebase:

- Firebase Authentication: Gerenciamento de login e autenticação dos usuários.

- Firebase Firestore: Armazenamento e consulta de dados em tempo real.

- Aplicativo Mobile

- Desenvolvido com Android Studio utilizando Kotlin.

- Visualização Gráfica

- Biblioteca MPAndroidChart para a exibição de gráficos interativos e customizáveis.


## Estrutura do Projeto


## Telas:


### Tela Principal

- Exibe uma mensagem de boas-vindas e descrição do sistema.

- Botões para registrar consumo, visualizar histórico e acessar gráficos.


### Tela de Registro

- Permite ao usuário inserir o consumo mensal e salvar no sistema.


### Tela de Histórico

- Mostra o histórico detalhado do consumo mensal com a opção de editar ou excluir.


### Tela de Gráfico

- Apresenta a evolução do consumo por meio de gráficos lineares.

-  Inclui feedback textual sobre o consumo comparado ao mês anterior.


### Configuração do Projeto

Requisitos

Ferramentas Necessárias:


- Android Studio.

- Gradle (versão 8.4 configurada no projeto).

- Firebase (configuração JSON para integração).

Configuração do Firebase:


Configure um projeto no Firebase.

Habilite o Firestore e o Authentication.

Baixe o arquivo google-services.json e adicione à pasta app do projeto.

### Passos para Executar

Clone o repositório:

git clone https://github.com/seu-usuario/projeto-monitoramento-energia.git

Abra o projeto no Android Studio.

Configure o arquivo google-services.json para integrar o Firebase.

Execute o aplicativo no emulador ou dispositivo físico.


## Objetivo do Projeto

O projeto busca conscientizar os usuários sobre a importância de economizar energia elétrica.

Através de ferramentas intuitivas, como gráficos e feedback textual, os usuários podem acompanhar seus padrões de consumo e tomar decisões mais conscientes e sustentáveis.
