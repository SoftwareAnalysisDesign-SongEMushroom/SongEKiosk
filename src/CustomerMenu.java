import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CustomerMenu extends JFrame implements ActionListener {

    private DefaultListModel<String> cartModel;
    private JList<String> cartList;
    private JTextField totalPriceField;
    private JTextField totalCountField;
    private int totalPrice = 0;
    private int totalItems = 0;
    private HashMap<String, Integer> menuList;

    public CustomerMenu() {
        menuList = new HashMap<>();

        setTitle("키오스크");
        setSize(340, 641);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 메인 패널 설정
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // 메뉴 패널 생성
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        // 장바구니 패널 생성
        JPanel cartPanel = createCartPanel();
        mainPanel.add(cartPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1)); // 2개의 행, 1개의 열

        // 탭 버튼 생성
        JTabbedPane tabbedPane = new JTabbedPane();

        // 햄버거 탭
        JPanel burgerPanel = new JPanel(new GridLayout(2, 4));
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton(new ImageIcon("hamburger.png")); // 이미지 아이콘 설정
            button.setText("[메뉴이름] 가격");
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setVerticalTextPosition(JButton.BOTTOM);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addToCart("[메뉴이름]", 5000);
                }
            });
            burgerPanel.add(button);
        }
        tabbedPane.addTab("햄버거", burgerPanel);

        // 세트 탭
        JPanel setPanel = new JPanel(new GridLayout(2, 4));
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton(new ImageIcon("hamburger.png")); // 이미지 아이콘 설정
            button.setText("[메뉴이름] 가격");
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setVerticalTextPosition(JButton.BOTTOM);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addToCart("[메뉴이름]", 8000);
                }
            });
            setPanel.add(button);
        }
        tabbedPane.addTab("세트", setPanel);

        panel.add(tabbedPane);

        return panel;
    }


    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 장바구니 리스트
        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        JScrollPane scrollPane = new JScrollPane(cartList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 총 가격 및 결제 버튼 패널
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2));
        totalPriceField = new JTextField("총 가격: 0");
        totalPriceField.setEditable(false);
        totalCountField = new JTextField("총 수량: 0");
        totalCountField.setEditable(false);

        bottomPanel.add(totalCountField);
        bottomPanel.add(totalPriceField);

        panel.add(bottomPanel, BorderLayout.LINE_END); // 오른쪽에 배치

        // 버튼용 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 오른쪽 정렬
        JButton anotherButton = new JButton("결제하기");
        anotherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 하단 버튼 2를 눌렀을 때의 동작 - CustomerBasket으로 넘어가기
                dispose(); // 현재 창 닫기
                new CustomerBasket(); // 새로운 창 열기
            }
        });
        // 다른 버튼에 대한 ActionListener 등록
        buttonPanel.add(anotherButton);
        panel.add(buttonPanel, BorderLayout.SOUTH); // 장바구니 아래에 배치

        return panel;
    }


    private void addToCart(String itemName, int price) {
        if (menuList.containsKey(itemName)) {
            menuList.put(itemName, menuList.get(itemName) + 1);
        } else {
            menuList.put(itemName, 1);
        }
        totalPrice += price;
        totalItems++;
        updateCart();
    }

    private void updateCart() {
        cartModel.clear();
        for (String itemName : menuList.keySet()) {
            int quantity = menuList.get(itemName);
            int itemTotalPrice = quantity * 5000; // 예제에서는 고정된 가격 사용
            cartModel.addElement(itemName + " - 수량: " + quantity + " - 가격: " + itemTotalPrice);
        }
        updateTotal();
    }

    private void updateTotal() {
        totalPriceField.setText("총 가격: " + totalPrice);
        totalCountField.setText("총 수량: " + totalItems);
    }

    private void changeQuantity(String itemName, int change) {
        if (menuList.containsKey(itemName)) {
            int newQuantity = menuList.get(itemName) + change;
            if (newQuantity > 0) {
                menuList.put(itemName, newQuantity);
                totalPrice += change * 5000; // 예제에서는 고정된 가격 사용
                totalItems += change;
            } else {
                totalPrice -= menuList.get(itemName) * 5000;
                totalItems -= menuList.get(itemName);
                menuList.remove(itemName);
            }
            updateCart();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("+")) {
            int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedValue = cartList.getSelectedValue();
                String itemName = selectedValue.split(" - ")[0];
                changeQuantity(itemName, 1);
            }
        } else if (actionCommand.equals("-")) {
            int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedValue = cartList.getSelectedValue();
                String itemName = selectedValue.split(" - ")[0];
                changeQuantity(itemName, -1);
            }
        }
    }

    public static void main(String[] args) {
        new CustomerMenu();
    }
}
