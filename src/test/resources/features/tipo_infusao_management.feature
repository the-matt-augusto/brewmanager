# language: pt
Funcionalidade: Gerenciamento de Métodos de Infusão
  Como um barista
  Eu quero gerenciar os métodos de infusão disponíveis
  Para poder registrar receitas com diferentes preparos

  Cenário: Listar métodos de infusão cadastrados
    Dado que existem os seguintes métodos de infusão cadastrados:
      | nome      | descricao                        |
      | V60       | Coagem por gotejamento           |
      | Aeropress | Extração por pressão de ar       |
    Quando eu acesso a lista de métodos de infusão
    Então eu devo ver o método "V60" na lista
    E eu devo ver o método "Aeropress" na lista

  Cenário: Cadastrar um novo método de infusão
    Dado que eu estou na página de cadastro de método
    Quando eu cadastro o método com nome "Chemex" e descrição "Coagem com filtro grosso"
    Então o método "Chemex" deve estar salvo

  Cenário: Impedir exclusão de método de infusão vinculado a receita
    Dado que existe um método de infusão chamado "V60 Em Uso" associado a uma receita
    Quando eu tento excluir o método com nome "V60 Em Uso"
    Então a exclusão do método deve ser bloqueada com mensagem de erro
