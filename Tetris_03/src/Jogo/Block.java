package Jogo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class Block {   
    private final int MAX_VARIACOES; 
    private final int MAX_TAMANHO;
    private final int VALOR;
    private final Color COR_PRINCIPAL;    
    private final Color COR_ESCURA;    
    private final Color COR_CLARA;
   
    private int[][] bloco_formato;
    private int[][][] variacoes;
    private int variacao;
    
    public static Block get_aleatorio(){
        Random r = new Random();
        int id = r.nextInt(7)+1;
        return Block.get_instancia(id);
    }
    
    public static Block get_instancia(int id){
        
        switch(id){
            case 1: return new Block_I();
                
            case 2: return new Block_T();
                
            case 3: return new Block_L();
                
            case 4: return new Block_Li();
                
            case 5: return new Block_S();
                
            case 6: return new Block_Z();
                
            case 7: return new Block_Cubo();
        }
        
        return null;
    }
    
    public static Block[] get_representantes(){
        ArrayList<Block> bs = new ArrayList<Block>();
        
        for(int i = 1;;i++){
            Block b = Block.get_instancia(i);
            if(b == null) break;
            bs.add(b);
        }
        
        return bs.toArray(new Block[bs.size()]);
    }
    
    Block(int variacao, int max_variacoes, int max_tamanho, int valor, Color cor_principal, Color cor_escura, Color cor_clara){     
       this.variacao = variacao;
       this.MAX_VARIACOES = max_variacoes;
       this.MAX_TAMANHO = max_tamanho;
       this.VALOR = valor;
       this.COR_PRINCIPAL = cor_principal;
       this.COR_CLARA = cor_clara;
       this.COR_ESCURA = cor_escura;
    }
    
    public boolean rotaciona(int tabuleiro_pedaco[][], int sentido){

        int variacao_aux;
        
        // incremento circular 
        if(variacao+sentido<0)
            variacao_aux = MAX_VARIACOES-1;       
        else if(variacao+sentido<MAX_VARIACOES)
            variacao_aux = variacao+sentido;      
        else
            variacao_aux = 0;
        
        // testa se a proxima rotaçao nao colide com nada no tabuleiro
        for(int i = 0; i < MAX_TAMANHO; i++)
            for(int j = 0; j < MAX_TAMANHO; j++)               
                if(variacoes[variacao_aux][i][j] != 0 && tabuleiro_pedaco[i][j] != 0)// verifica a colisão
                    return false;// retorna false e nao muda o bloco formato para outra rotação
            
        
        //atualiza variação
        variacao = variacao_aux;
        bloco_formato = variacoes[variacao]; //atualiza o bloco formato para a variação atuaol
               
        return true;
    }
    
    public int get_max_tamanho(){
        return MAX_TAMANHO;
    }
    
    public Color get_cor_principal(){
        return COR_PRINCIPAL;
    }
    
    public Color get_cor_escura(){
        return COR_ESCURA;
    }
    
    public Color get_cor_clara(){
        return COR_CLARA;
    }
    
    public int[][] get_bloco_formato(){
        return bloco_formato;
    }
    
    public int get_valor(){
        return VALOR;
    }
    
    protected void set_variacoes(int variacoes[][][]){
        this.variacoes = variacoes;
    }
    
    protected void set_bloco_formato(int bloco_formato[][]){
        this.bloco_formato = bloco_formato;
    }
    
    @Override
    public int hashCode(){
        if(this == null) return 0;
        return this.VALOR;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == null && obj == null) return true;
        if(this == null && this != obj) return false;
        if(obj == null && this != obj) return false;
        return(this.VALOR == ((Block)obj).VALOR);
    }
}
