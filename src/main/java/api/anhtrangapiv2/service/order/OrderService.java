package api.anhtrangapiv2.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dtos.*;
import api.anhtrangapiv2.models.Delivery;
import api.anhtrangapiv2.models.ImgProduct;
import api.anhtrangapiv2.models.Order;
import api.anhtrangapiv2.models.ProductOrder;
import api.anhtrangapiv2.models.Status;
import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.repositories.DeliveryRepository;
import api.anhtrangapiv2.repositories.OrderRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import api.anhtrangapiv2.repositories.ProductOrderRepository;
import api.anhtrangapiv2.responses.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;

    private final DeliveryRepository deliveryRepository;

    private final ProductRepository productRepository;

    private final ProductOrderRepository productOrderRepository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Order creatOrder(OrderDTO order) throws Exception{
        Delivery existingDelivery = deliveryRepository.findById(order.getDeliveryId()).orElseThrow(
            () -> new RuntimeException("Delivery not found with id: " + order.getDeliveryId())
        );
        Order newOrder = Order.builder()
        .status(order.getStatus())
        .customerName(order.getCustomerName())
        .customerTel(order.getCustomerTel())
        .customerAddress(order.getCustomerAddress())
        .customerNote(order.getCustomerNote())
        .note(order.getNote())
        .total(order.getTotal())
        .delivery(existingDelivery)
        .build();
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
        }
        return newOrder;
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
            .customerName(o.getCustomerName())
            .customerTel(o.getCustomerTel())
            .customerAddress(o.getCustomerAddress())
            .customerNote(o.getCustomerNote())
            .note(o.getNote())
            .total(o.getTotal())
            .createdAt(o.getCreateAt())
            .delivery(o.getDelivery())
            .productOrder(productOrders)
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
        .customerName(existingOrder.getCustomerName())
        .customerTel(existingOrder.getCustomerTel())
        .customerAddress(existingOrder.getCustomerAddress())
        .customerNote(existingOrder.getCustomerNote())
        .note(existingOrder.getNote())
        .total(existingOrder.getTotal())
        .createdAt(existingOrder.getCreateAt())
        .delivery(existingOrder.getDelivery())
        .productOrder(productOrders)
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
