package store.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import store.product.ProductController;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductController prodClient;

    public Order create(OrderIn in, String accountId) {
        var domain = OrderParser.toDomain(in, accountId, prodClient);
        return orderRepository.save(new OrderModel(domain)).to();
    }

    public List<Order> findAll(String accountId) {
        return orderRepository.findByAccountId(accountId).stream()
            .map(OrderModel::to)
            .toList();
    }

    public Order findById(String id, String accountId) {
        var m = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!m.accountId().equals(accountId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return m.to();
    }

    public void delete(String id, String accountId) {
        findById(id, accountId);
        orderRepository.deleteById(id);
    }

}
