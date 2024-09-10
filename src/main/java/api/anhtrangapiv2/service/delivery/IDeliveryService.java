package api.anhtrangapiv2.service.delivery;

import java.util.List;

import api.anhtrangapiv2.dtos.DeliveryDTO;
import api.anhtrangapiv2.models.Delivery;

public interface IDeliveryService {
    Delivery getDeliveryById(int id);
    List<Delivery> getAllDelivery();
    Delivery createDelivery(DeliveryDTO d);
    Delivery updateDelivery(int id, DeliveryDTO d);
    Delivery deletDelivery(int id);
}
