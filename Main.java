import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main implements ActionListener{
    JFrame frame;
    JTextField textField;
    JButton[] fiveButtons = new JButton[5];
    JButton[] fourButtons = new JButton[4];
    JButton[] threeButtons = new JButton[3];
    JButton b1,b2,b3,b4,b5;
    JButton b6,b7,b8,b9;
    JButton b10,b11,b12;


    JPanel panel;

    Font myfont = new Font("Serif",Font.BOLD,30);

    int turn=0;
    String player1;
    String player2;
    Main(){
        frame = new JFrame("5-4-3");       //create window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,550);
        frame.setLayout(null);
        textField = new JTextField();       //create textfield
        textField.setBounds(50,25,300,50);
        textField.setFont(myfont);

        frame.setVisible(true);
        fiveButtons[0] = b1;
        fiveButtons[1] = b2;
        fiveButtons[2] = b3;
        fiveButtons[3] = b4;
        fiveButtons[4] = b5;

        fourButtons[5] = b6;
        fourButtons[6] = b7;
        fourButtons[7] = b8;
        fourButtons[8] = b9;

        threeButtons[9] = b10;
        threeButtons[10] = b11;
        threeButtons[11] = b12;

        for (int i=0;i<5;i++) {    //adding actionlistener and font to each op. button on list
            fiveButtons[i].addActionListener(this);
            fiveButtons[i].setFont(myfont);
            fiveButtons[i].setFocusable(false);
        }
        for (int i=0;i<4;i++) {    //adding actionlistener and font to each op. button on list
            fourButtons[i].addActionListener(this);
            fourButtons[i].setFont(myfont);
            fourButtons[i].setFocusable(false);
        }
        for (int i=0;i<3;i++) {    //adding actionlistener and font to each op. button on list
            threeButtons[i].addActionListener(this);
            threeButtons[i].setFont(myfont);
            threeButtons[i].setFocusable(false);
        }

        panel = new JPanel();
        panel.setBounds(50,100,300,300);
        panel.setLayout(new GridLayout());
    }
    public static void main(String[] args) {
        Main fft = new Main();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        //TODO
    }
}
