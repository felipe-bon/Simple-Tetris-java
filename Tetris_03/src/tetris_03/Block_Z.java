
package tetris_03;


import java.awt.Color;

public class Block_Z extends Block{
    
    Block_Z(){        
        int[][][] n_variacoes = {{{6,6,0},
                                  {0,6,6},
                                  {0,0,0}},
                                        
                                 {{0,6,0},
                                  {6,6,0},
                                  {6,0,0}}};
        
        this.variacoes = n_variacoes;
    
        bloco_formato = variacoes[0];
        variacao = 0;
        MAXvariacoes = 2;
        Max_T = 3;
        
        cor_C = new Color(255,130,0);
        cor_P = new Color(255,100,0);       
        cor_E = new Color(200,69,0);                 
    }
}
