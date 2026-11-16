package br.unisul.contasapagar.repository;

import br.unisul.contasapagar.model.Conta;

/**
 * Gerencia o armazenamento das contas em vetor e oferece sub-rotinas
 * de busca, inclusão, exclusão e ordenação por data de vencimento.
 */
public class VetorContas {

    private static final int CAPACIDADE_MAXIMA = 1000;
    private final Conta[] contas;
    private int quantidade;

    public VetorContas() {
        this.contas = new Conta[CAPACIDADE_MAXIMA];
        this.quantidade = 0;
    }

    public boolean estaCheio() {
        return quantidade >= CAPACIDADE_MAXIMA;
    }

    public boolean estaVazio() {
        return quantidade == 0;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Busca o índice de uma conta pelo número do documento.
     *
     * @param documento Número do documento.
     * @return Índice no vetor ou -1 caso não exista.
     */
    public int buscarIndicePorDocumento(int documento) {
        for (int i = 0; i < quantidade; i++) {
            if (contas[i].getDocumento() == documento) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Busca uma conta pelo número do documento.
     *
     * @param documento Número do documento.
     * @return Objeto Conta ou null se não encontrado.
     */
    public Conta buscarPorDocumento(int documento) {
        int indice = buscarIndicePorDocumento(documento);
        if (indice != -1) {
            return contas[indice];
        }
        return null;
    }

    /**
     * Inclui uma nova conta no vetor.
     *
     * @param novaConta Conta a ser adicionada.
     * @return true se incluída com sucesso, false se o vetor estiver cheio ou documento duplicado.
     */
    public boolean incluir(Conta novaConta) {
        if (estaCheio() || buscarIndicePorDocumento(novaConta.getDocumento()) != -1) {
            return false;
        }
        contas[quantidade] = novaConta;
        quantidade++;
        return true;
    }

    /**
     * Exclui uma conta do vetor pelo número do documento, deslocando os elementos seguintes.
     *
     * @param documento Número do documento a ser excluído.
     * @return true se excluída com sucesso, false se não encontrada.
     */
    public boolean excluir(int documento) {
        int indice = buscarIndicePorDocumento(documento);
        if (indice == -1) {
            return false;
        }
        for (int i = indice; i < quantidade - 1; i++) {
            contas[i] = contas[i + 1];
        }
        contas[quantidade - 1] = null;
        quantidade--;
        return true;
    }

    /**
     * Retorna uma cópia do vetor apenas com as contas cadastradas,
     * ordenadas por data de vencimento (e por número de documento em caso de empate).
     *
     * @return Vetor de contas ordenado por data de vencimento.
     */
    public Conta[] obterContasOrdenadasPorVencimento() {
        Conta[] copia = new Conta[quantidade];
        for (int i = 0; i < quantidade; i++) {
            copia[i] = contas[i];
        }

        // Ordenação Bubble Sort para manter compatibilidade com Algoritmos e Programação
        for (int i = 0; i < quantidade - 1; i++) {
            for (int j = 0; j < quantidade - 1 - i; j++) {
                int comparacaoData = copia[j].getDataVencimento().compareTo(copia[j + 1].getDataVencimento());
                if (comparacaoData > 0 || (comparacaoData == 0 && copia[j].getDocumento() > copia[j + 1].getDocumento())) {
                    Conta temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }
}
