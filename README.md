# SpotfyLab - Clean Architecture + MVVM + Unit Tests + Analytics

## Descrição
Este é um projeto Android que utiliza a API web da Spotify para criar uma aplicação centrada na reprodução de música. A implementação é baseada em arquitetura limpa (Clean Architecture) com o padrão MVVM (Model-View-ViewModel), proporcionando uma estrutura organizada e escalável.

## Recursos Principais
* Data Binding: A aplicação faz uso extensivo do Data Binding para facilitar a transferência eficiente de dados entre a ViewModel e a interface do usuário (UI).

* Hilt para Injeção de Dependência: Utiliza Hilt para simplificar a injeção de dependência, tornando o código mais modular e de fácil manutenção.

* Kotlin: Desenvolvido inteiramente em Kotlin, aproveitando os recursos modernos da linguagem para tornar o código mais conciso e expressivo.

##Módulos
O projeto foi modularizado para facilitar o desenvolvimento, manutenção, escalabilidade e testabilidade. Os módulos incluem:

* **UI**: Contém a implementação da interface do usuário e segue o padrão MVVM.

* **COMMONS**: Inclui componentes e utilitários compartilhados em toda a aplicação.

* **NETWORK**: Responsável pela comunicação com a API da Spotify.

* **BUILDSRC**: Contém as configurações de build, incluindo versionamento de dependências.

* **ANALYTICS**: Implementação do Firebase Analytics e Crashlytics para monitoramento e análise de dados.

#Arquitetura
O projeto segue os princípios da arquitetura limpa, promovendo a separação de preocupações e a escalabilidade do código. A estrutura inclui:

## Clean Architecture
![https://fernandocejas.com/2018/05/07/architecting-android-reloaded/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture-Kotlin/architecture/clean_architecture_reloaded_main.png)

### ----------------------------------------------------------------------------------------------

## Android 3 Layers Architecture
![https://fernandocejas.com/2018/05/07/architecting-android-reloaded/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture-Kotlin/architecture/clean_architecture_reloaded_layers.png)

### ----------------------------------------------------------------------------------------------

## UI Layer: MVVM 
![https://fernandocejas.com/2018/05/07/architecting-android-reloaded/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture-Kotlin/architecture/clean_architecture_reloaded_mvvm_app.png)

### ----------------------------------------------------------------------------------------------

## Data Layer: Repository 
![https://fernandocejas.com/2018/05/07/architecting-android-reloaded/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture-Kotlin/architecture/clean_archictecture_reloaded_repository.png)

### ----------------------------------------------------------------------------------------------

# Escolhas de Segurança

No projeto, foram implementadas práticas de segurança para proteger as informações sensíveis dos usuários, como o token de acesso. Algumas das escolhas de segurança incluem:

### Armazenamento Seguro do Token de Acesso

O token de acesso do usuário é armazenado de maneira segura no aplicativo, seguindo as práticas recomendadas de segurança. As seguintes medidas foram adotadas:

1. **SharedPreferences:** O token de acesso é armazenado no `SharedPreferences` para persistência local.

2. **KeyStore:** Para garantir uma camada adicional de segurança, as chaves criptográficas necessárias para criptografar e descriptografar o token são armazenadas no `KeyStore` do dispositivo. O `KeyStore` é uma solução segura para armazenamento de chaves criptográficas, protegendo-as contra acesso não autorizado.

3. **Cipher:** A classe `Cipher` do Android é usada para criptografar a senha antes de ser salva no `SharedPreferences`. A senha permanece criptografada durante o armazenamento e é descriptografada apenas no momento de uso, garantindo que o token permaneça seguro enquanto estiver armazenado localmente.

Essas práticas visam garantir a integridade e segurança das informações do usuário, seguindo as melhores práticas de segurança no desenvolvimento de aplicativos Android.

### ----------------------------------------------------------------------------------------------

#Pré-requisitos
Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:
* Android Studio
* Java 18 sdk

O projeto possui todas as dependências necessárias nos arquivos do Gradle. Adicione o projeto ao Android Studio ou IntelliJ e sincronize. Todas as dependências serão baixadas e instaladas.

### Configuração do Firebase Analytics

Se o projeto utiliza o Firebase Analytics, será necessário configurar as credenciais do Firebase. Siga as instruções abaixo:

1. Acesse o [Console do Firebase](https://console.firebase.google.com/).

2. Crie um novo projeto ou selecione um projeto existente.

3. No painel do projeto, clique em "Adicionar app" e siga as instruções para adicionar o app Android ao projeto Firebase.

4. Faça o download do arquivo de configuração `google-services.json` e coloque-o no diretório `app` do seu projeto Android.

5. Sincronize o projeto no Android Studio para aplicar as configurações do Firebase.

Certifique-se de não compartilhar o arquivo `google-services.json` publicamente, pois contém informações sensíveis relacionadas ao seu projeto Firebase.

Agora, o Firebase Analytics estará configurado e integrado ao seu projeto Android.

### ----------------------------------------------------------------------------------------------

## Configuração

Antes de executar o projeto, é necessário configurar as credenciais da API da Spotify no arquivo `local.properties`. Siga as instruções abaixo:

1. Abra o arquivo `local.properties` localizado na raiz do seu projeto.

2. Insira as seguintes linhas no arquivo, substituindo `SEU_CLIENT_ID` e `SEU_SECRET_ID` pelos valores obtidos no [Spotify for Developers Dashboard](https://developer.spotify.com/dashboard):

CLIENT_ID= SEU_CLIENT_ID

SECRET_ID= SEU_SECRET_ID

### ----------------------------------------------------------------------------------------------

## Screenshots

![screenshot-app1](https://github.com/LucasPetros/SpotfyLab/assets/82883174/d81b4bc5-5691-4416-9e44-9548c284aa94)

![screnshot-app2](https://github.com/LucasPetros/SpotfyLab/assets/82883174/10d07557-1f9d-4fa6-b466-9ee487d7a212)

## Video

https://github.com/LucasPetros/SpotfyLab/assets/82883174/a4589b8a-7a3b-489b-8dcd-604cad243158

## Autor!

### Lucas Petros Borges da Silva
* Repositório: https://github.com/LucasPetros
* Linkedin: https://www.linkedin.com/in/Lucas-Petros/

Obrigado por demonstrar interesse no meu projeto. Seu envolvimento significa muito! 🤩
