package com.testeUnit2.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculadoraTest {
    Calculadora calculadora;

    @BeforeEach
    void setup(){
        calculadora = new Calculadora();
    }

    // Soma de dois números pares
    @Test
    @DisplayName("Retornar soma positiva quando somar dois números positivos")
    void deveRotornarSomaPositivaQuandoSomarDoisNumerosPositivos() {
        //Arrange (Organizar)
        Calculadora calculadora = new Calculadora();
        int n1 = 5;
        int n2 = 3;
        int resultadoEsperado = 8;

        //Act (Ação)
        int resultadoReal = calculadora.somar(n1, n2);

        //Assert (Verificar se o resultado real é o esperado)
        assertEquals(resultadoEsperado, resultadoReal, "A soma de 5 e 3 deveria ser 8");
    }

    // Subtração de um número maior menos um menor
    @Test
    void deveRetornarDiferencaCorretaQuandoSubtrair(){
        //Arrange(Organizar)
        Calculadora calculadora = new Calculadora();
        int n1 = 10;
        int n2 = 4;
        int resultadoEsperado = 6;

        //Act (Ação)
        int resultadoReal = calculadora.subtrair(n1, n2);

        //Assert (Verificar se o resultado é o esperado)
        assertEquals(resultadoEsperado, resultadoReal, "A subtração de 10 e 4 deveria ser 6");
    }

    //Soma negativa
    @Test
    void outroTesteDeSomaNegativa(){
        //Arrange(Organizando)
        Calculadora calculadora = new Calculadora();
        int n1 = 3;
        int n2 = -7;
        int resultadoEsperado = -4;

        //Act (Ação)
        int resultadoReal = calculadora.somar(n1, n2);

        //Assert(Verificar se o resultado é o esperado)
        assertEquals(resultadoEsperado, resultadoReal, "O resultado deveria ser -4");
    }

    //Multiplicação
    @Test
    void DeveRetornarProdutoCorretoDeDoisNumerosPositivos(){
        //Arrange(Organizar)
        Calculadora calculadora = new Calculadora();
        int n1 = 3;
        int n2 = 6;
        int valorEsperado = 18;

        //Act (Ação)
        double valorReal = calculadora.multiplicacao(n1, n2);

        //Assert(Verificando se o resultado é o esperado)
        assertEquals(valorEsperado, valorReal, "O produto da multiplicação entre 3 e 6 deveria ser 18");
    }

    //Divisão
    @Test
    void DeveRetornarDivisaoEntreDoisNumerosInteirosPositivos() {
        //Arrange(Organizar)
        Calculadora calculadora = new Calculadora();
        int n1 = 35;
        int n2 = 5;
        int valorEsperado = 7;

        //Act (ação)
        double valorReal = calculadora.divisao(n1, n2);

        //Assert (Verifica se o resultado é esperado)
        assertEquals(valorEsperado, valorReal, "A divisão de 35 por 5 deveria ser 7");
    }

    @Test
    void testExpectedExceptinoIsThrown(){
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> {
                    calculadora.divisao(20, 0);
                }); assertEquals("Não é possível dividir o números por zero", exception. getMessage());
    }

    /*Quando você cria um teste antes da funcionalidade
    * Red
    * Refator
    * Green
    * */

}
