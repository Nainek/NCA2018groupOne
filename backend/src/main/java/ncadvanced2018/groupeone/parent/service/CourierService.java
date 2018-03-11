package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;
import java.util.List;

public interface CourierService {

    List<FulfillmentOrder> findFulfillmentOrdersByCourier(Long courierId);

    FulfillmentOrder orderReceived(FulfillmentOrder fulfillment);

    FulfillmentOrder isntReceived(FulfillmentOrder fulfillment);

    FulfillmentOrder cancelExecution(FulfillmentOrder fulfillment);

    FulfillmentOrder cancelDelivering(FulfillmentOrder fulfillment);

    FulfillmentOrder orderDelivered(FulfillmentOrder fulfillment);

    FulfillmentOrder isntDelivered(FulfillmentOrder fulfillment);

}