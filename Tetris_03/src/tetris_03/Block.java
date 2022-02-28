
package tetris_03;

import java.awt.Color;

public abstract class Block {
    
    int[][] bloco_formato;
    int[][][] variacoes;
    int variacao;
    protected int MAXvariacoes; 
    protected int Max_T;
    Color cor_P, cor_E, cor_C;           
    
    int[][] rotaciona(int bloco[][], int sentido){

//         anda no vetor de rotações de acordo com o sentido recebido (-1 ou 1)        
//         faz a rotação enquanto verifica
//         if(rotação deu errado){
//        
//            return bloco_formato;
//        }
//        else
//            atualiza a bloco_formato para a rotação atual
//            return bloco_formato;
    
        return variacoes[sentido];
    }
    
    public int get_Max_T(){
        return Max_T;
    }
}
