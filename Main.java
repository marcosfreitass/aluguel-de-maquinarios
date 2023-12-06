// Raphael Braga Ribeiro: 202202830364
// Levi Ágape Silva Santos: 202202443671
// Marcos Antonio Freitas Souza: 202208699464
// Gustavo Gama da Silva Pinho Oliveira: 201951157771
// Felipe Magalhaes Sales: 202208863621
// Caike dos Santos Rocha: 202051349956

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Maquinario {
    private String nome;
    private double precoDiaria;
    private int estoque;

    public Maquinario(String nome, double precoDiaria, int estoque) {
        this.nome = nome;
        this.precoDiaria = precoDiaria;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public int getEstoque() {
        return estoque;
    }

    public void diminuirEstoque(int quantidade) {
        estoque -= quantidade;
    }

    @Override
    public String toString() {
        return "Maquinario{" +
                "nome='" + nome + '\'' +
                ", precoDiaria=" + precoDiaria +
                ", estoque=" + estoque +
                '}';
    }
}

class Cliente {
    private String nome;

    public Cliente(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

class Aluguel {
    private Cliente cliente;
    private Maquinario maquinario;
    private int dias;

    public Aluguel(Cliente cliente, Maquinario maquinario, int dias) {
        this.cliente = cliente;
        this.maquinario = maquinario;
        this.dias = dias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Maquinario getMaquinario() {
        return maquinario;
    }

    public int getDias() {
        return dias;
    }
}

class EmpresaAluguel {
    private List<Maquinario> maquinarios;
    private List<Aluguel> alugueis;

    public EmpresaAluguel() {
        this.maquinarios = new ArrayList<>();
        this.alugueis = new ArrayList<>();
    }

    public List<Maquinario> getMaquinarios() {
        return maquinarios;
    }

    public void adicionarMaquinario(Maquinario maquinario) {
        maquinarios.add(maquinario);
    }

    public boolean realizarAluguel(Cliente cliente, Maquinario maquinario, int dias, int quantidade) {
        if (maquinario.getEstoque() >= quantidade) {
            maquinario.diminuirEstoque(quantidade);
            Aluguel aluguel = new Aluguel(cliente, maquinario, dias);
            alugueis.add(aluguel);
            return true;
        } else {
            System.out.println("Maquinário indisponível para aluguel na quantidade desejada.");
            return false;
        }
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        EmpresaAluguel empresa = new EmpresaAluguel();
        List<Aluguel> alugueis = empresa.getAlugueis();

        empresa.adicionarMaquinario(new Maquinario("Escavadeira", 150.00, 5));
        empresa.adicionarMaquinario(new Maquinario("Trator", 120.00, 3));
        empresa.adicionarMaquinario(new Maquinario("Plantadeira", 100.00, 2));
        empresa.adicionarMaquinario(new Maquinario("Motoniveladora", 80.00, 4));
        empresa.adicionarMaquinario(new Maquinario("Empilhadeira", 50.00, 9));

        Cliente cliente = null;

        do {
            System.out.print("Digite o nome do cliente: ");
            String nomeCliente = scanner.nextLine();
            cliente = new Cliente(nomeCliente);

            int opcao;
            do {
                opcao = 1;

                System.out.println("Escolha o maquinário:");
                for (Maquinario maquinario : empresa.getMaquinarios()) {
                    System.out.println(opcao + ". " + maquinario.getNome() +
                            " | Diária: R$" + maquinario.getPrecoDiaria() +
                            " | Estoque: " + maquinario.getEstoque());
                    opcao++;
                }

                try {
                    System.out.print("Digite o número do maquinário desejado: ");
                    int escolhaMaquinario = scanner.nextInt();
                    scanner.nextLine();

                    if (escolhaMaquinario < 1 || escolhaMaquinario > empresa.getMaquinarios().size()) {
                        throw new IllegalArgumentException("Opção de maquinário inválida.");
                    }

                    Maquinario maquinarioEscolhido = empresa.getMaquinarios().get(escolhaMaquinario - 1);

                    System.out.print("Digite a quantidade desejada para aluguel: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine();

                    if (quantidade <= 0 || quantidade > maquinarioEscolhido.getEstoque()) {
                        throw new IllegalArgumentException("Quantidade inválida.");
                    }

                    System.out.print("Digite a quantidade de dias para aluguel: ");
                    int dias = scanner.nextInt();
                    scanner.nextLine();

                    if (dias <= 0) {
                        throw new IllegalArgumentException("A quantidade de dias deve ser um número positivo.");
                    }

                    if (empresa.realizarAluguel(cliente, maquinarioEscolhido, dias, quantidade)) {
                        // double valorAluguel = maquinarioEscolhido.getPrecoDiaria() * dias *
                        // quantidade;
                        System.out.println("Aluguel realizado com sucesso!");
                    }

                } catch (InputMismatchException | IllegalArgumentException e) {
                    System.out.println("Escolha inválida. Certifique-se de escolher um número válido.");
                    scanner.nextLine();
                }

                System.out.print("Deseja alugar outro maquinário? (Sim | Não): ");
                String escolha = scanner.nextLine().toLowerCase();
                continuar = escolha.equals("sim");

            } while (continuar);

            System.out.print("Deseja alugar para outro cliente? (Sim | Não): ");
            String escolhaCliente = scanner.nextLine().toLowerCase();
            continuar = escolhaCliente.equals("sim");

        } while (continuar);

        System.out.println("Lista de Alugueis:");
        double valorTotalAlugueis = 0.0;
        for (Aluguel aluguel : alugueis) {
            double valorAluguel = aluguel.getMaquinario().getPrecoDiaria() * aluguel.getDias();
            valorTotalAlugueis += valorAluguel;
            System.out.printf("Cliente: %s, Maquinário: %s, Dias: %d, Valor do Aluguel: R$%.2f%n",
                    aluguel.getCliente().getNome(),
                    aluguel.getMaquinario().getNome(),
                    aluguel.getDias(),
                    valorAluguel);
        }

        System.out.printf("Valor Total dos Aluguéis: R$%.2f%n", valorTotalAlugueis);

        scanner.close();
    }
}
