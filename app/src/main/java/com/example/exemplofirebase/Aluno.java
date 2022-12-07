package com.example.exemplofirebase;

public class Aluno {
    private String chave;
    private String nome;
    private double nota1;
    private double nota2;
    public Aluno(String c, String n, double n1, double n2) {
        nome = n; nota1 = n1; nota2 = n2; chave = c;
    }
    public Aluno() {}
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getNota1() {
        return nota1;
    }
    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }
    public double getNota2() {
        return nota2;
    }
    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }
    public Double calculaMedia() {
        Double media = (nota1 + nota2) / 2;
        return media;
    }
}