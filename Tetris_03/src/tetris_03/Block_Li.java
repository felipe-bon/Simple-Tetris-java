
package tetris_03;

import java.awt.Color;

public class Block_Li extends Block{
    
    Block_Li(){        
        int[][][] n_variacoes = {{{0,4,0,0},
                                  {0,4,0,0},
                                  {0,4,0,0},
                                  {4,4,0,0}},
                                        
                                  {{0,0,0,0},
                                   {4,0,0,0},
                                   {4,4,4,4},
                                   {0,0,0,0}},
                                          
                                   {{0,0,4,4},
                                    {0,0,4,0},
                                    {0,0,4,0},
                                    {0,0,4,0}},
                                          
                                   {{0,0,0,0},
                                    {4,4,4,4},
                                    {0,0,0,4},
                                    {0,0,0,0}}};
        
        this.variacoes = n_variacoes;
    
        bloco_formato = variacoes[0];
        variacao = 0;
        MAXvariacoes = 4;
        Max_T = 4;
        
        cor_C = new Color(255, 255, 0);
        cor_P = new Color(204, 204, 0);       
        cor_E = new Color(153, 153, 0);                 
    }
    
    // funciona como um get do bloco rotacionado
    @Override
    int[][] rotaciona(int bloco[][], int sentido){
        //if(rotação ok)
        return variacoes[sentido+1];
    }
    
}
