package dto;


import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class StockDto {
    private String symbol;
    private BigDecimal adj_price;
//    private BigDecimal change;
//    private String currency;
//    private BigDecimal bid;


}
