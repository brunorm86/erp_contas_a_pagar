package br.unisul.contasapagar.model;

import java.time.LocalDate;

/**
 * Classe que representa uma Conta a Pagar da empresa XYZ Comércio de Produtos Ltda.
 * Armazena as informações de documento, descrição, beneficiário, valor do débito e vencimento.
 */
public class Conta {

    private int documento;
    private String descricao;
    private String beneficiario;
    private double valorOriginal;
    private double saldoDevedor;
    private LocalDate dataVencimento;

    /**
     * Construtor da classe Conta.
     *
     * @param documento      Número do documento da conta.
     * @param descricao      Descrição da despesa.
     * @param beneficiario   Nome do beneficiário/fornecedor.
     * @param valorDebito    Valor inicial do débito.
     * @param dataVencimento Data de vencimento da conta.
     */
    public Conta(int documento, String descricao, String beneficiario, double valorDebito, LocalDate dataVencimento) {
        this.documento = documento;
        this.descricao = descricao;
        this.beneficiario = beneficiario;
        this.valorOriginal = valorDebito;
        this.saldoDevedor = valorDebito;
        this.dataVencimento = dataVencimento;
    }

    public int getDocumento() {
        return documento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public double getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public void setSaldoDevedor(double saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
