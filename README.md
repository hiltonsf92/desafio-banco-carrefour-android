# :trophy: Desafio Banco Carrefour
## :dart: Objetivo

O desafio consiste na implementação de uma aplicação Android para consumir a API
pública do Github. [^1]

## :hammer_and_wrench: Funcionalidades

1. Listar todos os usuários.
2. Buscar informações referentes ao usuário bem como seus repositórios.
3. O sistema deve exibir feedbacks do status atual das requisições(Ok, Loading e Erro).

## :clipboard: Arquitetura

A arquitetura utilizada para organizar o app como um todo foi a Clean Architecture [^2] adaptada ao contexto mobile. O padrão arquitetural MVVM [^3] foi utilizado na camada de apresentação para desacoplar a view das regras da aplicação.

## :books: Bibliotecas

- Material 3 - construção do layout.
- Koin - injeção de dependência.
- Jetpack Navigation - navegação do app.
- Glide - carregamento assíncrono de imagens.
- Corrotinas - processamento assíncrono.
- Retrofit - requisições HTTP.

## :camera_flash: Telas do app

<div style="display: flex;">
  <img src="/images/user_list.jpg" style="width: 200px; height: 400px" alt="User list" />
  <img src="/images/user_detail.jpg" style="width: 200px; height: 400px" alt="User detail" />
  <img src="/images/error_message.jpg" style="width: 200px; height: 400px" alt="Error message" />
</div>

[^1]: [API Github.](https://developer.github.com/v3/)
[^2]: [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[^3]: [MVVM](https://learn.microsoft.com/pt-br/windows/uwp/data-binding/data-binding-and-mvvm)
