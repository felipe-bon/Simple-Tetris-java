
package tetris_03;

import java.awt.Color;

public class Block_S extends Block{
    
    Block_S(){        
        int[][][] n_variacoes ={{{0,5,5},
                                 {5,5,0},
                                 {0,0,0}},
                                        
                                {{5,0,0},
                                 {5,5,0},
                                 {0,5,0}}};
        
        this.variacoes = n_variacoes;
    
        bloco_formato = variacoes[0];
        variacao = 0;
        MAXvariacoes = 2;
        Max_T = 3;
        
        cor_C = new Color(0, 255, 255);
        cor_P = new Color(0, 204, 204);       
        cor_E = new Color(6, 146, 146);                 
    }
    
    // funciona como um get do bloco rotacionado
    @Override
    int[][] rotaciona(int bloco[][], int sentido){
        //if(rotação ok)
        return variacoes[sentido+1];
    }
}
