import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

class MenuItem {
    private String name;
    private int price;
    private String imagePath; // 이미지 경로 추가

    public MenuItem(String name, int price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}

public class Test extends JFrame implements ActionListener {

    private DefaultListModel<String> cartModel;
    private JList<String> cartList;
    private JTextField totalPriceField;
    private JTextField totalCountField;
    private int totalPrice = 0;
    private int totalCount = 0;
    private Map<String, Integer> menuList;
    private List<MenuItem> singleMenuItems;
    private List<MenuItem> setMenuItems;

    public Test() {
        singleMenuItems = new ArrayList<>();
        singleMenuItems.add(new MenuItem("햄버거1", 5000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거2", 6000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거3", 5000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거4", 6000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거5", 5000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거6", 6000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거7", 5000, "hamburger.png"));
        singleMenuItems.add(new MenuItem("햄버거8", 6000, "hamburger.png"));

        setMenuItems = new ArrayList<>();
        setMenuItems.add(new MenuItem("세트1", 12000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트2", 15000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트3", 12000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트4", 15000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트5", 12000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트6", 15000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트7", 12000, "hamburger.png"));
        setMenuItems.add(new MenuItem("세트8", 15000, "hamburger.png"));

        menuList = new TreeMap<>(); // TreeMap을 사용하여 오름차순 정렬

        setTitle("키오스크");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 탭 패널 생성
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel singlePanel = createMenuPanel("단품", singleMenuItems);
        JPanel setPanel = createMenuPanel("세트", setMenuItems);
        tabbedPane.addTab("단품", singlePanel);
        tabbedPane.addTab("세트", setPanel);
        add(tabbedPane, BorderLayout.NORTH);

        // 장바구니 패널 생성
        JPanel cartPanel = createCartPanel();
        add(cartPanel, BorderLayout.CENTER);

        // 총 가격 및 총 수량 표시 패널 생성
        JPanel totalPanel = createTotalPanel();
        add(totalPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createMenuPanel(String categoryName, List<MenuItem> items) {
        JPanel panel = new JPanel(new GridLayout(2, 4));
        for (MenuItem menuItem : items) {
            JPanel buttonPanel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(menuItem.getName() + " " + menuItem.getPrice() + "원", SwingConstants.CENTER);
            buttonPanel.add(label, BorderLayout.CENTER);

            // 이미지를 표시할 라벨 생성
            ImageIcon icon = new ImageIcon(getClass().getResource(menuItem.getImagePath()));
            JLabel imageLabel = new JLabel(icon, SwingConstants.CENTER);
            buttonPanel.add(imageLabel, BorderLayout.NORTH);

            buttonPanel.setPreferredSize(new Dimension(100, 100)); // 버튼 크기 조정

            buttonPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 마우스 클릭 시 장바구니에 추가
                    addToCart(menuItem.getName(), menuItem.getPrice());
                }
            });

            panel.add(buttonPanel);
        }
        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 장바구니 리스트
        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        cartList.setCellRenderer(new CartListRenderer());
        JScrollPane scrollPane = new JScrollPane(cartList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 장바구니 삭제 버튼
        JButton clearCartButton = new JButton("장바구니 비우기");
        clearCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuList.clear();
                totalPrice = 0;
                totalCount = 0;
                updateCart();
            }
        });
        panel.add(clearCartButton, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 총 수량 및 총 가격
        totalCountField = new JTextField("총 수량: 0");
        totalCountField.setEditable(false);
        totalPriceField = new JTextField("총 가격: 0");
        totalPriceField.setEditable(false);
        panel.add(totalCountField, BorderLayout.WEST);
        panel.add(totalPriceField, BorderLayout.CENTER);

        // 결제하기 버튼
        JButton paymentButton = new JButton("결제하기");
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                List<MenuItem> allMenuItems = new ArrayList<>();
                allMenuItems.addAll(singleMenuItems);
                allMenuItems.addAll(setMenuItems);
                new Test2(menuList, totalPrice, totalCount, allMenuItems); // 모든 메뉴 항목을 전달
            }
        });
        panel.add(paymentButton, BorderLayout.EAST);

        return panel;
    }

    private void addToCart(String itemName, int price) {
        if (menuList.containsKey(itemName)) {
            menuList.put(itemName, menuList.get(itemName) + 1);
        } else {
            menuList.put(itemName, 1);
        }
        totalPrice += price;
        totalCount++;
        updateCart();
    }

    private void updateCart() {
        cartModel.clear();
        for (String itemName : menuList.keySet()) {
            int quantity = menuList.get(itemName);
            int itemTotalPrice = quantity * getPriceByName(itemName);
            cartModel.addElement(itemName + " - 수량: " + quantity + " - 가격: " + itemTotalPrice);
        }
        updateTotal();
    }

    private int getPriceByName(String itemName) {
        for (MenuItem menuItem : singleMenuItems) {
            if (menuItem.getName().equals(itemName)) {
                return menuItem.getPrice();
            }
        }
        for (MenuItem menuItem : setMenuItems) {
            if (menuItem.getName().equals(itemName)) {
                return menuItem.getPrice();
            }
        }
        return 0; // 없으면 가격을 0으로 반환
    }

    private void updateTotal() {
        totalCountField.setText("총 수량: " + totalCount);
        totalPriceField.setText("총 가격: " + totalPrice);
    }

    public static void main(String[] args) {
        new Test();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 장바구니에 아이템 추가
        JButton button = (JButton) e.getSource(); // 클릭된 버튼 가져오기
        String buttonText = button.getText();
        String itemName = buttonText.split(" ")[0]; // 버튼 텍스트에서 아이템 이름 가져오기
        // 버튼 텍스트에서 아이템 가격 가져오기
        int itemPrice = Integer.parseInt(buttonText.split(" ")[1].replace("원", ""));

        addToCart(itemName, itemPrice);
    }

    class CartListRenderer extends JPanel implements ListCellRenderer<String> {

        private JLabel label;
        private JButton minusButton;
        private JButton plusButton;

        public CartListRenderer() {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding 설정

            label = new JLabel();
            add(label, BorderLayout.CENTER);

            // 수량 조절을 위한 버튼 생성
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

            // - 버튼
            minusButton = new JButton("-");
            minusButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = cartList.getSelectedIndex();
                    if (index != -1) {
                        String selectedItem = cartModel.getElementAt(index);
                        String itemName = selectedItem.split(" - ")[0];
                        int itemPrice = getPriceByName(itemName);
                        int quantity = menuList.get(itemName);
                        if (quantity > 1) {
                            menuList.put(itemName, quantity - 1);
                            totalCount--;
                            totalPrice -= itemPrice;
                            updateCart(); // 장바구니 업데이트
                        } else if (quantity == 1) {
                            menuList.remove(itemName);
                            totalCount--;
                            totalPrice -= itemPrice;
                            updateCart(); // 장바구니 업데이트
                        }
                    }
                }
            });
            buttonPanel.add(minusButton);

            // + 버튼
            plusButton = new JButton("+");
            plusButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = cartList.getSelectedIndex();
                    if (index != -1) {
                        String selectedItem = cartModel.getElementAt(index);
                        String itemName = selectedItem.split(" - ")[0];
                        int itemPrice = getPriceByName(itemName);
                        menuList.put(itemName, menuList.get(itemName) + 1);
                        totalCount++;
                        totalPrice += itemPrice;
                        updateCart(); // 장바구니 업데이트
                    }
                }
            });
            buttonPanel.add(plusButton);

            add(buttonPanel, BorderLayout.EAST); // 버튼 패널을 CartListRenderer에 추가
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            label.setText(value);
            return this;
        }
    }
}

