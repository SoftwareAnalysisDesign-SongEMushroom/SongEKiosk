import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerHome extends JFrame{

    ManagerHome(){
        setTitle("SONG-E BURGER KIOSK");

        JPanel p = new JPanel();
        JPanel pb = new JPanel();
        JLabel l = new JLabel("SONG-E BURGER KIOSK");

        JButton orderBtn = new JButton("주문정보");
        JButton menuBtn = new JButton("메뉴정보");
        JButton logout = new JButton("로그아웃");

        p.add(l);
        pb.add(orderBtn);
        pb.add(menuBtn);

        add(p);
        add(pb, BorderLayout.SOUTH);

        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerOrder();
            }
        });

        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerMenu();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ManagerHome();
    }
}

