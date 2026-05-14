# Configuração PostgreSQL para Render

## Como escolher entre SQLite e PostgreSQL

### Desenvolvimento Local (SQLite - padrão)
```bash
mvn spring-boot:run
# Usa application.properties com SQLite
# Dados salvos em cafeapp.db localmente
```

### Produção com PostgreSQL
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
# Usa application-prod.properties com PostgreSQL
```

## Configurar no Render

### 1. Criar o banco de dados PostgreSQL
- Acessar [render.com](https://render.com)
- Dashboard → New → PostgreSQL
- Nome: `brewmanager-db` (ou similar)
- Região: mesma da aplicação
- Plano: Free

### 2. Anotar as credenciais
Após criar, você verá:
- **Internal Database URL** (use isso para `DB_HOST`)
- **User** (padrão: `postgres` ou o que você criou)
- **Password**
- **Database** (padrão ou customizado)

Exemplo:
```
dpg-xxxxxx.render.internal
postgres
seu_password_aqui
brewmanager
```

### 3. Adicionar variáveis de ambiente no serviço da aplicação

No dashboard do serviço:
- Settings → Environment
- Adicionar:
```
SPRING_PROFILES_ACTIVE=prod
DB_HOST=dpg-xxxxxx.render.internal
DB_PORT=5432
DB_NAME=brewmanager
DB_USER=postgres
DB_PASSWORD=seu_password_aqui
```

### 4. Deploy
```bash
git add .
git commit -m "Add PostgreSQL support for production"
git push
```

O Render vai:
1. Detectar mudanças
2. Fazer build
3. Usar variáveis de ambiente
4. Conectar ao PostgreSQL automaticamente

## Testar localmente com PostgreSQL (usando Docker)

Se quiser testar a config prod localmente:

```bash
# Criar container PostgreSQL
docker run --name brewmanager-postgres \
  -e POSTGRES_DB=brewmanager \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16

# Rodar a aplicação com profile prod
SPRING_PROFILES_ACTIVE=prod \
DB_HOST=localhost \
DB_USER=postgres \
DB_PASSWORD=postgres \
DB_NAME=brewmanager \
mvn spring-boot:run
```

## Resumo das configurações

| Aspecto | SQLite (dev) | PostgreSQL (prod) |
|---------|-------------|-------------------|
| Arquivo config | application.properties | application-prod.properties |
| Comando | `mvn spring-boot:run` | `mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"` |
| Var ambiental | Nenhuma | `SPRING_PROFILES_ACTIVE=prod` |
| Dados | Locais (cafeapp.db) | Persistidos na nuvem |
| Ideal para | Desenvolvimento | Produção (Render) |

## Próximos passos (opcional)

Se precisar de migrações automáticas com Flyway:
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

Por enquanto, `ddl-auto=update` cria/altera tabelas automaticamente.
