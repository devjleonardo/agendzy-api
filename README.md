# Agendzy

A gestão da sua barbearia na palma da mão

## Pré-requisitos

Certifique-se de ter o Docker e o Docker Compose instalados na sua máquina.

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Instruções de Uso

### 1. Clone o repositório:

```bash
git clone https://github.com/devjleonardo/agendzy-api.git
cd agendzy-api
```

### 2. Iniciar os Serviços com Docker Compose e Construir as Imagens

```git
docker-compose up -d --build
```

### 3. Listar Containers em Execução

```git
docker ps
```

Este comando exibe uma lista dos containers em execução, com informações como ID, nomes, portas expostas, etc.

### Acesso

A api estará disponível em `localhost:8080`.

O banco de dados PostgreSQL estará disponível em `localhost:5433`.

### Credenciais de Acesso

- **Cliente:**
  - **Email:** lucas@gmail.com
  - **Senha:** 123

### Notas Adicionais

- Este README assume que você está no mesmo diretório que o arquivo docker-compose.yml.
