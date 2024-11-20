package model;

public class MaxCategoria {
	private int id;
    private String nome;
    private int limiteItens;

    public MaxCategoria(int id, String nome, int limiteItens) {
        this.id = id;
        this.nome = nome;
        this.limiteItens = limiteItens;
    }
    
    public MaxCategoria(String nome, int limiteItens) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        if (limiteItens <= 0) {
            throw new IllegalArgumentException("O limite de itens deve ser maior que zero.");
        }

        this.nome = nome;
        this.limiteItens = limiteItens;
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

    public int getLimiteItens() {
        return limiteItens;
    }

    public void setLimiteItens(int limiteItens) {
        this.limiteItens = limiteItens;
    }
}
