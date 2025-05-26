package store.order;

public class OrderParser {

    public static Order to(OrderIn in) {
        return in == null ? null :
            Order.builder()
                .items(
                    in.items().stream()
                        .map(OrderItemParser::to)
                        .toList()
                )
                .build();
    }

    public static OrderOut to(Order o) {
        return o == null ? null :
            OrderOut.builder()
                .id(o.id())
                .date(o.date())
                .items(
                    o.items().stream()
                        .map(OrderItemParser::to)
                        .toList()
                )
                .total(o.total())
                .build();
    }

}
