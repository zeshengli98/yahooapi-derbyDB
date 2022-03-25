package yahoofinance;

import dto.StockDto;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class YahooStockAPI {
    public StockDto getStock(String stockName) throws IOException {
        StockDto dto = null;
        Stock stock = YahooFinance.get(stockName);
        dto=new StockDto(stock.getName(), stock.getQuote().getPrice());
        return dto;
    }

    public Map<String,Stock> getStock(String[] stockNames) throws IOException {
        Map<String,Stock> stock = YahooFinance.get(stockNames);
        return stock;
    }


    public List<HistoricalQuote> getHistory(String stockName, int year, String searchType) throws IOException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, Integer.parseInt("-"+year));

        Stock stock = YahooFinance.get(stockName);

        List<HistoricalQuote> history = stock.getHistory(from,to, getInterval(searchType));
//        for(HistoricalQuote quote : history){
//            System.out.println("================================");
//            System.out.println("symbol : "+quote.getSymbol());
//            System.out.println("date : "+converDate(quote.getDate()));
//            System.out.println("High : "+quote.getHigh());
//            System.out.println("Low : "+quote.getLow());
//            System.out.println("Closed : "+quote.getClose());
//            System.out.println("================================");
//
//        }
        return history;
    }



    private String converDate(Calendar cal){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(cal.getTime());
        return format1;
    }

    private Interval getInterval(String searchType){
        Interval interval=null;
        switch (searchType.toUpperCase()){
            case "MONTHLY":
                interval = Interval.MONTHLY;
                break;
            case "WEEKLY":
                interval = Interval.WEEKLY;
                break;
            case "DAILY":
                interval = Interval.DAILY;
                break;
        }
        return interval;
    }

    public static void main(String[] args) throws IOException {
        YahooStockAPI yahooStockAPI = new YahooStockAPI();
        System.out.println(yahooStockAPI.getStock("SPY"));

//        String[] stockNames = {"SPY","SPX","BABA","YHOO"};
//        System.out.println(yahooStockAPI.getStock(stockNames));

//        yahooStockAPI.getHistory("GOOG");

        yahooStockAPI.getHistory("SPY",1,"daily");


    }

}
