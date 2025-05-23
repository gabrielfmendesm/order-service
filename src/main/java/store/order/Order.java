package store.order;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder @Data @Accessors(fluent = true)
public class Order {

    private String id;
    private String accountId;
    private Date date;
    private Double total;
    private List<OrderItem> items;

}
