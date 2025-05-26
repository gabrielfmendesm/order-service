package store.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    @PostMapping
    public ResponseEntity<OrderOut> create(@RequestBody OrderIn orderIn) {
        Order saved = orderService.create(OrderParser.to(orderIn));
        return ResponseEntity.ok(OrderParser.to(saved));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OrderOut>> findAll() {
        List<OrderOut> list = orderService.findAll().stream()
            .map(OrderParser::to)
            .toList();
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderOut> findById(@PathVariable String id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(OrderParser.to(order));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
