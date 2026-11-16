package br.unisul.contasapagar;

import br.unisul.contasapagar.repository.VetorContas;
import br.unisul.contasapagar.service.BaixaService;
import br.unisul.contasapagar.service.CadastroService;
import br.unisul.contasapagar.service.RelatorioService;
import br.unisul.contasapagar.util.ConsoleUtil;

/**
 * Classe principal do Sistema de Contas a Pagar - XYZ Comércio de Produtos Ltda.
 * Unidade Curricular: Algoritmos e Programação - UNISUL (A3).
 */
public class Main {

    public static void main(String[] args) {
        VetorContas repositorio = new VetorContas();
        CadastroService cadastroService = new CadastroService(repositorio);
        BaixaService baixaService = new BaixaService(repositorio);
        RelatorioService relatorioService = new RelatorioService(repositorio);

        int opcao;
        do {
            ConsoleUtil.exibirCabecalho("MENU PRINCIPAL");
            System.out.println("  1 - CADASTRO DE CONTAS");
            System.out.println("  2 - BAIXA DE CONTAS");
            System.out.println("  3 - RELATORIOS");
            System.out.println("  0 - FINALIZAR");
            System.out.println();

            opcao = ConsoleUtil.lerOpcaoMenu("  OPCAO : ", 0, 3);

            switch (opcao) {
                case 1:
                    cadastroService.exibirMenuCadastro();
                    break;
                case 2:
                    baixaService.realizarBaixa();
                    break;
                case 3:
                    relatorioService.exibirMenuRelatorios();
                    break;
                case 0:
                    System.out.println("\n  Sistema finalizado. Obrigado!");
                    break;
            }
        } while (opcao != 0);
    }
}
