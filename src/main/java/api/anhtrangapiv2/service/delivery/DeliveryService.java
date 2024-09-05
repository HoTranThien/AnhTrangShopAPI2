package api.anhtrangapiv2.service.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dto.DeliveryDTO;
import api.anhtrangapiv2.models.Delivery;
import api.anhtrangapiv2.repositories.DeliveryRepository;
import api.anhtrangapiv2.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService implements IDeliveryService{
    @Autowired
    private final DeliveryRepository deliveryRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Override
    public Delivery createDelivery(DeliveryDTO d) {
        if(deliveryRepository.existsByName(d.getName())){
            throw new RuntimeException("The delivery's name already exists");
        }
        else{
            Delivery newDelivery = Delivery.builder()
            .name(d.getName())
            .cost(d.getCost())
            .build();
            deliveryRepository.save(newDelivery);
            return newDelivery;
        }
    }

    @Override
    public Delivery deletDelivery(int id) {
        Delivery existingDelivery = getDeliveryById(id);
        if(orderRepository.existsByDeliveryId(id)){
            throw new RuntimeException("Cannot delete this size because of associated products");
        }
        else{
            deliveryRepository.delete(existingDelivery);
            return null;
        }
    }

    @Override
    public List<Delivery> getAllDelivery() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery getDeliveryById(int id) {
        Delivery existingDelivery = deliveryRepository.findById(id).orElseThrow(()->
        new RuntimeException("Delivery not found with id: " + id));
        return existingDelivery;
    }

    @Override
    public Delivery updateDelivery(int id, DeliveryDTO d) {
        Delivery existingdDelivery = getDeliveryById(id);
        if(deliveryRepository.existsByName(d.getName())&&(!existingdDelivery.getName().equals(d.getName()))){
            throw new RuntimeException("The delivery's name already exists");
        }
        else{
            existingdDelivery.setName(d.getName());
            existingdDelivery.setCost(d.getCost());
            return deliveryRepository.save(existingdDelivery);
        }

    }

}
