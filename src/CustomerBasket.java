import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerBasket extends JFrame implements ActionListener {

    private DefaultListModel<CartItem> cartModel;
    private JList<CartItem> cartList;
    private JTextField totalPriceField;
    private JTextField totalItemsLabel;
    private int totalPrice = 0;
    private int totalItems = 0;

    public CustomerBasket() {
        setTitle("키오스크");
        setSize(340, 641);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 장바구니 패널 생성
        JPanel cartPanel = createCartPanel();
        add(cartPanel, BorderLayout.CENTER);

        // 결제 버튼 패널 생성
        JPanel paymentPanel = createPaymentPanel();
        add(paymentPanel, BorderLayout.SOUTH);

        // 예시 데이터 추가
        addToCart("햄버거1", 5000);
        addToCart("햄버거2", 6000);
        addToCart("세트1", 8000);
        addToCart("세트2", 9000);

        setVisible(true);
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 장바구니 리스트
        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        cartList.setCellRenderer(new CartItemRenderer());
        JScrollPane scrollPane = new JScrollPane(cartList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 총 가격 및 총 수량 패널
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
        totalPriceField = new JTextField("총 가격: 0원");
        totalPriceField.setEditable(false);
        totalItemsLabel = new JTextField("총 수량: 0");
        bottomPanel.add(new JLabel("총 수량:"));
        bottomPanel.add(totalItemsLabel);
        bottomPanel.add(new JLabel("총 가격:"));
        bottomPanel.add(totalPriceField);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton couponPayButton = new JButton("쿠폰 결제");
        couponPayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "쿠폰 결제가 완료되었습니다.");
            }
        });
        panel.add(couponPayButton);

        JButton cardPayButton = new JButton("카드 결제");
        cardPayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "카드를 투입구에 넣어주세요.");
                //카드 유효 검사 로직
            }
        });
        panel.add(cardPayButton);

        return panel;
    }

    private void addToCart(String itemName, int price) {
        boolean itemExists = false;
        for (int i = 0; i < cartModel.size(); i++) {
            CartItem item = cartModel.getElementAt(i);
            if (item.getName().equals(itemName)) {
                item.incrementQuantity();
                totalPrice += price;
                totalItems++;
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            cartModel.addElement(new CartItem(itemName, price, 1));
            totalPrice += price;
            totalItems++;
        }
        updateTotal();
    }

    private void updateTotal() {
        totalPriceField.setText("총 가격: " + totalPrice + "원");
        totalItemsLabel.setText("총 수량: " + totalItems);
    }

    public static void main(String[] args) {
        new CustomerBasket();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // CartItem 클래스 정의
    class CartItem {
        private String name;
        private int price;
        private int quantity;

        public CartItem(String name, int price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void incrementQuantity() {
            quantity++;
        }

        public void decrementQuantity() {
            if (quantity > 0) {
                quantity--;
            }
        }

        @Override
        public String toString() {
            return name + " / " + quantity + "개 / " + (price * quantity) + "원";
        }
    }

    // CartItemRenderer 클래스 정의
    class CartItemRenderer extends JPanel implements ListCellRenderer<CartItem> {
        private JLabel nameLabel;
        private JLabel quantityLabel;
        private JLabel priceLabel;

        public CartItemRenderer() {
            setLayout(new GridLayout(1, 5));
            nameLabel = new JLabel();
            quantityLabel = new JLabel();
            priceLabel = new JLabel();

            add(nameLabel);
            add(quantityLabel);
            add(priceLabel);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends CartItem> list, CartItem value, int index, boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(value.getName());
            quantityLabel.setText(String.valueOf(value.getQuantity()));
            priceLabel.setText((value.getPrice() * value.getQuantity()) + "원");

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}
