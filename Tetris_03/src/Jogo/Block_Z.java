package Jogo;

import java.awt.Color;

public class Block_Z extends Block{
    Block_Z(){   
        super(0,2,3,6, new Color(255,100,0), new Color(200,69,0), new Color(255,130,0));
       
        final int[][][] Z_variacoes = {{{6,6,0},
                                        {0,6,6},
                                        {0,0,0}},
                                        
                                        {{0,6,0},
                                         {6,6,0},
                                         {6,0,0}}};
        
        super.set_variacoes(Z_variacoes);
        super.set_bloco_formato(Z_variacoes[0]);
    }
}
