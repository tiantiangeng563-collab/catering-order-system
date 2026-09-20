import java.math.BigDecimal;
public class Dish {
    private int id;
    private String name;
    private BigDecimal price;
    public Dish() {
    }
    public  Dish(int id, String name, BigDecimal price){
        this.id=id;
        this.name=name;
        this.price=price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "id为" + id + ", 菜的名称为" + name + ", 价格为" + price;

    }
}

