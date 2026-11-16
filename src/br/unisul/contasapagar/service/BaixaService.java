package br.unisul.contasapagar.service;

import br.unisul.contasapagar.model.Conta;
import br.unisul.contasapagar.repository.VetorContas;
import br.unisul.contasapagar.util.ConsoleUtil;

/**
 * Sub-rotinas referentes à Movimentação - Baixa de Conta (Tela 1.2.2).
 */
public class BaixaService {

    private final VetorContas repositorio;

    public BaixaService(VetorContas repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * TELA 1.2.2 - Movimentação - Baixa de Conta (parcial ou total).
     */
    public void realizarBaixa() {
        boolean novaBaixa;
        do {
            ConsoleUtil.exibirCabecalho("MOVIMENTACAO - BAIXA DE CONTA");

            if (repositorio.estaVazio()) {
                System.out.println("  [AVISO] Nao ha contas cadastradas no sistema.");
                ConsoleUtil.aguardarEnter();
                return;
            }

            int documento = ConsoleUtil.lerDocumento("  DOCUMENTO   : ");
            Conta conta = repositorio.buscarPorDocumento(documento);

            if (conta == null) {
                System.out.println("  [ERRO] Conta com documento " + documento + " nao encontrada!");
            } else if (conta.getSaldoDevedor() <= 0.0) {
                System.out.println("  [AVISO] Esta conta ja se encontra totalmente quitada (Saldo = 0,00).");
            } else {
                System.out.println("  DESCRICAO   : " + conta.getDescricao());
                System.out.println("  BENEFICIARIO: " + conta.getBeneficiario());
                System.out.println("  VENCIMENTO  : " + ConsoleUtil.formatarData(conta.getDataVencimento()));
                System.out.println("  VALOR ATUAL : " + ConsoleUtil.formatarMoeda(conta.getSaldoDevedor()));

                double valorPago = ConsoleUtil.lerValorPagamento("  VALOR PAGO  : ", conta.getSaldoDevedor());
                double valorFinal = Math.max(0.0, Math.round((conta.getSaldoDevedor() - valorPago) * 100.0) / 100.0);

                System.out.println("  VALOR FINAL : " + ConsoleUtil.formatarMoeda(valorFinal));
                System.out.println();

                boolean confirma = ConsoleUtil.lerConfirmacao("  CONFIRMA BAIXA ( S/N ) ? ");
                if (confirma) {
                    conta.setSaldoDevedor(valorFinal);
                    System.out.println("  -> Baixa registrada com sucesso!");
                } else {
                    System.out.println("  -> Operacao de baixa cancelada.");
                }
            }

            System.out.println();
            novaBaixa = ConsoleUtil.lerConfirmacao("  NOVA BAIXA ( S/N ) ? ");
        } while (novaBaixa);
    }
}
