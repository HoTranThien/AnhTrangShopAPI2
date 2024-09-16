package api.anhtrangapiv2.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dtos.OrderDTO;
import api.anhtrangapiv2.models.Order;
import api.anhtrangapiv2.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    @Autowired
    private final OrderRepository orderRepository;

    @Override
    public Order creatOrder(OrderDTO order) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deleteOrder(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> findAllOrders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order findOrderById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order updatOrder(int id, OrderDTO order) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
