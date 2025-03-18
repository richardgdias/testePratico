/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.DTO;

/**
 *
 * @author Richard
 */
public class ClientesDTO {

    private int codigo;
    private String nome;
    private double limiteCompra;
    private int diaFechamentoFatura;

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the limiteCompra
     */
    public double getLimiteCompra() {
        return limiteCompra;
    }

    /**
     * @param limiteCompra the limiteCompra to set
     */
    public void setLimiteCompra(double limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    /**
     * @return the diaFechamentoFatura
     */
    public int getDiaFechamentoFatura() {
        return diaFechamentoFatura;
    }

    /**
     * @param diaFechamentoFatura the diaFechamentoFatura to set
     */
    public void setDiaFechamentoFatura(int diaFechamentoFatura) {
        this.diaFechamentoFatura = diaFechamentoFatura;
    }
}
