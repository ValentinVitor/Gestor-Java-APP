package model;

public class Produto {
	private int id;
    private String nome;
    private int quantidade;
    private double preco;
    private String dataValidade;
    private int categoriaId;  // Agora você tem o ID da categoria

    public Produto(int id, String nome, int quantidade, double preco, String dataValidade, int categoriaId) {
    	
    	this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataValidade = dataValidade;
        this.categoriaId = categoriaId;  // Recebe o ID da categoria
    }
    
    public Produto(String nome, int quantidade, double preco, String dataValidade, int categoriaId, MaxCategoria categoria) {
    	if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
    	if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade precisa ser maior que zero.");
        }
    	if (preco <= 0) {
            throw new IllegalArgumentException("O preço precisa ser maior que zero.");
        }
    	if (quantidade > categoria.getLimiteItens()) {
            throw new IllegalArgumentException("A quantidade do produto excede o limite permitido pela categoria.");
        }
    	
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataValidade = dataValidade;
        this.categoriaId = categoriaId;  // Recebe o ID da categoria
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}
