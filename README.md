# Sistema de Contas a Pagar - XYZ Comércio de Produtos Ltda.

Projeto desenvolvido para a avaliação **A3 – Desempenho de Compreensão** da Unidade Curricular **Algoritmos e Programação** da **Universidade do Sul de Santa Catarina (UNISUL)**.

- **Professores:** Osmar de Oliveira Braz Júnior e Ricardo Ribeiro Assink

---

## 1. Contextualização

O software atende à necessidade de controle diário das **Contas a Pagar** da empresa fictícia **XYZ Comércio de Produtos Ltda.**, permitindo cadastrar despesas periódicas e esporádicas, registrar baixas parciais ou totais atualizando o saldo devedor e emitir relatórios gerenciais em vídeo para apoio ao planejamento financeiro.

Cada conta armazena as seguintes informações em vetor (`Conta[]`):
- **Nº do Documento** (`int`)
- **Descrição** (`String`)
- **Beneficiário** (`String`)
- **Valor do Débito / Saldo Devedor** (`double`)
- **Data de Vencimento** (`LocalDate` no formato `dd/MM/aa` ou `dd/MM/aaaa`)

---

## 2. Ambiente de Desenvolvimento

- **Sistema Operacional:** Linux Ubuntu 22.04 LTS (`x86_64`)
- **Linguagem / JDK:** Java OpenJDK 25 (`25.0.4.1+1-1-22.04.4-Ubuntu`)
- **Paradigma / Recursos:** Sub-rotinas (procedimentos e funções), armazenamento em vetores, interface de console interativa com validação completa de entradas.
- **Controle de Versão:** Git / GitHub (branch `main`)

---

## 3. Funcionalidades e Relação de Telas

| Tela | Título | Funcionalidade / Descrição | Classe Responsável |
| :--- | :--- | :--- | :--- |
| **TELA 1.0** | `MENU PRINCIPAL` | Acesso aos módulos de Cadastro, Baixa, Relatórios e Finalização do sistema. | `Main.java` |
| **TELA 1.1** | `CADASTRO DE CONTAS` | Submenu das operações de Inclusão, Alteração, Consulta e Exclusão. | `CadastroService.java` |
| **TELA 1.1.1** | `INCLUSÃO DE CONTA` | Cadastro de nova conta com validação de documento único, valor positivo, data válida e confirmação (`S/N`). | `CadastroService.java` |
| **TELA 1.1.2** | `ALTERAÇÃO DE CONTA` | Busca pelo Nº do Documento, exibe dados atuais e atualiza Descrição, Beneficiário, Valor e Vencimento mediante confirmação. | `CadastroService.java` |
| **TELA 1.1.3** | `CONSULTA DE CONTA` | Pesquisa pelo Nº do Documento no vetor e exibe todos os dados cadastrados. | `CadastroService.java` |
| **TELA 1.1.4** | `EXCLUSÃO DE CONTA` | Pesquisa pelo Nº do Documento, exibe os dados e remove o registro do vetor mediante confirmação. | `CadastroService.java` |
| **TELA 1.2.2** | `MOVIMENTAÇÃO - BAIXA DE CONTA` | Registra o pagamento parcial ou total de uma conta, validando se `0 < valorPago <= valorAtual` e atualizando o saldo devedor. | `BaixaService.java` |
| **TELA 1.3** | `RELATÓRIOS` | Submenu e emissão em vídeo dos 3 relatórios: **Contas a Pagar por Período**, **Contas Vencidas por Período** e **Balanço de Despesas no Período**. | `RelatorioService.java` |

---

## 4. Estrutura do Repositório

```text
erp_contas_a_pagar/
├── LICENSE
├── README.md
├── .gitignore
├── bin/                                    # Bytecodes (.class) compilados com OpenJDK 25
└── src/
    └── br/
        └── unisul/
            └── contasapagar/
                ├── Main.java               # Menu Principal (Tela 1.0)
                ├── model/
                │   └── Conta.java          # Entidade de Conta a Pagar
                ├── repository/
                │   └── VetorContas.java    # Manipulação e ordenação do vetor de contas
                ├── service/
                │   ├── CadastroService.java # Inclusão, Alteração, Consulta e Exclusão
                │   ├── BaixaService.java    # Baixa parcial ou total de contas
                │   └── RelatorioService.java# Relatórios gerenciais em vídeo
                └── util/
                    └── ConsoleUtil.java    # Sub-rotinas de leitura, validação e formatação
```

---

## 5. Como Compilar e Executar

### Pré-requisitos
Verificar se o **OpenJDK 25** está ativo no terminal:

```bash
java -version
javac -version
```

### Compilação
Na pasta raiz do projeto (`erp_contas_a_pagar`), execute:

```bash
mkdir -p bin
javac -Xlint:all -d bin \
    src/br/unisul/contasapagar/model/Conta.java \
    src/br/unisul/contasapagar/repository/VetorContas.java \
    src/br/unisul/contasapagar/util/ConsoleUtil.java \
    src/br/unisul/contasapagar/service/CadastroService.java \
    src/br/unisul/contasapagar/service/BaixaService.java \
    src/br/unisul/contasapagar/service/RelatorioService.java \
    src/br/unisul/contasapagar/Main.java
```

### Execução
```bash
java -cp bin br.unisul.contasapagar.Main
```

---

## 6. Distribuição das Tarefas entre a Equipe

| Nome do Aluno | Funcionalidade Desenvolvida | Arquivo(s) Criado(s) / Modificado(s) |
| :--- | :--- | :--- |
| Integrante 1 | Menu Principal, Modelo de Dados e Armazenamento em Vetor | `src/br/unisul/contasapagar/Main.java`, `src/br/unisul/contasapagar/model/Conta.java`, `src/br/unisul/contasapagar/repository/VetorContas.java` |
| Integrante 2 | Sub-rotinas Utilitárias de Entrada, Formatação e Validação de Erros | `src/br/unisul/contasapagar/util/ConsoleUtil.java` |
| Integrante 3 | Cadastro de Contas (Inclusão, Alteração, Consulta e Exclusão) | `src/br/unisul/contasapagar/service/CadastroService.java` |
| Integrante 4 | Movimentação – Baixa Parcial e Total de Contas | `src/br/unisul/contasapagar/service/BaixaService.java` |
| Integrante 5 | Relatórios (Contas a Pagar, Contas Vencidas e Balanço de Despesas) | `src/br/unisul/contasapagar/service/RelatorioService.java` |
