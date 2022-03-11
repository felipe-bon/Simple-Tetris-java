
package tetris_03;

import java.awt.Color;

public abstract class Block {
    
    private int[][] bloco_formato;
    private int[][][] variacoes;
    private int variacao;
    private final int max_variacoes; 
    private final int max_tamanho;
    private final int valor;
    private final Color cor_principal;    
    private final Color cor_escura;    
    private final Color cor_clara;
    
    
    Block(int variacao, int max_variacoes, int max_tamanho, int valor, Color cor_principal, Color cor_escura, Color cor_clara){     
       this.variacao = variacao;
       this.max_variacoes = max_variacoes;
       this.max_tamanho = max_tamanho;
       this.valor = valor;
       this.cor_principal = cor_principal;
       this.cor_clara = cor_clara;
       this.cor_escura = cor_escura;
    }
    
    
    public boolean rotaciona(int tabuleiro_pedaco[][], int sentido){

        int variacao_aux;
        
        // incremento circular 
        if(variacao+sentido<0)
            variacao_aux = max_variacoes-1;       
        else if(variacao+sentido<max_variacoes)
            variacao_aux = variacao+sentido;      
        else
            variacao_aux = 0;
        
        // testa se a proxima rotaçao nao colide com nada no tabuleiro
        for(int i = 0; i < max_tamanho; i++)
            for(int j = 0; j < max_tamanho; j++)               
                if(variacoes[variacao_aux][i][j] != 0 && tabuleiro_pedaco[i][j] != 0)// verifica a colisão
                    return false;// retorna false e nao muda o bloco formato para outra rotação
            
        
        //atualiza variação
        variacao = variacao_aux;
        bloco_formato = variacoes[variacao]; //atualiza o bloco formato para a variação atuaol
               
        return true;
    }
    
    public int get_max_tamanho(){
        return max_tamanho;
    }
    
    public Color get_cor_principal(){
        return cor_principal;
    }
    
    public Color get_cor_escura(){
        return cor_escura;
    }
    
    public Color get_cor_clara(){
        return cor_clara;
    }
    
    public int[][] get_bloco_formato(){
        return bloco_formato;
    }
    
    public int get_valor(){
        return valor;
    }
    
    protected void set_variacoes(int variacoes[][][]){
        this.variacoes = variacoes;
    }
    
    protected void set_bloco_formato(int bloco_formato[][]){
        this.bloco_formato = bloco_formato;
    }   
}
