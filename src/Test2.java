import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Test2 extends JFrame {

    private DefaultListModel<String> basketModel;
    private JList<String> basketList;
    private JTextField totalPriceField;
    private JTextField totalCountField;
    private int totalPrice;
    private int totalCount;
    private List<MenuItem> menuItems;
    private Map<String, Integer> menuList;

    public Test2(Map<String, Integer> menuList, int totalPrice, int totalCount, List<MenuItem> menuItems) {
        this.menuList = menuList;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.menuItems = menuItems;

        setTitle("결제 화면");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 장바구니 패널 생성
        JPanel basketPanel = createBasketPanel(menuList);
        add(basketPanel, BorderLayout.CENTER);

        // 총 가격 및 총 수량 표시 패널 생성
        JPanel totalPanel = createTotalPanel();
        add(totalPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createBasketPanel(Map<String, Integer> menuList) {
        JPanel panel = new JPanel(new BorderLayout());

        // 장바구니 리스트
        basketModel = new DefaultListModel<>();
        basketList = new JList<>(basketModel);
        JScrollPane scrollPane = new JScrollPane(basketList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 장바구니 텍스트
        JLabel basketLabel = new JLabel("장바구니");
        panel.add(basketLabel, BorderLayout.NORTH);

        // 데이터 추가
        for (String itemName : menuList.keySet()) {
            int quantity = menuList.get(itemName);
            int itemTotalPrice = quantity * getPriceByName(itemName); // 아이템 총 가격 계산
            basketModel.addElement(itemName + " - 수량: " + quantity + " - 가격: " + itemTotalPrice);
        }

        return panel;
    }

    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 총 수량 및 총 가격
        totalCountField = new JTextField("총 수량: " + totalCount);
        totalCountField.setEditable(false);
        totalPriceField = new JTextField("총 가격: " + totalPrice);
        totalPriceField.setEditable(false);
        panel.add(totalCountField, BorderLayout.WEST);
        panel.add(totalPriceField, BorderLayout.CENTER);

        // 결제 버튼 패널
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        // 쿠폰 결제 버튼
        JButton couponButton = new JButton("쿠폰 결제");
        couponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "쿠폰 결제가 완료되었습니다.");
            }
        });
        buttonPanel.add(couponButton);

        // 카드 결제 버튼
        JButton cardButton = new JButton("카드 결제");
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "카드 결제가 완료되었습니다.");
            }
        });
        buttonPanel.add(cardButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private int getPriceByName(String itemName) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getName().equals(itemName)) {
                return menuItem.getPrice();
            }
        }
        return 0; // 아이템을 찾지 못한 경우
    }
}
