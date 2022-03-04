
package tetris_03;

import java.awt.Color;

public class Block_I extends Block{
    
    Block_I(){        
        final int[][][]n_variacoes={{     
                                { 0,0,1,0},
                                { 0,0,1,0},
                                { 0,0,1,0},
                                { 0,0,1,0}},
                
                                {{0,0,0,0},
                                { 0,0,0,0},
                                { 1,1,1,1},
                                { 0,0,0,0}}};  
        variacoes = n_variacoes;
        bloco_formato = variacoes[0];
        variacao = 0;
        MAXvariacoes = 2;
        Max_T = 4;

        cor_C = new Color(0, 255, 0);
        cor_P = new Color(0, 200, 0);       
        cor_E = new Color(0, 100, 0);           
    }       
    
}
