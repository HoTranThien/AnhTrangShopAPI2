package api.anhtrangapiv2.service.order;

import java.util.List;

import api.anhtrangapiv2.dtos.OrderDTO;
import api.anhtrangapiv2.models.Order;

public interface IOrderService {
    List<Order> findAllOrders();
    Order findOrderById (int id);
    Order creatOrder(OrderDTO order);
    Order updatOrder (int id, OrderDTO order);
    String deleteOrder(int id);
}
