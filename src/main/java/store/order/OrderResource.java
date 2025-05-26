package store.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import store.account.AccountOut;

@RestController
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<OrderOut> createOrder(String idAccount, OrderIn orderIn) {
        Order created = orderService.create(
            OrderParser.to(orderIn)
                .account(AccountOut.builder().id(idAccount).build())
        );
        return ResponseEntity.ok().body(OrderParser.to(created));
    }

    @Override
    public ResponseEntity<List<OrderOut>> getAllOrders(String idAccount) {
        return ResponseEntity
            .ok()
            .body(orderService.findAll(idAccount).stream().map(OrderParser::to).toList());
    }

    @Override
    public ResponseEntity<OrderOut> getOrderById(String idAccount, String id) {
        Order order = orderService.findById(idAccount, id);
        return ResponseEntity.ok().body(OrderParser.to(order));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(String idAccount, String id) {
        orderService.deleteOrder(id);
    }

}
