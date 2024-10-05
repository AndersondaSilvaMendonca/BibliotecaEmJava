public class Usuario {
    private int id;
    private String tipo; // "admin" ou "comum"
    private String nome;
    private String senha;

    public Usuario(String tipo, String nome, String senha) {
        this.tipo = tipo;
        this.nome = nome;
        this.senha = senha;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "Usuário [id=" + id + ", tipo=" + tipo + ", nome=" + nome + "]";
    }
}
