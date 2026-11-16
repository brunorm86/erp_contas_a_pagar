# Sistema de Contas a Pagar - XYZ Comércio de Produtos Ltda.

Projeto desenvolvido para a A3 da Unidade Curricular **Algoritmos e Programação** (UNISUL).
Professores: Osmar de Oliveira Braz Júnior e Ricardo Ribeiro Assink.

## 1. Contextualização

O sistema realiza o controle de **Contas a Pagar** da empresa fictícia **XYZ Comércio de Produtos Ltda.**, permitindo o gerenciamento diário das despesas periódicas e esporádicas por meio de operações de cadastro, movimentação (baixas parciais ou totais) e emissão de relatórios gerenciais em vídeo.

## 2. Funcionalidades Implementadas

- **Cadastro de Contas (`TELA 1.1`)**:
  - **Inclusão (`TELA 1.1.1`)**: Cadastro de novas contas com validação de número de documento único, descrição, beneficiário, valor positivo e data de vencimento válida.
  - **Alteração (`TELA 1.1.2`)**: Atualização dos dados de uma conta já cadastrada (mantendo o número do documento inalterado).
  - **Consulta (`TELA 1.1.3`)**: Pesquisa e exibição dos dados de uma conta pelo número do documento.
  - **Exclusão (`TELA 1.1.4`)**: Remoção de uma conta mediante confirmação do usuário e reorganização do vetor.
- **Movimentação - Baixa de Contas (`TELA 1.2.2`)**:
  - Pagamento parcial ou total de uma conta cadastrada, validando o valor pago e atualizando o saldo devedor.
- **Relatórios em Vídeo (`TELA 1.3`)**:
  - **Contas a Pagar por Período**: Relação das contas em aberto no período informado, em ordem crescente de data de vencimento.
  - **Contas Vencidas por Período**: Relação das contas em atraso no período informado, em ordem crescente de data de vencimento.
  - **Balanço de Despesas no Período**: Resumo mensal (Janeiro a Dezembro) do ano selecionado, apresentando o total de despesas de cada mês e o maior beneficiário.

## 3. Estrutura do Repositório

```text
erp_contas_a_pagar/
├── LICENSE
├── README.md
├── .gitignore
└── src/
    └── br/
        └── unisul/
            └── contasapagar/
                ├── Main.java
                ├── model/
                │   └── Conta.java
                ├── repository/
                │   └── VetorContas.java
                ├── service/
                │   ├── CadastroService.java
                │   ├── BaixaService.java
                │   └── RelatorioService.java
                └── util/
                    └── ConsoleUtil.java
```

## 4. Como Compilar e Executar

### Pré-requisitos
- Java JDK 11 ou superior instalado (`java` e `javac`).

### Compilação e Execução via Terminal
Na pasta raiz do projeto (`erp_contas_a_pagar`), execute:

```bash
mkdir -p bin
javac -d bin src/br/unisul/contasapagar/model/Conta.java \
              src/br/unisul/contasapagar/repository/VetorContas.java \
              src/br/unisul/contasapagar/util/ConsoleUtil.java \
              src/br/unisul/contasapagar/service/CadastroService.java \
              src/br/unisul/contasapagar/service/BaixaService.java \
              src/br/unisul/contasapagar/service/RelatorioService.java \
              src/br/unisul/contasapagar/Main.java
java -cp bin br.unisul.contasapagar.Main
```
