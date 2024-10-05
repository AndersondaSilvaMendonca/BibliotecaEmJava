import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioLogado = null;

        try (Connection connection = ConexaoBD.getConexao()) {
            LivroDAO livroDAO = new LivroDAO(connection);
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);

            // Menu do aplicativo
            while (true) {
                System.out.println("\n=== Biblioteca ===");
                if (usuarioLogado == null) {
                    System.out.println("1. Cadastrar Usuário");
                    System.out.println("2. Login");
                    System.out.println("3. Sair");
                    System.out.print("Escolha uma opção: ");

                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    switch (opcao) {
                        case 1:
                            int tipoUsuario;
                            do {
                                System.out.print("Digite o tipo de usuário (1 para admin / 2 para comum): ");
                                tipoUsuario = scanner.nextInt();
                                scanner.nextLine(); // Consumir a quebra de linha
                                if (tipoUsuario != 1 && tipoUsuario != 2) {
                                    System.out.println("Opção inválida. Escolha 1 para admin ou 2 para comum.");
                                }
                            } while (tipoUsuario != 1 && tipoUsuario != 2);
                            
                            String tipo = (tipoUsuario == 1) ? "admin" : "comum";
                            System.out.print("Nome do usuário: ");
                            String nome = scanner.nextLine();
                            System.out.print("Senha: ");
                            String senha = scanner.nextLine();
                            usuarioDAO.cadastrarUsuario(tipo, nome, senha);
                            break;

                        case 2:
                            System.out.print("Nome do usuário: ");
                            String nomeLogin = scanner.nextLine();
                            System.out.print("Senha: ");
                            String senhaLogin = scanner.nextLine();
                            usuarioLogado = usuarioDAO.login(nomeLogin, senhaLogin);
                            if (usuarioLogado == null) {
                                System.out.println("Login falhou! Verifique suas credenciais.");
                            } else {
                                System.out.println("Login bem-sucedido! Você está logado como " + usuarioLogado.getNome());
                            }
                            break;

                        case 3:
                            System.out.println("Saindo...");
                            return;

                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    // Menu após login
                    System.out.println("1. Cadastrar Livro");
                    System.out.println("2. Listar Livros");
                    System.out.println("3. Pesquisar Livro");
                    System.out.println("4. Excluir Livro");
                    System.out.println("5. Excluir Usuário");
                    System.out.println("6. Listar Usuários");
                    System.out.println("7. Emprestar Livro");
                    System.out.println("8. Devolver Livro");
                    System.out.println("9. Sair");
                    System.out.print("Escolha uma opção: ");

                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    switch (opcao) {
                        case 1:
                            if (usuarioLogado.getTipo().equals("admin")) {
                                System.out.print("Nome do autor: ");
                                String nomeAutor = scanner.nextLine();
                                System.out.print("Nome do livro: ");
                                String nomeLivro = scanner.nextLine();
                                System.out.print("Ano de publicação: ");
                                int anoPublicacao = scanner.nextInt();
                                System.out.print("Quantidade de livros: ");
                                int quantidade = scanner.nextInt();
                                livroDAO.cadastrarLivro(nomeAutor, nomeLivro, anoPublicacao, quantidade);
                            } else {
                                System.out.println("Você não tem permissão para cadastrar livros.");
                            }
                            break;

                        case 2:
                            List<Livro> livros = livroDAO.listarLivros();
                            if (livros.isEmpty()) {
                                System.out.println("Não há livros cadastrados.");
                            } else {
                                for (Livro livro : livros) {
                                    System.out.println(livro);
                                }
                            }
                            break;

                        case 3:
                            System.out.print("Digite o nome do autor ou do livro: ");
                            String pesquisa = scanner.nextLine();
                            List<Livro> livrosEncontrados = livroDAO.pesquisarLivros(pesquisa);
                            if (livrosEncontrados.isEmpty()) {
                                System.out.println("Nenhum livro encontrado.");
                            } else {
                                for (Livro livro : livrosEncontrados) {
                                    System.out.println(livro);
                                }
                            }
                            break;

                        case 4:
                            if (usuarioLogado.getTipo().equals("admin")) {
                                System.out.print("Digite o código do livro para excluir: ");
                                int codigoParaExcluir = scanner.nextInt();
                                livroDAO.excluirLivro(codigoParaExcluir);
                            } else {
                                System.out.println("Você não tem permissão para excluir livros.");
                            }
                            break;

                        case 5:
                            if (usuarioLogado.getTipo().equals("admin")) {
                                System.out.print("Digite o ID do usuário para excluir: ");
                                String idInput = scanner.nextLine();
                                try {
                                    int idParaExcluir = Integer.parseInt(idInput);
                                    usuarioDAO.excluirUsuario(idParaExcluir);
                                } catch (NumberFormatException e) {
                                    System.out.println("Por favor, insira apenas números.");
                                }
                            } else {
                                System.out.println("Você não tem permissão para excluir usuários.");
                            }
                            break;

                        case 6:
                            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
                            if (usuarios.isEmpty()) {
                                System.out.println("Não há usuários cadastrados.");
                            } else {
                                for (Usuario usuario : usuarios) {
                                    System.out.println(usuario);
                                }
                            }
                            break;

                        case 7:
                            System.out.print("Digite o código do livro para emprestar: ");
                            int codigoEmprestimo = scanner.nextInt();
                            livroDAO.emprestarLivro(codigoEmprestimo);
                            break;

                        case 8: // Opção para devolver livro
                            System.out.print("Digite o código do livro a ser devolvido: ");
                            int codigoDevolucao = scanner.nextInt();
                            System.out.print("Digite a quantidade a ser devolvida: ");
                            int quantidadeDevolucao = scanner.nextInt();
                            
                            // Chame o método de devolução
                            livroDAO.devolverLivro(codigoDevolucao, quantidadeDevolucao);
                            break;




                        case 9:
                            System.out.println("Saindo...");
                            return;

                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}


