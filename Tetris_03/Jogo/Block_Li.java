package Jogo;

import java.awt.Color;

public class Block_Li extends Block{ 
    Block_Li(){     
        
        super(0,4,3,4, new Color(204, 204, 0), new Color(153, 153, 0), new Color(255, 255, 0));

        final int[][][] Li_variacoes = {{{0,4,0},
                                         {0,4,0},
                                         {4,4,0}},
                                        
                                         {{4,0,0},
                                          {4,4,4},
                                          {0,0,0}},
                                          
                                         {{4,4,0},
                                          {4,0,0},
                                          {4,0,0}},
                                          
                                         {{4,4,4},
                                          {0,0,4},
                                          {0,0,0}}};
        
        super.set_variacoes(Li_variacoes);
        super.set_bloco_formato(Li_variacoes[0]);
    }
}
