# language: pt
Funcionalidade: Gerenciamento de Receitas de Preparo
  Como um barista
  Eu quero registrar minhas receitas de café
  Para acompanhar meu processo de degustação

  Cenário: Cadastrar uma receita válida
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu cadastro uma receita com ratio "1:15", tempo 180 e nota 4
    Então a receita deve estar salva no banco de dados

  Cenário: Rejeitar receita com nota sensorial inválida
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu tento cadastrar uma receita com nota sensorial 6
    Então a receita não deve ser salva
    E o sistema deve exibir o erro "Nota sensorial deve ser entre 1 e 5."

  Cenário: Rejeitar receita com ratio inválido
    Dado que existe um café chamado "Bourbon Amarelo" para a receita
    E que existe um método chamado "V60" para a receita
    Quando eu tento cadastrar uma receita com ratio "invalido"
    Então a receita não deve ser salva
    E o sistema deve exibir o erro "Ratio inválido. Use o formato 1:15."
