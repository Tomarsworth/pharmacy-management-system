public class Medicine {

    private String name;            // название лекарства
    private double price;           // цена
    private int amount;             // количество лекарств
    private int shelfLife;          // срок годности

    public Medicine(String name, double price, int amount, int shelfLife){
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.shelfLife = shelfLife;
    }

    public String getName(){
        return name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }
    public int getAmount(){
        return amount;
    }
    public int getShelfLife(){
        return shelfLife;
    }
    // метод уменьшения количества
    public void reduceAmount(int reduce){
        this.amount = this.amount - reduce;
    }

    @Override
    public String toString(){
        return getName()
                + " | цена: " + getPrice()
                + " | кол-во: " + getAmount()
                + " | срок: " + getShelfLife() + " мес.";
    }
}
