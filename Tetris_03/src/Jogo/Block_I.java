package Jogo;

import java.awt.Color;

public class Block_I extends Block{ 
    Block_I(){     
        super(0,2,4,1,new Color(0, 200, 0), new Color(0, 100, 0), new Color(0, 255, 0)); 
        
        final int[][][] I_variacoes={{     
                                { 0,0,1,0},
                                { 0,0,1,0},
                                { 0,0,1,0},
                                { 0,0,1,0}},
                
                                {{0,0,0,0},
                                { 0,0,0,0},
                                { 1,1,1,1},
                                { 0,0,0,0}}};  
        
        super.set_variacoes(I_variacoes);
        super.set_bloco_formato(I_variacoes[0]);
    }       
}
