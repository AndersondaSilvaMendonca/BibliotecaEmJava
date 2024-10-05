public class Livro {
    private int codigo;
    private String nomeAutor;
    private String nomeLivro;
    private int anoPublicacao;
    private int quantidade;

    public Livro(int codigo, String nomeAutor, String nomeLivro, int anoPublicacao, int quantidade) {
        this.codigo = codigo;
        this.nomeAutor = nomeAutor;
        this.nomeLivro = nomeLivro;
        this.anoPublicacao = anoPublicacao;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Autor: " + nomeAutor + ", Livro: " + nomeLivro + ", Ano: " + anoPublicacao + ", Quantidade: " + quantidade;
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
