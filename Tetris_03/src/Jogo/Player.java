package Jogo;

public class Player {
    private String nome;    
    private int pontuacao;
    
    Player(String nome, int pontuacao){
        this.nome = nome;
        this.pontuacao = pontuacao;
    }
    
    public void set_jogador(String nome, int pontuacao){
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public int get_pontos(){
        return this.pontuacao;
    }
    
    public String get_nome(){
        return this.nome;
    }
    
}
