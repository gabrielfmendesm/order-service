package store.order;

import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

    @Autowired
    private ProductController productController;

    @Autowired
    private OrderRepository orderRepository;

    public Order create(Order domain) {
        domain.date(new Date());
        domain.total(0.0);

        for (OrderItem item : domain.items()) {
            ProductOut prod = productController.findById(item.product().id()).getBody();
            item.product(prod);
            item.total(item.quantity() * prod.price());
            domain.total(domain.total() + item.total());
        }

        OrderModel model = new OrderModel(domain);
        OrderModel saved = orderRepository.save(model);
        return saved.to();
    }

    public List<Order> findAll() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
            .map(OrderModel::to)
            .toList();
    }

    public Order findById(String id) {
        OrderModel model = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return model.to();
    }

    public void delete(String id) {
        OrderModel model = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        orderRepository.delete(model);
    }

}
