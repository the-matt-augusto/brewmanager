# language: pt
Funcionalidade: Gerenciamento de Receitas de Preparo
  Como um barista
  Eu quero registrar minhas receitas de café
  Para acompanhar meu processo de degustação

  Cenário: Cadastrar uma receita válida
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu cadastro uma receita com proporção "1:15", tempo 180 e nota 4
    Então a receita deve estar salva no banco de dados

  Cenário: Adicionar receita com múltiplos ingredientes
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu cadastro uma receita com proporção "1:15", tempo 180 e nota 4
    E eu adiciono o ingrediente "açúcar" com quantidade "2 colheres"
    E eu adiciono o ingrediente "leite" com quantidade "100ml"
    Então a receita é salva com 2 ingredientes

  Cenário: Rejeitar receita com nota sensorial inválida
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu tento cadastrar uma receita com nota sensorial 6
    Então a receita não deve ser salva
    E o sistema deve exibir o erro "Nota sensorial deve ser entre 1 e 5."

  Cenário: Rejeitar receita com proporção inválida
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu tento cadastrar uma receita com proporção "invalido"
    Então a receita não deve ser salva
    E o sistema deve exibir o erro "Proporção inválida. Use o formato 1:15."

  Cenário: Cadastrar receita com observações
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu cadastro uma receita com proporção "1:15", tempo 180, nota 4 e observações "Agua a 92 graus"
    Então a receita deve estar salva no banco de dados
    E as observações "Agua a 92 graus" devem estar salvas na receita
