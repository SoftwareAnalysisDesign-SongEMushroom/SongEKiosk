import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerHome extends JFrame implements ActionListener {

    public CustomerHome() {
        setTitle("키오스크");
        setSize(340, 641);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 상단 버튼
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 왼쪽 정렬
        JButton topButton = new JButton("관리자 모드");
        topButton.addActionListener(this);
        topPanel.add(topButton);
        add(topPanel, BorderLayout.EAST);

        // 하단 버튼
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton bottomButton1 = new JButton("포장하기");
        bottomButton1.addActionListener(this);
        bottomPanel.add(bottomButton1, BorderLayout.WEST); // 왼쪽에 배치
        JButton bottomButton2 = new JButton("먹고가기");
        bottomButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 하단 버튼 2를 눌렀을 때의 동작 - CustomerBasket으로 넘어가기
                dispose(); // 현재 창 닫기
                //new CustomerMenu(); // 새로운 창 열기
                new Test();
            }
        });
        bottomButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 하단 버튼 2를 눌렀을 때의 동작 - CustomerBasket으로 넘어가기
                dispose(); // 현재 창 닫기
                //new CustomerMenu(); // 새로운 창 열기
                new Test();
            }
        });
        bottomPanel.add(bottomButton2, BorderLayout.EAST); // 오른쪽에 배치
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    public static void main(String[] args) {
        new CustomerHome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
