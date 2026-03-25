package app.model;

public class Medicine {

    private long id;
    private String name;            // название лекарства
    private double price;           // цена
    private int amount;             // количество лекарств
    private int shelfLife;          // срок годности

    // для строки из UI
    public Medicine(String name, double price, int amount, int shelfLife){
        this.id = 0;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.shelfLife = shelfLife;
    }

    // для строки из DB
    public Medicine(long id, String name, double price, int amount, int shelfLife){
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.shelfLife = shelfLife;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getName(){
        return name;
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
        return getId()
                + " | "+ getName()
                + " | цена: " + getPrice()
                + " | кол-во: " + getAmount()
                + " | срок: " + getShelfLife() + " мес.";
    }


}
