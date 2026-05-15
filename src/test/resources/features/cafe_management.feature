# language: pt
Funcionalidade: Gerenciamento de Cafés
  Como um barista
  Eu quero gerenciar os cafés disponíveis
  Para que eu possa registrar novas receitas com eles

  Cenário: Listar cafés cadastrados
    Dado que existem os seguintes cafés cadastrados:
      | nome           | marca_torrefacao | origem  | nivel_torra |
      | Café Especial  | Torra Forte      | Brasil  | Média       |
      | Ethiopian Soul | Roasters inc     | Etiópia | Clara       |
    Quando eu acesso a lista de cafés
    Então eu devo ver "Café Especial" na lista
    E eu devo ver "Ethiopian Soul" na lista

  Cenário: Cadastrar um novo café com sucesso
    Dado que eu estou na página de cadastro de café
    Quando eu preencho o nome com "Colômbia Excelso"
    E a marca com "Café do Povo"
    E a origem com "Colômbia"
    E o nível de torra com "Escura"
    E eu clico em salvar
    Então o café "Colômbia Excelso" deve estar salvo no banco de dados

  Cenário: Impedir exclusão de café vinculado a receita
    Dado que existe um café chamado "Café Em Uso" associado a uma receita
    Quando eu tento excluir o café com nome "Café Em Uso"
    Então a exclusão deve ser bloqueada com mensagem de erro
