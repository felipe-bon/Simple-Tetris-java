package Jogo;

import java.awt.Color;

public class Block_Cubo extends Block{        
    Block_Cubo(){  
        super(0,1,2,7,new Color(204, 0, 0), new Color(153, 0, 53), new Color(255,0,0));
        
        int[][][] cubo_variacoes = new int[1][2][2];
        
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                cubo_variacoes[0][i][j] = 7;
    
        super.set_bloco_formato(cubo_variacoes[0]);
        super.set_variacoes(cubo_variacoes);
    }
}
