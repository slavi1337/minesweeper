import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Home {
    JFrame frame=new JFrame();
    JPanel panel=new JPanel();
    int width=700;
    int height=700;
    int mineCount=1;
    JButton start=new JButton("Start");
    JTextField mineText=new JTextField(mineCount);
    JRadioButton easy=new JRadioButton("Easy - 8x8 with 10 mines");
    JRadioButton medium=new JRadioButton("Medium - 12x12 with 20 mines");
    JRadioButton hard=new JRadioButton("Hard - 15x15 with 30 mines");

    Home(){
        frame.setTitle("Minesweeper setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        panel.setLayout(new FlowLayout());
        panel.setSize(width, height/4);
        frame.add(start,BorderLayout.SOUTH);

        panel.add(easy);
        panel.add(medium);
        panel.add(hard);

        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);

        start.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(easy.isSelected() && !medium.isSelected() && !hard.isSelected()){
                        System.out.println("Easy");
                        frame.dispose();
                        new Minesweeper(8,8,10);
                    }
                    else if(!easy.isSelected() && medium.isSelected() && !hard.isSelected()){
                        System.out.println("Medium");
                        frame.dispose();
                        new Minesweeper(12,12,20);
                    }
                    else if(!easy.isSelected() && !medium.isSelected() && hard.isSelected()){
                        System.out.println("Hard");
                        frame.dispose();
                        new Minesweeper(15,15,30);
                    }
                    else{
                        System.out.println("Only 1 difficulty can be chosen");
                    }
                }
            }
        });
    }
}
