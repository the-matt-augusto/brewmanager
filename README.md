# Brewmanager

Brewmanager é uma aplicação Spring Boot moderna projetada para entusiastas de café que desejam registrar, gerenciar e aperfeiçoar suas receitas de preparo.

## Propósito do Projeto

O objetivo do Brewmanager é fornecer uma ferramenta simples, porém robusta, para o controle de inventário de cafés e o registro detalhado de métodos de infusão. O sistema permite que o usuário acompanhe diferentes grãos, torrefações e receitas, ajudando a encontrar o "ratio" perfeito para cada xícara.

## O que o sistema faz

- **Gestão de Cafés**: Cadastro detalhado de grãos, incluindo marca, origem e nível de torra.
- **Métodos de Infusão**: Registro de diferentes formas de preparo (V60, Aeropress, Prensa Francesa, etc.).
- **Diário de Receitas**: Criação de receitas vinculando cafés e métodos, com controle de:
    - Ratio (proporção pó/água).
    - Tempo de infusão.
    - Nota sensorial (avaliação de 1 a 5).
- **Interface Web**: Interface amigável utilizando Thymeleaf para renderização server-side.
- **Persistência Confiável**: Utiliza SQLite para um armazenamento leve e eficiente, sem necessidade de configuração complexa de banco de dados.

## Requisitos para Execução

Para rodar o projeto localmente, você precisará de:

- **Java 17** ou superior.
- **Maven 3.6+** (ou utilizar o Maven Wrapper incluso no projeto).
- Um navegador web moderno.

### Como rodar:

1. Clone o repositório.
2. Na raiz do projeto, execute:
   ```powershell
   .\mvnw spring-boot:run
   ```
3. Acesse `http://localhost:8080` no seu navegador.

### Executando Testes:

Para garantir que tudo está funcionando corretamente:
```powershell
.\mvnw test
```

## Licença

Este projeto está licenciado sob a **Licença MIT**. Para mais detalhes, consulte o arquivo [LICENSE.md](LICENSE.md).

---
*Desenvolvido com Spring Boot, Thymeleaf e SQLite.*
