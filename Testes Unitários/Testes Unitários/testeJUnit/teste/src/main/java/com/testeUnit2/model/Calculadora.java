package com.testeUnit2.model;

public class Calculadora {
    public int somar(int a, int b){
        return a + b;
    }

    public int subtrair(int a, int b){
        return a - b;
    }

    public double multiplicacao(int a, int b){
        return a * b;
    }

    public double divisao(double a, double b){
        if(b == 0){
            throw new RuntimeException();
        }
        return a / b;
    }
}
