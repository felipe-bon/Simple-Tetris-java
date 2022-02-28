
package tetris_03;

import java.awt.Color;

public class Block_Cubo extends Block{        
    
    Block_Cubo(){
        variacoes = new int[1][2][2];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                variacoes[0][i][j] = 7;
            }
        }
        bloco_formato = variacoes[0];
        MAXvariacoes = 1;
        variacao = 0;
        Max_T = 2;

        cor_P = new Color(204, 0, 0);
        cor_C = new Color(255,0,0);       
        cor_E = new Color(153, 0, 53);              
    }
    
    // funciona como um get do bloco rotacionado
    @Override
    int[][] rotaciona(int bloco[][], int sentido){    
        return variacoes[0];
    }
}
