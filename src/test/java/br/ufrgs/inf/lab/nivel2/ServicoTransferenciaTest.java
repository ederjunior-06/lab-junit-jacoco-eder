package br.ufrgs.inf.lab.nivel2;

import static org.junit.jupiter.api.Assertions.*;

import br.ufrgs.inf.lab.nivel1.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * NÍVEL 2 (semi-guiado): ServicoTransferencia.
 *
 * Aqui NÃO há os asserts: cada teste abaixo diz o CENÁRIO e o que deve
 * ser verificado. Você decide como escrever (assertEquals, assertTrue...).
 * Leia as regras R1..R5 em ServicoTransferencia.java.
 *
 * Remova o @Disabled e o fail(...) de cada teste à medida que o implementar.
 * Sinta-se livre para ACRESCENTAR testes (e usar @ParameterizedTest).
 */
class ServicoTransferenciaTest {

    private static final double DELTA = 0.001;

    private ServicoTransferencia servico;
    private Conta origem;
    private Conta destino;

    // Roda ANTES de cada teste: garante contas "limpas" (testes independentes)
    @BeforeEach
    void preparar() {
        servico = new ServicoTransferencia();
        origem = new Conta("Origem", 10_000.0);
        destino = new Conta("Destino", 0.0);
    }

    // ---------------------------------------------------------------
    // PRONTO (exemplo)
    // ---------------------------------------------------------------

    @Test
    void transferenciaPequenaDeveSerAceitaSemTarifa() {
        StatusTransferencia status = servico.transferir(origem, destino, 200.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(9_800.0, origem.getSaldo(), DELTA);
        assertEquals(200.0, destino.getSaldo(), DELTA);
    }

    // ---------------------------------------------------------------
    // PARA VOCÊ ESCREVER
    // ---------------------------------------------------------------

    @Test
    void transferenciaAcimaDe1000PagaTarifaDe1Porcento() {
        // DADO   origem com 10.000 e destino com 0
        // QUANDO transfere 2.000
        StatusTransferencia status = servico.transferir(origem, destino, 2000.0);
        assertEquals(StatusTransferencia.OK, status);
        assertEquals(7_980.0, origem.getSaldo(), DELTA);
        assertEquals(2000.0, destino.getSaldo(), DELTA);
        // ENTÃO  status = ?  | calcularTarifa(2000) = ?  | saldo da origem = ?  | saldo do destino = ?
        //        (calcule os valores esperados na mão ANTES de rodar!)
        
    }

    @Test
    void transferenciaDeExatamente1000NaoPagaTarifa() {
        // Valor-limite da regra R4: "ACIMA de 1.000" ... e 1.000 exatos?
        StatusTransferencia status = servico.transferir(origem, destino, 1000.0);
        double tarifa = servico.calcularTarifa(1000.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(0.0, tarifa, DELTA);
        assertEquals(9_000.0, origem.getSaldo(), DELTA);
        assertEquals(1000.0, destino.getSaldo(), DELTA);
        // Verifique o status, a tarifa e os saldos.
      
    }

    @Test
    void transferenciaAcimaDoLimiteDeveSerRecusada() {
        // Qual status? Qual o valor mínimo que ultrapassa o limite (R3)?
        StatusTransferencia status = servico.transferir(origem, destino, 5001.0);

        assertEquals(StatusTransferencia.ACIMA_DO_LIMITE, status);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);
        // E um teste com EXATAMENTE o limite: o que deveria acontecer?
        // Verifique também que nenhuma conta foi alterada.
    }

    @Test
    void transferenciaIgualAoLimiteDeveSerAceita() {
        // Qual status? Qual o valor mínimo que ultrapassa o limite (R3)?
        StatusTransferencia status = servico.transferir(origem, destino, 5000.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(4_950.0, origem.getSaldo(), DELTA);
        assertEquals(5_000.0, destino.getSaldo(), DELTA);
        // E um teste com EXATAMENTE o limite: o que deveria acontecer?
        // Verifique também que nenhuma conta foi alterada.
    }

    @Test
    void saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta() {
        // DICA: crie uma conta de origem com saldo suficiente para o VALOR,
        //       mas NÃO suficiente para VALOR + TARIFA (ex.: saldo 2.000, valor 2.000).
        // Verifique o status e que origem E destino mantêm os saldos originais.

        origem = new Conta("Origem", 3_000.0);

        StatusTransferencia status = servico.transferir(origem, destino, 3000.0);

        assertEquals(StatusTransferencia.SALDO_INSUFICIENTE, status);
        assertEquals(3_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);

    }

    @Test
    void mesmaContaEValorInvalidoDevemSerRecusados() {

        StatusTransferencia statusR1 = servico.transferir(origem, origem, 200.0);
        assertEquals(StatusTransferencia.MESMA_CONTA, statusR1);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);

        StatusTransferencia statusR2Zero = servico.transferir(origem, destino,0);
        assertEquals(StatusTransferencia.VALOR_INVALIDO, statusR2Zero);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);

        StatusTransferencia statusR2Menos = servico.transferir(origem, destino,-200);
        assertEquals(StatusTransferencia.VALOR_INVALIDO, statusR2Menos);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);

        // Cubra R1 (mesma conta) e R2 (valor zero e valor negativo).
        // Quantos testes/asserts você precisa para exercitar os DOIS lados de cada "if"?
        // Em todos os casos, confira o status E que os saldos não mudaram.

    }
}
