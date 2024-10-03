package api.anhtrangapiv2.service.order;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dtos.*;
import api.anhtrangapiv2.models.*;
import api.anhtrangapiv2.repositories.*;
import api.anhtrangapiv2.responses.*;
import api.anhtrangapiv2.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final EmailService emailService;

    @Override
    @Transactional
    public Order creatOrder(OrderDTO order) throws Exception{
        Delivery existingDelivery = deliveryRepository.findById(order.getDeliveryId()).orElseThrow(
            () -> new RuntimeException("Delivery not found with id: " + order.getDeliveryId())
        );
        User existingUser = userRepository.findById(order.getUserId()).orElseThrow(
            () -> new RuntimeException("User not found with id: " + order.getUserId())
        );
        StringBuilder productsInfo = new StringBuilder();
        Order newOrder = Order.builder()
        .status(order.getStatus())
        .customerName(order.getCustomerName())
        .customerTel(order.getCustomerTel())
        .customerAddress(order.getCustomerAddress())
        .customerNote(order.getCustomerNote())
        .user(existingUser)
        .note(order.getNote())
        .total(order.getTotal())
        .delivery(existingDelivery)
        .build();
        orderRepository.save(newOrder);
        String code = generateCode(newOrder);
        newOrder.setCode(code);
        orderRepository.save(newOrder);
        for(ProductOrderDTO po: order.getProductOrder()){
            Product existingProduct = productRepository.findById(po.getProductId()).orElseThrow(
                () -> new RuntimeException("Product not found with id: " + po.getProductId())
            );
            ProductOrder newPO = ProductOrder.builder()
            .color(po.getColor())
            .size(po.getSize())
            .quantity(po.getQuantity())
            .order(newOrder)
            .product(existingProduct).build();
            productOrderRepository.save(newPO);
            long cost = existingProduct.getSale_cost()>0?existingProduct.getSale_cost():existingProduct.getCost();
            productsInfo.append(String.format("   + %s: [%s, %s] SL: %s Đơn giá: %s\n",existingProduct.getName(),
            po.getSize(), po.getColor(),po.getQuantity(),cost));
        }
        String customerEmail = existingUser.getEmail();
        String subject = String.format("ANH TRANG SHOP CONFIRM: #%s", code);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String day = newOrder.getCreateAt().format(formatter);

        String emailBody = String.format("Cảm ơn quí khách đã ủng hộ AnhTrangShop!!!\n\n"
        +"- Mã đơn hàng: %s\n"
        +"- Ngày đặt hàng: %s\n"
        +"- Khách hàng: %s\n"
        +"- Số điện thoại: %s\n"
        +"- Địa chỉ giao hàng: %s\n"
        +"- Hình thức giao hàng: %s\n\n"
        +"- Đơn hàng đã đặt:\n"
        +"%s"
        +"- Tổng giá trị đơn hàng: %s\n\n"
        +"Mọi thắc mắc xin vui lòng liên hệ shop qua số điện thoại: 09XXXXXXXX"
        ,code,day,newOrder.getCustomerName(),newOrder.getCustomerTel(),newOrder.getCustomerAddress(),
        newOrder.getDelivery().getName(), productsInfo,newOrder.getTotal());
        emailService.sendEmail(customerEmail, subject, emailBody);
        // emailService.sendEmail(customerEmail, "hello", "test");
        return newOrder;
    }
    private String generateCode(Order order){
        int id = order.getId();
        Integer year = order.getCreateAt().getYear();
        int size = orderRepository.findByUserId(order.getUser().getId()).size();
        Random random = new Random();
        char randomChar1 = (char) (random.nextInt(26) + 'A');
        char randomChar2 = (char) (random.nextInt(26) + 'A');
        String code = year.toString().substring(2)
        + randomChar1 + randomChar2 + String.format("%04d", size);
        return code;
    }

    @Override
    @Transactional
    public String deleteOrder(int id) throws Exception{
        Order existingOrder = findOrderById(id);
        orderRepository.delete(existingOrder);
        return "Delete Success";
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        List<OrderResponse> orders = orderRepository.findAll().stream()
        .map(o -> {
            List<ProductOrderResponse> productOrders = o.getProductOrder().stream()
            .map(po ->{
                List<ImgProduct> imgs = po.getProduct().getImgProduct();
                String img = imgs.get(0).getLink();
    
                ProductForOrderResponse pfo = ProductForOrderResponse.builder()
                    .id(po.getProduct().getId())
                    .name(po.getProduct().getName())
                    .img(img).build();
                return ProductOrderResponse.builder()
                .color(po.getColor())
                .size(po.getSize())
                .id(po.getId())
                .quantity(po.getQuantity())
                .product(pfo)
                .build();
            }).toList();

            return OrderResponse.builder()
            .id(o.getId())
            .status(o.getStatus())
            .code(o.getCode())
            .customerName(o.getCustomerName())
            .customerTel(o.getCustomerTel())
            .customerAddress(o.getCustomerAddress())
            .customerNote(o.getCustomerNote())
            .note(o.getNote())
            .total(o.getTotal())
            .createdAt(o.getCreateAt())
            .delivery(o.getDelivery())
            .productOrder(productOrders)
            .userPhoneNumber(o.getUser().getPhoneNumber())
            .build();
        })
        .toList();

        return orders;
    }

    @Override
    public OrderResponse findOneOrder(int id) throws Exception{
        Order existingOrder = findOrderById(id);
        List<ProductOrderResponse> productOrders = existingOrder.getProductOrder().stream()
        .map(po -> {
            List<ImgProduct> imgs = po.getProduct().getImgProduct();
            String img = imgs.get(0).getLink();

            ProductForOrderResponse pfo = ProductForOrderResponse.builder()
                .id(po.getProduct().getId())
                .name(po.getProduct().getName())
                .img(img).build();

            return ProductOrderResponse.builder()
                .color(po.getColor())
                .size(po.getSize())
                .id(po.getId())
                .quantity(po.getQuantity())
                .product(pfo)
                .build();
        })
        .toList();

        return OrderResponse.builder()
        .id(existingOrder.getId())
        .status(existingOrder.getStatus())
        .code(existingOrder.getCode())
        .customerName(existingOrder.getCustomerName())
        .customerTel(existingOrder.getCustomerTel())
        .customerAddress(existingOrder.getCustomerAddress())
        .customerNote(existingOrder.getCustomerNote())
        .note(existingOrder.getNote())
        .total(existingOrder.getTotal())
        .createdAt(existingOrder.getCreateAt())
        .delivery(existingOrder.getDelivery())
        .productOrder(productOrders)
        .userPhoneNumber(existingOrder.getUser().getPhoneNumber())
        .build();
    }

    @Override
    public Order findOrderById(int id) throws Exception{
        return         orderRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Order not found with id: " + id)
        );
    }

    @Override
    @Transactional
    public String updateOrder(int id, UpdatedOrderDTO order) throws Exception{
        Order existingOrder = findOrderById(id);
        if(order.getCustomerName()!=null) existingOrder.setCustomerName(order.getCustomerName());
        if(order.getCustomerTel()!=null) existingOrder.setCustomerTel(order.getCustomerTel());
        if(order.getCustomerNote()!=null) existingOrder.setCustomerNote(order.getCustomerNote());
        if(order.getNote()!=null) existingOrder.setNote(order.getNote());
        if(order.getStatus()!=null) existingOrder.setStatus(order.getStatus());
        orderRepository.save(existingOrder);
        return "Update Success";
    }
    
    @Override
    public String[] getOrderStatusColumnType() {
        String sql = "SELECT SUBSTRING(COLUMN_TYPE, 6, LENGTH(COLUMN_TYPE) - 6) AS status " +
                     "FROM INFORMATION_SCHEMA.COLUMNS " +
                     "WHERE TABLE_NAME = '_order' " +
                     "AND COLUMN_NAME = 'status'";

        String result = jdbcTemplate.queryForObject(sql, String.class);
        String[] arrResult = result.replaceAll("'", "").split(",");
        return arrResult;
    }
}
