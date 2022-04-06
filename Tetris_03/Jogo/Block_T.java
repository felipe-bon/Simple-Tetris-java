package Jogo;

import java.awt.Color;

public class Block_T extends Block{
    Block_T(){         
        super(0,4,3,2, new Color(255,0,255), new Color(139,0,139), new Color(238,130,238));
        
        final int[][][] T_variacoes={{{0,2,0},
                                      {2,2,2},
                                      {0,0,0}},
                                        
                                     {{0,2,0},
                                      {2,2,0},
                                      {0,2,0}},
                                          
                                     {{0,0,0},
                                      {2,2,2},
                                      {0,2,0}},
                                          
                                     {{0,2,0},
                                      {0,2,2},
                                      {0,2,0}}};
      
        super.set_variacoes(T_variacoes);
        super.set_bloco_formato(T_variacoes[0]);
    }
}
