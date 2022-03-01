
package tetris_03;

import java.awt.Color;

public class Block_L extends Block{
    
    Block_L(){        
        int[][][] n_variacoes = {{     
                                           {3,0,0},
                                           {3,0,0},
                                           {3,3,0}},
                
                                          {{0,0,0},
                                           {3,3,3},
                                           {3,0,0}},
                                          
                                          {{3,3,0},
                                           {0,3,0},
                                           {0,3,0}},
                                          
                                          {{0,0,3},
                                           {3,3,3},
                                           {0,0,0}}};
        
        this.variacoes = n_variacoes;
    
        bloco_formato = variacoes[0];
        variacao = 0;
        MAXvariacoes = 4;
        Max_T = 3;
        
        cor_C = new Color( 204, 0, 255);
        cor_P = new Color(153, 0, 204);       
        cor_E = new Color(102, 0, 153);                 
    }
    
    
    
    
}
