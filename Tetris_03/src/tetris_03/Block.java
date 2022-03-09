
package tetris_03;

import java.awt.Color;

public abstract class Block {
    
    protected int[][] bloco_formato;
    protected int[][][] variacoes;
    protected int variacao;
    protected int MAXvariacoes; 
    protected int Max_T;
    protected Color cor_P, cor_E, cor_C;           
    
    public void rotaciona(int tabuleiro_pedaco[][], int sentido){

        int variacaoAUX;
        
        if(variacao+sentido<MAXvariacoes)
            variacaoAUX = variacao+sentido;      
        else
            variacaoAUX = 0;
        
        // testa se a proxima rotaçao nao colide com nada no tabuleiro
        for(int i = 0; i < Max_T; i++){
            for(int j = 0; j < Max_T; j++){
                // verifica a colisão
                if(variacoes[variacaoAUX][i][j] != 0 && tabuleiro_pedaco[i][j] != 0)
                    return;// retorna e nao muda o bloco formato para outra rotação
            }
        }
        //atualiza variação
        variacao = variacaoAUX;
        bloco_formato = variacoes[variacao]; //atualiza o bloco formato para a variação atuaol
               
    }
    
    public int get_Max_T(){
        return Max_T;
    }
    
    public Color get_Color_P(){
        return cor_P;
    }
    
    public Color get_Color_E(){
        return cor_E;
    }
    
    public Color get_Color_C(){
        return cor_C;
    }
    
    
}
