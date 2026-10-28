package br.ufrgs.inf.lab.nivel3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnalisadorEmprestimoTest {

    private static final double DELTA = 0.001;
    private AnalisadorEmprestimo analisador;

    @BeforeEach
    void setUp() {
        analisador = new AnalisadorEmprestimo();
    }

    @Test
    void entradasInvalidasDevemRetornarDadosInvalidos() {
        //score < 0
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, analisador.analisar(-1, 5000.0, 1000.0, 12));
        //score > 1000
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, analisador.analisar(1001, 5000.0, 1000.0, 12));
        //renda <= 0
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, analisador.analisar(700, 0.0, 1000.0, 12));
        //valor <= 0
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, analisador.analisar(700, 5000.0, -100.0, 12));
        //parcelas < 1
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, analisador.analisar(700, 5000.0, 1000.0, 0));
        //parcelas > 360
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, analisador.analisar(700, 5000.0, 1000.0, 361));
    }

    @Test
    void scoreAbaixoDe300DeveSerNegado() {
        assertEquals(DecisaoEmprestimo.NEGADO, analisador.analisar(299, 5000.0, 1000.0, 12));
    }

    @Test
    void taxaJurosAplicaCorretamentePorFaixaDeScore() {
        assertEquals(0.12, analisador.taxaAnual(800), DELTA);
        assertEquals(0.24, analisador.taxaAnual(600), DELTA);
        assertEquals(0.36, analisador.taxaAnual(599), DELTA);
    }

    @Test
    void calculoDaParcelaPriceDeveEstarCorreto() {
        
        double parcela = analisador.calcularParcela(10000.0, 0.01, 12);
        assertEquals(888.487, parcela, DELTA);
    }

    @Test
    void comprometimentoAcimaDosLimitesDeveSerNegadoOuAnaliseManual() {
        
        assertEquals(DecisaoEmprestimo.NEGADO, analisador.analisar(700, 2000.0, 20000.0, 6));//comprometimento > 50%
        assertEquals(DecisaoEmprestimo.ANALISE_MANUAL, analisador.analisar(700, 3000.0, 10000.0, 10));//compromentemento entre 30 e 50 por cento
    }

    @Test
    void comprometimentoAte30PorCentoAprovaOuPedeAnaliseManual() {
       
        assertEquals(DecisaoEmprestimo.APROVADO, analisador.analisar(600, 5000.0, 3000.0, 12));
        assertEquals(DecisaoEmprestimo.ANALISE_MANUAL, analisador.analisar(599, 5000.0, 3000.0, 12));
    }
}