
package tetris_03;

public class Board {
    
    int[][] tabuleiro;
    int inicioY;
    int inicioX;  

    Board(){
        tabuleiro = new int[22][12];
        for(int i = 0; i < 22; i++){
            for(int j = 0; j < 12; j++){
                if(i == 0 || ((j == 0 || j == 11) && i != 21))
                    tabuleiro[i][j] = -1;      
                else if(i == 21)
                    tabuleiro[i][j] = -2;
                else
                    tabuleiro[i][j] = 0;
            }
        }                    
        inicioY = 0;
        inicioX = 4;       
    }
    
    public int apaga_linha(){
        
        
        
        return 1;
    }
    
    public void movimenta_bloco(int comando){
        
        switch (comando){
            
            case 1:
                
            break;
            
            case 2:
                
            break;
            
            case 3:
                
            break;
            
            case 4:
                
            break;
            
            case 5:
                
            break;
        }
        
    }
    
    public void atualiza_tabuleiro(){
        if(inicioY < 20)
            inicioY++;
        else
            inicioY = 0;
    }
    
    public boolean colisao(Block bloco){
       
        // testa a colisão para baixo;
        for(int i = bloco.get_Max_T()-1; i >= 0; i--){
            for(int j = bloco.get_Max_T()-1; j >= 0; j--){
                if(bloco.bloco_formato[i][j] != 0 && tabuleiro[i+inicioY+1][j+inicioX] != 0){
                    return true;
                }
            }
        }
    
        return false;   
    }
    
    public boolean insere_bloco(Block bloco){
              
        if(colisao(bloco)){
            for(int ib = 0; ib < bloco.get_Max_T(); ib++){
                for(int jb = 0; jb < bloco.get_Max_T(); jb++){
                    if(bloco.variacoes[bloco.variacao][ib][jb] > 0){
                        tabuleiro[ib+inicioY][jb+inicioX] = bloco.variacoes[bloco.variacao][ib][jb];
                    }
                }
            }
            inicioY = 0;
            inicioX = 4;
            return true;        
        }
        return false;
    
    }          

}


