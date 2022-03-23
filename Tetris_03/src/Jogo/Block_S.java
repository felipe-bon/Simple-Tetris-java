package Jogo;

import java.awt.Color;

public class Block_S extends Block{ 
    Block_S(){  
        
        super(0,2,3,5, new Color(0, 204, 204), new Color(6, 146, 146), new Color(0, 255, 255));
        
        final int[][][] S_variacoes ={{{0,5,5},
                                       {5,5,0},
                                       {0,0,0}},
                                        
                                      {{5,0,0},
                                       {5,5,0},
                                       {0,5,0}}};
        
        super.set_variacoes(S_variacoes);
        super.set_bloco_formato(S_variacoes[0]);
    }
}
