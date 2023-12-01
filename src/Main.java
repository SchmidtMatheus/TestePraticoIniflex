import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.getDefault()));
        List<Funcionario> funcionarios = new ArrayList<>();

        //Inserir todos os funcionários
        funcionarios.add(new Funcionario("João", LocalDate.parse("01/01/1980", formatter), new BigDecimal("5000.00"), "Desenvolvedor"));
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("15/05/1990", formatter), new BigDecimal("6000.00"), "Analista"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.parse("22/09/1985", formatter), new BigDecimal("7000.00"), "Gerente"));
        funcionarios.add(new Funcionario("Ana", LocalDate.parse("10/12/1982", formatter), new BigDecimal("5500.00"), "Desenvolvedor"));

        //Remover o funcionário João da lista.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        //Imprimir todos os funcionários
        System.out.println("3.3 – Imprimir todos os funcionários:");
        funcionarios.forEach(f -> {
            System.out.printf("Nome: %s, Data de Nascimento: %s, Salário: %s, Função: %s%n",
                    f.getNome(), f.getDataNascimento().format(formatter), decimalFormat.format(f.getSalario()), f.getFuncao());
        });

        //Aumentar salário em 10%
        funcionarios.forEach(f -> f.salario = f.getSalario().multiply(new BigDecimal("1.10")));


        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir os funcionários agrupados por função
        System.out.println("\n3.6 – Imprimir os funcionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println("  " + f.getNome()));
        });

        //Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\n3.8 – Imprimir os funcionários que fazem aniversário nos meses 10 e 12:");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println("Nome: " + f.getNome() +
                        ", Data de Nascimento: " + f.getDataNascimento().format(formatter)));

        // Imprimir o funcionário com a maior idade
        System.out.println("\n3.9 – Imprimir o funcionário com a maior idade:");
        Funcionario funcionarioMaiorIdade = Collections.max(funcionarios, Comparator.comparing(f -> f.getDataNascimento()));
        int idade = LocalDate.now().getYear() - funcionarioMaiorIdade.getDataNascimento().getYear();
        System.out.println("Nome: " + funcionarioMaiorIdade.getNome() + ", Idade: " + idade);

        // Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n3.10 – Imprimir a lista de funcionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println("Nome: " + f.getNome()));

        // Imprimir o total dos salários dos funcionários
        System.out.println("\n3.11 – Imprimir o total dos salários dos funcionários:");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("Total dos salários: %,.2f%n", totalSalarios);

        //Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\n3.12 – Imprimir quantos salários mínimos ganha cada funcionário:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("Nome: " + f.getNome() + ", Salários Mínimos: " + salariosMinimos);
        });
    }
}
