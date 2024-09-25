package api.anhtrangapiv2.service.order;

import java.util.List;

import api.anhtrangapiv2.dtos.OrderDTO;
import api.anhtrangapiv2.dtos.UpdatedOrderDTO;
import api.anhtrangapiv2.models.Order;
import api.anhtrangapiv2.responses.OrderResponse;

public interface IOrderService {
    List<OrderResponse> findAllOrders();
    OrderResponse findOneOrder(int id) throws Exception;
    Order findOrderById (int id) throws Exception;
    Order creatOrder(OrderDTO order) throws Exception;
    String updateOrder (int id, UpdatedOrderDTO order) throws Exception;
    String deleteOrder(int id) throws Exception;
    String[] getOrderStatusColumnType();
}
