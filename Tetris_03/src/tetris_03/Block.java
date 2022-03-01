
package tetris_03;

import java.awt.Color;

public abstract class Block {
    
    int[][] bloco_formato;
    int[][][] variacoes;
    int variacao;
    protected int MAXvariacoes; 
    protected int Max_T;
    Color cor_P, cor_E, cor_C;           
    
    public void rotaciona(int tabuleiro_pedaco[][], int sentido){

        if(variacao+sentido < MAXvariacoes)
            variacao = variacao + sentido;      
        else
            variacao = 0; 
        
        bloco_formato = variacoes[variacao];  

//         anda no vetor de rotações de acordo com o sentido recebido (-1 ou 1)        
//         faz a rotação enquanto verifica
//         if(rotação deu errado){
//        
//            return bloco_formato;
//        }
//        else
//            atualiza a bloco_formato para a rotação atual
//            return bloco_formato;
    
        //return variacoes[sentido];
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
