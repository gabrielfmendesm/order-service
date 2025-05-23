package store.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderResource implements OrderController {

    @Autowired private OrderService orderService;

    @Override
    public ResponseEntity<OrderOut> create(String idAccount, OrderIn orderIn) {
        Order o = orderService.create(orderIn, idAccount);
        return ResponseEntity.ok(OrderParser.toOut(o));
    }

    @Override
    public ResponseEntity<List<OrderOut>> findAll(String idAccount) {
        List<OrderOut> list = orderService.findAll(idAccount).stream()
            .map(OrderParser::toOut)   // sem parênteses!
            .toList();
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<OrderOut> findById(String idAccount, String id) {
        Order o = orderService.findById(id, idAccount);
        return ResponseEntity.ok(OrderParser.toOut(o));
    }

    @Override
    public ResponseEntity<Void> delete(String idAccount, String id) {
        orderService.delete(id, idAccount);
        return ResponseEntity.noContent().build();
    }

}
