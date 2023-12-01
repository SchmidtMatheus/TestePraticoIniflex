import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 – Inserir todos os funcionários
        funcionarios.add(new Funcionario("João", LocalDate.parse("01/01/1980", formatter), new BigDecimal("5000.00"), "Desenvolvedor"));
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("15/05/1990", formatter), new BigDecimal("6000.00"), "Analista"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.parse("22/09/1985", formatter), new BigDecimal("7000.00"), "Gerente"));
        funcionarios.add(new Funcionario("Ana", LocalDate.parse("10/12/1982", formatter), new BigDecimal("5500.00"), "Desenvolvedor"));

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 – Imprimir todos os funcionários
        System.out.println("Imprimir todos os funcionários:");
        funcionarios.forEach(f -> {
            System.out.printf("Nome: %s, Data de Nascimento: %s, Salário: %,.2f, Função: %s%n",
                    f.getNome(), f.getDataNascimento().format(formatter), f.getSalario(), f.getFuncao());
        });

        // 3.4 – Aumentar salário em 10%
        funcionarios.forEach(f -> f.salario = f.getSalario().multiply(new BigDecimal("1.10")));

        // 3.5 – Agrupar funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários agrupados por função
        System.out.println("\n3.6 – Imprimir os funcionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println("  " + f.getNome()));
        });

        // 3.8 – Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\n3.8 – Imprimir os funcionários que fazem aniversário nos meses 10 e 12:");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println("Nome: " + f.getNome() +
                        ", Data de Nascimento: " + f.getDataNascimento().format(formatter)));

        // 3.9 – Imprimir o funcionário com a maior idade
        System.out.println("\n3.9 – Imprimir o funcionário com a maior idade:");
        Funcionario funcionarioMaiorIdade = Collections.max(funcionarios, Comparator.comparing(f -> f.getDataNascimento()));
        int idade = LocalDate.now().getYear() - funcionarioMaiorIdade.getDataNascimento().getYear();
        System.out.println("Nome: " + funcionarioMaiorIdade.getNome() + ", Idade: " + idade);

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n3.10 – Imprimir a lista de funcionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println("Nome: " + f.getNome()));

        // 3.11 – Imprimir o total dos salários dos funcionários
        System.out.println("\n3.11 – Imprimir o total dos salários dos funcionários:");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("Total dos salários: %,.2f%n", totalSalarios);

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\n3.12 – Imprimir quantos salários mínimos ganha cada funcionário:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("Nome: " + f.getNome() + ", Salários Mínimos: " + salariosMinimos);
        });
    }
}
