package br.unisul.contasapagar.service;

import br.unisul.contasapagar.model.Conta;
import br.unisul.contasapagar.repository.VetorContas;
import br.unisul.contasapagar.util.ConsoleUtil;

import java.time.LocalDate;

/**
 * Sub-rotinas referentes ao menu de Cadastro de Contas (Tela 1.1 e subtelas).
 */
public class CadastroService {

    private final VetorContas repositorio;

    public CadastroService(VetorContas repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * TELA 1.1 - Menu de Cadastro de Contas.
     */
    public void exibirMenuCadastro() {
        int opcao;
        do {
            ConsoleUtil.exibirCabecalho("CADASTRO DE CONTAS");
            System.out.println("  1 - INCLUSAO");
            System.out.println("  2 - ALTERACAO");
            System.out.println("  3 - CONSULTA");
            System.out.println("  4 - EXCLUSAO");
            System.out.println("  0 - RETORNAR");
            System.out.println();

            opcao = ConsoleUtil.lerOpcaoMenu("  OPCAO : ", 0, 4);

            switch (opcao) {
                case 1:
                    incluirConta();
                    break;
                case 2:
                    alterarConta();
                    break;
                case 3:
                    consultarConta();
                    break;
                case 4:
                    excluirConta();
                    break;
                case 0:
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * TELA 1.1.1 - Inclusão de Conta.
     */
    private void incluirConta() {
        boolean novaInclusao;
        do {
            ConsoleUtil.exibirCabecalho("INCLUSAO DE CONTA");

            if (repositorio.estaCheio()) {
                System.out.println("  [ERRO] Limite maximo de contas cadastradas atingido!");
                ConsoleUtil.aguardarEnter();
                return;
            }

            int documento;
            while (true) {
                documento = ConsoleUtil.lerDocumento("  DOCUMENTO    : ");
                if (repositorio.buscarPorDocumento(documento) == null) {
                    break;
                }
                System.out.println("  [ERRO] Ja existe uma conta cadastrada com este No. de Documento!");
            }

            String descricao = ConsoleUtil.lerTextoObrigatorio("  DESCRICAO    : ", "DESCRICAO");
            String beneficiario = ConsoleUtil.lerTextoObrigatorio("  BENEFICIARIO : ", "BENEFICIARIO");
            double valor = ConsoleUtil.lerValorPositivo("  VALOR        : ");
            LocalDate vencimento = ConsoleUtil.lerData("  VENCIMENTO   : ");

            System.out.println();
            boolean confirma = ConsoleUtil.lerConfirmacao("  CONFIRMA INCLUSAO ( S/N ) ? ");
            if (confirma) {
                Conta novaConta = new Conta(documento, descricao, beneficiario, valor, vencimento);
                repositorio.incluir(novaConta);
                System.out.println("  -> Conta incluida com sucesso!");
            } else {
                System.out.println("  -> Inclusao cancelada pelo usuario.");
            }

            System.out.println();
            novaInclusao = ConsoleUtil.lerConfirmacao("  NOVA INCLUSAO ( S/N ) ? ");
        } while (novaInclusao);
    }

    /**
     * TELA 1.1.2 - Alteração de Conta.
     */
    private void alterarConta() {
        boolean novaAlteracao;
        do {
            ConsoleUtil.exibirCabecalho("ALTERACAO DE CONTA");

            if (repositorio.estaVazio()) {
                System.out.println("  [AVISO] Nao ha contas cadastradas no sistema.");
                ConsoleUtil.aguardarEnter();
                return;
            }

            int documento = ConsoleUtil.lerDocumento("  DOCUMENTO    : ");
            Conta conta = repositorio.buscarPorDocumento(documento);

            if (conta == null) {
                System.out.println("  [ERRO] Conta com documento " + documento + " nao encontrada!");
            } else {
                System.out.println("\n  --- Dados Atuais da Conta ---");
                exibirDadosConta(conta);
                System.out.println("  (Obs: O No. do Documento nao pode ser alterado)");
                System.out.println("  --- Digite os Novos Dados ---");

                String novaDescricao = ConsoleUtil.lerTextoObrigatorio("  DESCRICAO    : ", "DESCRICAO");
                String novoBeneficiario = ConsoleUtil.lerTextoObrigatorio("  BENEFICIARIO : ", "BENEFICIARIO");
                double novoValor = ConsoleUtil.lerValorPositivo("  VALOR        : ");
                LocalDate novoVencimento = ConsoleUtil.lerData("  VENCIMENTO   : ");

                System.out.println();
                boolean confirma = ConsoleUtil.lerConfirmacao("  CONFIRMA ALTERACAO ( S/N ) ? ");
                if (confirma) {
                    conta.setDescricao(novaDescricao);
                    conta.setBeneficiario(novoBeneficiario);
                    conta.setValorOriginal(novoValor);
                    conta.setSaldoDevedor(novoValor);
                    conta.setDataVencimento(novoVencimento);
                    System.out.println("  -> Alteracao realizada com sucesso!");
                } else {
                    System.out.println("  -> Alteracao cancelada.");
                }
            }

            System.out.println();
            novaAlteracao = ConsoleUtil.lerConfirmacao("  NOVA ALTERACAO ( S/N ) ? ");
        } while (novaAlteracao);
    }

    /**
     * TELA 1.1.3 - Consulta de Conta.
     */
    private void consultarConta() {
        boolean novaConsulta;
        do {
            ConsoleUtil.exibirCabecalho("CONSULTA DE CONTA");

            if (repositorio.estaVazio()) {
                System.out.println("  [AVISO] Nao ha contas cadastradas no sistema.");
                ConsoleUtil.aguardarEnter();
                return;
            }

            int documento = ConsoleUtil.lerDocumento("  DOCUMENTO    : ");
            Conta conta = repositorio.buscarPorDocumento(documento);

            if (conta == null) {
                System.out.println("  [ERRO] Conta com documento " + documento + " nao esta cadastrada!");
            } else {
                exibirDadosConta(conta);
            }

            System.out.println();
            novaConsulta = ConsoleUtil.lerConfirmacao("  NOVA CONSULTA ( S/N ) ? ");
        } while (novaConsulta);
    }

    /**
     * TELA 1.1.4 - Exclusão de Conta.
     */
    private void excluirConta() {
        boolean novaExclusao;
        do {
            ConsoleUtil.exibirCabecalho("EXCLUSAO DE CONTA");

            if (repositorio.estaVazio()) {
                System.out.println("  [AVISO] Nao ha contas cadastradas no sistema.");
                ConsoleUtil.aguardarEnter();
                return;
            }

            int documento = ConsoleUtil.lerDocumento("  DOCUMENTO    : ");
            Conta conta = repositorio.buscarPorDocumento(documento);

            if (conta == null) {
                System.out.println("  [ERRO] Conta com documento " + documento + " nao esta cadastrada!");
            } else {
                exibirDadosConta(conta);
                System.out.println();
                boolean confirma = ConsoleUtil.lerConfirmacao("  CONFIRMA EXCLUSAO ( S/N ) ? ");
                if (confirma) {
                    repositorio.excluir(documento);
                    System.out.println("  -> Conta excluida com sucesso!");
                } else {
                    System.out.println("  -> Exclusao cancelada.");
                }
            }

            System.out.println();
            novaExclusao = ConsoleUtil.lerConfirmacao("  NOVA EXCLUSAO ( S/N ) ? ");
        } while (novaExclusao);
    }

    /**
     * Sub-rotina auxiliar para exibir os campos de uma conta.
     */
    private void exibirDadosConta(Conta conta) {
        System.out.println("  DESCRICAO    : " + conta.getDescricao());
        System.out.println("  BENEFICIARIO : " + conta.getBeneficiario());
        System.out.println("  VALOR        : " + ConsoleUtil.formatarMoeda(conta.getSaldoDevedor()));
        System.out.println("  VENCIMENTO   : " + ConsoleUtil.formatarData(conta.getDataVencimento()));
    }
}
