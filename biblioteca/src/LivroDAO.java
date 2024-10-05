import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private Connection connection;

    public LivroDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarLivro(String nomeAutor, String nomeLivro, int anoPublicacao, int quantidade) {
        String sql = "INSERT INTO livros (nome_autor, nome_livro, ano_publicacao, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeAutor);
            stmt.setString(2, nomeLivro);
            stmt.setInt(3, anoPublicacao);
            stmt.setInt(4, quantidade);
            stmt.executeUpdate();
            System.out.println("Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livro livro = new Livro(rs.getInt("codigo"), rs.getString("nome_autor"),
                        rs.getString("nome_livro"), rs.getInt("ano_publicacao"), rs.getInt("quantidade"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
        return livros;
    }
    public void excluirLivro(int codigo) {
        // Excluir o livro independentemente de sua disponibilidade
        String sqlDelete = "DELETE FROM livros WHERE codigo = ?";
        try (PreparedStatement stmtDelete = connection.prepareStatement(sqlDelete)) {
            stmtDelete.setInt(1, codigo);
            int rowsAffected = stmtDelete.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Livro excluído com sucesso!");
            } else {
                System.out.println("Livro não encontrado com esse código.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
        }
    }


    public List<Livro> pesquisarLivros(String pesquisa) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE nome_autor LIKE ? OR nome_livro LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + pesquisa + "%");
            stmt.setString(2, "%" + pesquisa + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro(rs.getInt("codigo"), rs.getString("nome_autor"),
                        rs.getString("nome_livro"), rs.getInt("ano_publicacao"), rs.getInt("quantidade"));
                livrosEncontrados.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar livros: " + e.getMessage());
        }
        return livrosEncontrados;
    }

  


    public void emprestarLivro(int codigo) {
        String sql = "UPDATE livros SET quantidade = quantidade - 1 WHERE codigo = ? AND quantidade > 0";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro emprestado com sucesso!");
            } else {
                System.out.println("Não há livros disponíveis para emprestar.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao emprestar livro: " + e.getMessage());
        }
    }

    public void devolverLivro(int codigo, int quantidade) {
        // Verifica se o livro já existe
        String sqlCheck = "SELECT * FROM livros WHERE codigo = ?";
        try (PreparedStatement stmtCheck = connection.prepareStatement(sqlCheck)) {
            stmtCheck.setInt(1, codigo);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                // Se o livro existe, apenas aumenta a quantidade
                String sqlUpdate = "UPDATE livros SET quantidade = quantidade + ? WHERE codigo = ?";
                try (PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate)) {
                    stmtUpdate.setInt(1, quantidade);
                    stmtUpdate.setInt(2, codigo);
                    stmtUpdate.executeUpdate();
                    System.out.println("Livro devolvido com sucesso!");
                }
            } else {
                System.out.println("Livro não encontrado para devolução.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao devolver livro: " + e.getMessage());
        }
    }



}
