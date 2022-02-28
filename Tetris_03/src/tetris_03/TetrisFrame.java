
package tetris_03;

import javax.swing.JFrame;

public class TetrisFrame extends JFrame{
    
    
    
    TetrisFrame(){
              
        this.add(new TetrisPanel()); 
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
