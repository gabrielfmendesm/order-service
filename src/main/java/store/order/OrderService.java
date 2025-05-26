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
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order create(Order order) {
        order.date(new Date());
        order.total(0.0);

        order.items().forEach(item -> {
            ProductOut product = productController.findById(item.product().id()).getBody();
            item.product(product);
            item.total(item.quantity() * item.product().price());
            order.total(order.total() + item.total());
        });

        Order saved = orderRepository.save(new OrderModel(order)).to();

        order.items().forEach(item -> {
            item.order(saved);
            OrderItem savedItem = orderItemRepository.save(new OrderItemModel(item)).to();
            saved.items().add(savedItem);
        });

        return saved;
    }

    public List<Order> findAll(String idAccount) {
        List<Order> orders = StreamSupport
            .stream(orderRepository.findByAccountId(idAccount).spliterator(), false)
            .map(OrderModel::to)
            .toList();

        orders.forEach(order ->
            order.items(
                StreamSupport
                    .stream(orderItemRepository.findByOrderId(order.id()).spliterator(), false)
                    .map(OrderItemModel::to)
                    .toList()
            )
        );

        return orders;
    }

    public Order findById(String idAccount, String id) {
        OrderModel model = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        Order order = model.to();

        if (!order.account().id().equals(idAccount)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        order.items(
            StreamSupport
                .stream(orderItemRepository.findByOrderId(id).spliterator(), false)
                .map(OrderItemModel::to)
                .toList()
        );

        return order;
    }

    public void deleteOrder(String id) {
        OrderModel model = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        orderRepository.delete(model);
    }

}
