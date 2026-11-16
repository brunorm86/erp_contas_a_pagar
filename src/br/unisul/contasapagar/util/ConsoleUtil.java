package br.unisul.contasapagar.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;

/**
 * Sub-rotinas utilitárias para interação com o usuário via console,
 * formatação de dados e validação de todas as condições de erro possíveis.
 */
public class ConsoleUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final DateTimeFormatter FORMATO_EXIBICAO_DATA = DateTimeFormatter.ofPattern("dd/MM/yy");
    private static final DateTimeFormatter FORMATO_ENTRADA_8_DIGITOS = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMATO_ENTRADA_6_DIGITOS = DateTimeFormatter.ofPattern("dd/MM/uu")
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DecimalFormat FORMATO_MOEDA;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.of("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        FORMATO_MOEDA = new DecimalFormat("#,##0.00", symbols);
    }

    /**
     * Exibe o cabeçalho padrão das telas do sistema.
     *
     * @param tituloTela Título da tela atual.
     */
    public static void exibirCabecalho(String tituloTela) {
        System.out.println();
        System.out.println("======================================================================");
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("SISTEMA DE CONTAS A PAGAR");
        System.out.println();
        System.out.println("          " + tituloTela);
        System.out.println("======================================================================");
    }

    /**
     * Lê uma opção numérica inteira dentro de um intervalo permitido.
     */
    public static int lerOpcaoMenu(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim();
            try {
                int opcao = Integer.parseInt(entrada);
                if (opcao >= min && opcao <= max) {
                    return opcao;
                }
                System.out.println("  [ERRO] Opcao invalida! Digite um numero entre " + min + " e " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Entrada invalida! Digite apenas numeros inteiros.");
            }
        }
    }

    /**
     * Lê e valida o número de um documento (inteiro positivo entre 1 e 99999).
     */
    public static int lerDocumento(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim();
            try {
                int doc = Integer.parseInt(entrada);
                if (doc > 0 && doc <= 99999) {
                    return doc;
                }
                System.out.println("  [ERRO] O No. do Documento deve ser um inteiro positivo (1 a 99999).");
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] No. do Documento invalido! Digite apenas numeros.");
            }
        }
    }

    /**
     * Lê e valida um texto obrigatório (não vazio).
     */
    public static String lerTextoObrigatorio(String prompt, String nomeCampo) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada.toUpperCase();
            }
            System.out.println("  [ERRO] O campo " + nomeCampo + " nao pode ficar em branco.");
        }
    }

    /**
     * Lê e valida um valor monetário estritamente positivo (> 0).
     */
    public static double lerValorPositivo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim().replace(".", "").replace(",", ".");
            try {
                double valor = Double.parseDouble(entrada);
                if (valor > 0.0) {
                    return Math.round(valor * 100.0) / 100.0;
                }
                System.out.println("  [ERRO] O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Valor invalido! Exemplo de formato valido: 150,00");
            }
        }
    }

    /**
     * Lê e valida o valor pago na baixa, garantindo que seja > 0 e <= saldoAtual.
     */
    public static double lerValorPagamento(String prompt, double saldoAtual) {
        while (true) {
            double valorPago = lerValorPositivo(prompt);
            if (valorPago <= saldoAtual + 0.0001) {
                return Math.min(valorPago, saldoAtual);
            }
            System.out.println("  [ERRO] O valor pago (" + formatarMoeda(valorPago)
                    + ") nao pode ser maior que o saldo devedor atual (" + formatarMoeda(saldoAtual) + ").");
        }
    }

    /**
     * Lê e valida uma data no formato dd/MM/aa ou dd/MM/aaaa.
     */
    public static LocalDate lerData(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim();
            try {
                if (entrada.length() == 8) {
                    return LocalDate.parse(entrada, FORMATO_ENTRADA_6_DIGITOS);
                } else if (entrada.length() == 10) {
                    return LocalDate.parse(entrada, FORMATO_ENTRADA_8_DIGITOS);
                } else {
                    System.out.println("  [ERRO] Formato de data invalido! Utilize DD/MM/AA ou DD/MM/AAAA.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("  [ERRO] Data inexistente ou invalida! Verifique o dia, mes e ano.");
            }
        }
    }

    /**
     * Lê um ano válido (ex.: 1900 a 2100).
     */
    public static int lerAno(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim();
            try {
                int ano = Integer.parseInt(entrada);
                if (ano >= 1900 && ano <= 2100) {
                    return ano;
                }
                System.out.println("  [ERRO] Digite um ano valido (entre 1900 e 2100).");
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Ano invalido! Digite um numero de 4 digitos (ex: 2026).");
            }
        }
    }

    /**
     * Lê uma confirmação do tipo ( S/N ).
     *
     * @return true para 'S', false para 'N'.
     */
    public static boolean lerConfirmacao(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = SCANNER.nextLine().trim().toUpperCase();
            if (entrada.equals("S")) {
                return true;
            }
            if (entrada.equals("N")) {
                return false;
            }
            System.out.println("  [ERRO] Resposta invalida! Digite apenas S (Sim) ou N (Nao).");
        }
    }

    /**
     * Pausa a execução aguardando ENTER do usuário.
     */
    public static void aguardarEnter() {
        System.out.print("\nPressione ENTER para continuar...");
        SCANNER.nextLine();
    }

    public static String formatarData(LocalDate data) {
        return data.format(FORMATO_EXIBICAO_DATA);
    }

    public static String formatarMoeda(double valor) {
        return FORMATO_MOEDA.format(valor);
    }

    public static String ajustarTamanhoTexto(String texto, int tamanho) {
        if (texto == null) {
            texto = "";
        }
        if (texto.length() > tamanho) {
            return texto.substring(0, tamanho);
        }
        return String.format("%-" + tamanho + "s", texto);
    }
}
