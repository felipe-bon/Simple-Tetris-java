
package tetris_03;



import java.awt.Color;

public class Block_T extends Block{
    
    Block_T(){        
        int[][][] n_variacoes={{{0,2,0},
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
        
        this.variacoes = n_variacoes;
    
        bloco_formato = variacoes[0];
        variacao = 0;
        MAXvariacoes = 4;
        Max_T = 3;
        
        cor_C = new Color(238,130,238);
        cor_P = new Color(255,0,255);       
        cor_E = new Color(139,0,139);                 
    }
    
    // funciona como um get do bloco rotacionado
    @Override
    int[][] rotaciona(int bloco[][], int sentido){
        //if(rotação ok)
        return variacoes[sentido+1];
    } 
}
