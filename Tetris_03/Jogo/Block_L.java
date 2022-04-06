
package Jogo;

import java.awt.Color;

public class Block_L extends Block{
    Block_L(){   
        super(0,4,3,3, new Color(153, 0, 204), new Color(102, 0, 153), new Color( 204, 0, 255));

        final int[][][] L_variacoes = {{     
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
        
        super.set_variacoes(L_variacoes);
        super.set_bloco_formato(L_variacoes[0]);    
    }
 
}
