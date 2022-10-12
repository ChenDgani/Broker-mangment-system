import java.util.LinkedList;
import java.util.TreeSet;

public class LimitBook implements Comparable <LimitBook> {

    enum OrderType {
        BUY,
        SELL
    }
    TreeSet<LimitBook> buyList;
    TreeSet<LimitBook> sellList;

    LinkedList<LimitBook> buyLinked;
    LinkedList<LimitBook> sellLinked;


    private String stockName;
    private double limitPrice;
    private int quantity;

    public LimitBook()
    {
        buyList = new TreeSet<LimitBook>();
        sellList = new TreeSet<LimitBook>();
        buyLinked = new LinkedList<LimitBook>();
        sellLinked = new LinkedList<LimitBook>();

}

    public LimitBook(String stockName, OrderType orderType, double limitPrice, int quantity) {

        this.stockName=stockName;
        this.limitPrice=limitPrice;
        this.quantity=quantity;
        orderType=OrderType.SELL;


    }

    public String getStockName() {
        return stockName;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addOrder(String stockName, OrderType orderType, double limitPrice, int quantity) {

        LimitBook book =  new LimitBook(stockName,orderType,limitPrice,quantity);
        if(orderType== OrderType.BUY)
        {
            buyList.add(book);
            addToLinkedSell(book);
            TryToBuy(book);
        }

        if(orderType== OrderType.SELL)
        {
            sellList.add(book);
            addToLinkedSell(book);
            TryToSell(book);
        }



    }

    public void TryToSell(LimitBook book)
    {

        while (sellList != null & buyList.last().getLimitPrice()>= book.getLimitPrice() ) {

                    if (buyList.last().getQuantity() >= book.getQuantity()) {
                        buyList.last().setQuantity(buyList.last().getQuantity() - book.getQuantity());
                        sellList.remove(book);
                        sellLinked.remove(book);
                    }
                    else {
                        book.setQuantity(book.getQuantity()-buyList.last().getQuantity());
                        buyList.remove(buyList.last());
                        buyLinked.remove(buyList.last());
                    }


        }

    }

    public void TryToBuy(LimitBook book)
    {
       while (sellList!= null && book.limitPrice >= sellList.first().getLimitPrice())
       {
           if (book.getQuantity() >= sellList.first().getQuantity())
           {
               int newQu = (book.getQuantity() - sellList.first().getQuantity());
               book.setQuantity(newQu);
               sellList.remove(sellList.first());
               sellLinked.remove(sellList.first());
           }
           if (book.getQuantity()==0)
               buyList.remove(book);
               buyLinked.remove(book);

       }


    }

    public void addToLinkedBuy(LimitBook book)
    {

        while (buyLinked != null)
        {
            {
                for (LimitBook book1 : buyLinked)
                {
                    if (book.getLimitPrice() >= book1.getLimitPrice()) {
                        buyLinked.add(book);
                        break;
                    }
                }

            }
        }
    }

    public void addToLinkedSell(LimitBook book)
    {
        while (sellLinked != null)
        {
            {
                for (LimitBook book1 : sellLinked)
                {
                    if (book.getLimitPrice() <= book1.getLimitPrice()) {
                        sellLinked.add(book);
                        break;
                    }
                }

            }
        }
    }




    public String printBuySet()
    {
        String str = "BUY ORDERS" + "\n";
        for (LimitBook book1 : buyLinked)
        {
            String temp = book1.getStockName() + book1.getQuantity() + book1.getLimitPrice() + "\n";
            str += temp;
        }
        return str;
    }

    public String printSellSet()
    {

        String str = "SELL ORDERS"+ "\n";
        for (LimitBook book1 : sellLinked)
        {
            String temp = book1.getStockName() + book1.getQuantity() + book1.getLimitPrice() + "\n";
            str += temp;
        }
        return str;
    }

    public int compareTo(LimitBook o) {
        if (this.getLimitPrice() > o.getLimitPrice())
            return 1;
        if (this.getLimitPrice() < o.getLimitPrice())
            return -1;
        else return 0;
    }



    public void printAll() {

        String str = printBuySet() + "\n" + printSellSet();
        System.out.println(str);

    }

    public static void main(String[] args) {
        LimitBook limitBook = new LimitBook();

        limitBook.addOrder("TSLA", OrderType.SELL, 1130.15, 60);
        limitBook.addOrder("TSLA", OrderType.SELL, 1100.50, 40);
        limitBook.printAll();

        /**
         * Should print out the following:
         BUY ORDERS
         SELL ORDERS
         TSLA 1100.5 40
         TSLA 1130.15 60
         */

        limitBook.addOrder("TSLA", OrderType.BUY, 1165.65, 70);
        limitBook.addOrder("AAPL", OrderType.BUY, 160.25, 55);
        limitBook.addOrder("AAPL", OrderType.SELL, 180.35, 10);
        limitBook.printAll();

        /**
         * Should print out the following:
         BUY ORDERS
         AAPL 160.25 55
         SELL ORDERS
         AAPL 180.35 10
         TSLA 1130.15 30
         */

    }
}