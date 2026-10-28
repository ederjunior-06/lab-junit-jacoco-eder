# Relatório do Laboratório: JUnit 5

**Dupla:** Éder Junior 
**Link do fork:** _https://github.com/ederjunior-06/lab-junit-jacoco-eder.git_

## 1. Resultado final

Cole aqui o resumo que aparece no final de `java scripts/Rodar.java`:

```
Test run finished after 476 ms
[         6 containers found      ]
[         0 containers skipped    ]
[         6 containers started    ]
[         0 containers aborted    ]
[         6 containers successful ]
[         0 containers failed     ]
[        21 tests found           ]
[         0 tests skipped         ]
[        21 tests started         ]
[         0 tests aborted         ]
[        21 tests successful      ]
[         0 tests failed          ]
```

## 2. Nível 1: `if`s de `Conta`

Para cada `if` de `Conta.java`, diga qual teste faz o `if` ser **verdadeiro** e qual faz ser **falso**
(escreva o nome do método de teste).

| `if` (condição) | Teste que torna **verdadeiro** | Teste que torna **falso** |
|---|---|---|
| saldo inicial negativo (construtor) | `construtorComSaldoNegativoDeveZerarSaldo` | `contaNovaDeveGuardarTitularESaldoInicial` |
| valor do depósito `<= 0` | `depositoComValorNegativoDeveSerRecusado` | `depositoValidoDeveAumentarOSaldo` |
| valor do saque `<= 0` | `saqueZeradoDeveSerRecusado` | `saqueValidoDeveDiminuirOSaldo` |
| saque maior que o saldo | `saqueMaiorQueOSaldoDeveSerRecusado` | `saqueValidoDeveDiminuirOSaldo` |

## 3. Nível 2: regras de `ServicoTransferencia`

| Regra | Teste(s) que a exercita(m) |
|---|---|
| R1 mesma conta | `mesmaContaEValorInvalidoDevemSerRecusados` |
| R2 valor <= 0 | `mesmaContaEValorInvalidoDevemSerRecusados` |
| R3 acima do limite de 5.000 (e 5.000 exatos permitido) | `transferenciaAcimaDoLimiteDeveSerRecusada`   `transferenciaIgualAoLimiteDeveSerAceita` |
| R4 saldo insuficiente para valor + tarifa (nada é alterado); tarifa de 1% acima de 1.000 (1.000 exatos sem tarifa) | `saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta`   `transferenciaIgualAoLimiteDeveSerAceita` |
| R5 transferência OK: saldos da origem e do destino | `transferenciaIgualAoLimiteDeveSerAceita `  `transferenciaDeExatamente1000NaoPagaTarifa`  `transferenciaAcimaDe1000PagaTarifaDe1Porcento `  `transferenciaPequenaDeveSerAceitaSemTarifa` |

## 4. Nível 3: regras de `AnalisadorEmprestimo`

| Regra | Cenário escolhido (entradas → decisão esperada) | Método de teste |
|---|---|---|
| 1. entradas inválidas → DADOS_INVALIDOS (cada uma das 6 condições) | teste com score negativo, com score acima de 1000, renda zerada, valor negativo → DADOS_INVALIDOS | `entradasInvalidasDevemRetornarDadosInvalidos` |
| 2. score < 300 → NEGADO | score de 299 → retorna NEGADO | `scoreAbaixoDe300DeveSerNegado` |
| 3. taxa por faixa de score (800 / 600) | taxas de 12% (score 800), 24% (score 600) e 36%(score 599) | `taxaJurosAplicaCorretamentePorFaixaDeScore` |
| 4. parcela (Price) | 10 mil reais em 12x eom 1% ao mes → parcela de 888,48 reais | `calculoDaParcelaPriceDeveEstarCorreto` |
| 5. comprometimento > 50% → NEGADO; > 30% → ANÁLISE MANUAL | parcela pagando mais de 50% da renda → NEGADO, parcelam entre 30 e 50 por cento → ANALISE MANUAL | `comprometimentoAcimaDosLimitesDeveSerNegadoOuAnaliseManual` |
| 6. comprometimento <= 30%: score >= 600 aprovado; abaixo, análise manual | parcela menor ou igual a 30% da renda com score de 600 → APROVADO, score 599 → ANALISE MANUAL | `comprometimentoAte30PorCentoAprovaOuPedeAnaliseManual` |

## 5. Reflexão (2 a 4 linhas cada)

1. **Os dois lados do `if`:** qual `if` ficou sem teste de um dos lados no começo? Que bug passaria
   despercebido por isso? acredito que seria o saque se colacasse um valor menor que 0 pois passaria, pois nao existe teste para esse caso.
2. **Valor-limite:** qual teste de fronteira (ex.: 1.000,00 / 5.000,00 / score 600) vocês acham
   mais importante? Que bug ele pegaria se o código usasse `>=` no lugar de `>`? o de transferencia de 1.000 reais porque se mudasse >= no lugar de > ele nao ia passar no teste pois teria que pagar taxa com valores a 1000
3. **Nível 3:** como vocês decidiram quais cenários testar? (classes de equivalência, fronteiras,
   leitura das regras...) eu li as regras na ordem e quis aplicar um teste para cada, tentando testar as pontas como valores invalidos, limites de score e variando as porcentagens de renda
4. **Teste que passa mas não protege:** descrevam um teste que executa o código e passa, mas não
   verificaria um erro (dica: um teste sem `assert`). um teste onde executa uma transferência, porém no final não utiliza dos assertsEquals, assim passando no teste mas não podendo checar se a trasnferencia podia ser realizada de acordo com as regras.
5. **Divisão do trabalho:** quem escreveu o quê? (uma linha): Fiz tudo sozinho pois não pude ir à aula
