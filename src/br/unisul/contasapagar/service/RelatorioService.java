package br.unisul.contasapagar.service;

import br.unisul.contasapagar.model.Conta;
import br.unisul.contasapagar.repository.VetorContas;
import br.unisul.contasapagar.util.ConsoleUtil;

import java.time.LocalDate;

/**
 * Sub-rotinas referentes à emissão dos Relatórios no vídeo (Tela 1.3).
 */
public class RelatorioService {

    private static final int LINHAS_POR_PAGINA = 15;
    private static final String[] NOMES_MESES = {
        "JANEIRO", "FEVEREIRO", "MARCO", "ABRIL", "MAIO", "JUNHO",
        "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"
    };

    private final VetorContas repositorio;

    public RelatorioService(VetorContas repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * TELA 1.3 - Menu de Relatórios.
     */
    public void exibirMenuRelatorios() {
        int opcao;
        do {
            ConsoleUtil.exibirCabecalho("RELATORIOS");
            System.out.println("  1 - CONTAS A PAGAR");
            System.out.println("  2 - CONTAS ATRASADAS");
            System.out.println("  3 - BALANCO DE DESPESAS");
            System.out.println("  0 - RETORNAR");
            System.out.println();

            opcao = ConsoleUtil.lerOpcaoMenu("  OPCAO : ", 0, 3);

            switch (opcao) {
                case 1:
                    relatorioContasAPagarPorPeriodo();
                    break;
                case 2:
                    relatorioContasVencidasPorPeriodo();
                    break;
                case 3:
                    relatorioBalancoDespesas();
                    break;
                case 0:
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Relatório 1: Contas a Pagar por Período (ordenadas por data de vencimento).
     */
    private void relatorioContasAPagarPorPeriodo() {
        ConsoleUtil.exibirCabecalho("PARAMETROS - CONTAS A PAGAR POR PERIODO");
        LocalDate dataInicial = ConsoleUtil.lerData("  DATA INICIAL (DD/MM/AA) : ");
        LocalDate dataFinal;
        while (true) {
            dataFinal = ConsoleUtil.lerData("  DATA FINAL   (DD/MM/AA) : ");
            if (!dataFinal.isBefore(dataInicial)) {
                break;
            }
            System.out.println("  [ERRO] A data final nao pode ser anterior a data inicial.");
        }

        Conta[] ordenadas = repositorio.obterContasOrdenadasPorVencimento();
        int pagina = 1;
        int linhasNaPagina = 0;
        boolean encontrou = false;

        imprimirCabecalhoListagem("CONTAS A PAGAR POR PERIODO", pagina);

        for (int i = 0; i < ordenadas.length; i++) {
            Conta c = ordenadas[i];
            boolean dentroDoPeriodo = !c.getDataVencimento().isBefore(dataInicial)
                    && !c.getDataVencimento().isAfter(dataFinal);

            if (c.getSaldoDevedor() > 0.0 && dentroDoPeriodo) {
                if (linhasNaPagina >= LINHAS_POR_PAGINA) {
                    pagina++;
                    linhasNaPagina = 0;
                    imprimirCabecalhoListagem("CONTAS A PAGAR POR PERIODO", pagina);
                }
                imprimirLinhaConta(c);
                linhasNaPagina++;
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("  Nenhuma conta a pagar encontrada no periodo informado.");
        }
        System.out.println("----------------------------------------------------------------------");
        ConsoleUtil.aguardarEnter();
    }

    /**
     * Relatório 2: Contas Vencidas por Período (atrasadas em relação à data de referência e dentro do período).
     */
    private void relatorioContasVencidasPorPeriodo() {
        ConsoleUtil.exibirCabecalho("PARAMETROS - CONTAS VENCIDAS POR PERIODO");
        LocalDate dataInicial = ConsoleUtil.lerData("  DATA INICIAL (DD/MM/AA)        : ");
        LocalDate dataFinal;
        while (true) {
            dataFinal = ConsoleUtil.lerData("  DATA FINAL   (DD/MM/AA)        : ");
            if (!dataFinal.isBefore(dataInicial)) {
                break;
            }
            System.out.println("  [ERRO] A data final nao pode ser anterior a data inicial.");
        }
        LocalDate dataReferencia = ConsoleUtil.lerData("  DATA DE REFERENCIA (HOJE)      : ");

        Conta[] ordenadas = repositorio.obterContasOrdenadasPorVencimento();
        int pagina = 1;
        int linhasNaPagina = 0;
        boolean encontrou = false;

        imprimirCabecalhoListagem("CONTAS A VENCIDAS", pagina);

        for (int i = 0; i < ordenadas.length; i++) {
            Conta c = ordenadas[i];
            boolean dentroDoPeriodo = !c.getDataVencimento().isBefore(dataInicial)
                    && !c.getDataVencimento().isAfter(dataFinal);
            boolean estaVencida = c.getDataVencimento().isBefore(dataReferencia);

            if (c.getSaldoDevedor() > 0.0 && dentroDoPeriodo && estaVencida) {
                if (linhasNaPagina >= LINHAS_POR_PAGINA) {
                    pagina++;
                    linhasNaPagina = 0;
                    imprimirCabecalhoListagem("CONTAS A VENCIDAS", pagina);
                }
                imprimirLinhaConta(c);
                linhasNaPagina++;
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("  Nenhuma conta vencida encontrada para o periodo informado.");
        }
        System.out.println("----------------------------------------------------------------------");
        ConsoleUtil.aguardarEnter();
    }

    /**
     * Relatório 3: Balanço de Despesas no Período (Janeiro a Dezembro de um ano).
     */
    private void relatorioBalancoDespesas() {
        ConsoleUtil.exibirCabecalho("PARAMETROS - BALANCO DE DESPESAS");
        int ano = ConsoleUtil.lerAno("  ANO DE REFERENCIA (AAAA) : ");

        Conta[] todas = repositorio.obterContasOrdenadasPorVencimento();

        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("SISTEMA DE CONTAS A PAGAR");
        System.out.println();
        System.out.printf("%-10s          BALANCO DE DESPESAS NO PERIODO              PG %03d%n",
                ConsoleUtil.formatarData(LocalDate.now()), 1);
        System.out.printf("                    JANEIRO A DEZEMBRO DE %04d%n", ano);
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-12s %18s   %-25s%n", "MES", "TOTAL DE DESPESAS", "MAIOR BENEFICIARIO");
        System.out.println("----------------------------------------------------------------------");

        for (int mes = 1; mes <= 12; mes++) {
            double totalMes = 0.0;

            // Vetores auxiliares para acumular o total por beneficiário no mês
            String[] beneficiariosMes = new String[todas.length];
            double[] totaisPorBeneficiario = new double[todas.length];
            int qtdBeneficiariosDistintos = 0;

            for (int i = 0; i < todas.length; i++) {
                Conta c = todas[i];
                if (c.getDataVencimento().getYear() == ano && c.getDataVencimento().getMonthValue() == mes) {
                    double valorConsiderado = c.getValorOriginal();
                    totalMes += valorConsiderado;

                    int idxBenef = -1;
                    for (int b = 0; b < qtdBeneficiariosDistintos; b++) {
                        if (beneficiariosMes[b].equalsIgnoreCase(c.getBeneficiario())) {
                            idxBenef = b;
                            break;
                        }
                    }

                    if (idxBenef == -1) {
                        beneficiariosMes[qtdBeneficiariosDistintos] = c.getBeneficiario();
                        totaisPorBeneficiario[qtdBeneficiariosDistintos] = valorConsiderado;
                        qtdBeneficiariosDistintos++;
                    } else {
                        totaisPorBeneficiario[idxBenef] += valorConsiderado;
                    }
                }
            }

            String maiorBeneficiario = "-";
            double maiorValor = 0.0;
            for (int b = 0; b < qtdBeneficiariosDistintos; b++) {
                if (totaisPorBeneficiario[b] > maiorValor) {
                    maiorValor = totaisPorBeneficiario[b];
                    maiorBeneficiario = beneficiariosMes[b];
                }
            }

            System.out.printf("%-12s %18s   %-25s%n",
                    NOMES_MESES[mes - 1],
                    ConsoleUtil.formatarMoeda(totalMes),
                    ConsoleUtil.ajustarTamanhoTexto(maiorBeneficiario, 25));
        }

        System.out.println("----------------------------------------------------------------------");
        ConsoleUtil.aguardarEnter();
    }

    private void imprimirCabecalhoListagem(String tituloRelatorio, int pagina) {
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("SISTEMA DE CONTAS A PAGAR");
        System.out.println();
        System.out.printf("%-10s             %-32s         PG %03d%n",
                ConsoleUtil.formatarData(LocalDate.now()), tituloRelatorio, pagina);
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s %10s %9s %-20s %-20s%n",
                "VENCIMENTO", "VALOR", "DOCUMENTO", "DESCRICAO", "BENEFICIARIO");
        System.out.println("----------------------------------------------------------------------");
    }

    private void imprimirLinhaConta(Conta c) {
        System.out.printf("%-10s %10s %9d %-20s %-20s%n",
                ConsoleUtil.formatarData(c.getDataVencimento()),
                ConsoleUtil.formatarMoeda(c.getSaldoDevedor()),
                c.getDocumento(),
                ConsoleUtil.ajustarTamanhoTexto(c.getDescricao(), 20),
                ConsoleUtil.ajustarTamanhoTexto(c.getBeneficiario(), 20));
    }
}
