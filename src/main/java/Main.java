import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Dish> dishes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String choice = sc.next();
            if (choice.equals("exit")) {
                break;
            }
            else if(choice.equals("list")){
                for (int i = 0; i < dishes.size(); i++) {
                    System.out.println(dishes.get(i));
                }
            }
            else if(choice.equals("add")){
                int id = sc.nextInt();
                String name = sc.next();
                BigDecimal price = sc.nextBigDecimal();
                
                // 检查是否有重复 ID
                boolean exists = false;
                for (int i = 0; i < dishes.size(); i++) {
                    Dish dish = dishes.get(i);
                    if (dish.getId() == id) {
                        exists = true;
                        break; // 找到重复立即退出
                    }
                }
                            
                if (exists) {
                    System.out.println("ID 已存在，添加失败");
                } else {
                    Dish newDish = new Dish(id, name, price);
                    dishes.add(newDish);
                    System.out.println("添加成功");
                }
            }
            else if(choice.equals("get")){
                int id2 = sc.nextInt();
                boolean found = false;
                for (int i = 0; i < dishes.size(); i++) {
                    if(dishes.get(i).getId() == id2){
                        System.out.println(dishes.get(i));
                        found = true;
                        break; // 找到第一个就退出
                    }
                }
                if (!found) {
                    System.out.println("未找到 ID 为 " + id2 + " 的菜品");
                }
            }
            else if(choice.equals("delete")){
                int id3 = sc.nextInt();
                boolean removed = false;
                for (int i = dishes.size() - 1; i >= 0; i--) {
                    if(dishes.get(i).getId() == id3){
                        dishes.remove(i);
                        System.out.println("删除成功");
                        removed = true;
                        // 如果需要删除所有匹配项，继续循环；否则 break
                        break; 
                    }
                }
                if (!removed) {
                    System.out.println("未找到 ID 为 " + id3 + " 的菜品");
                }
            }




        }


    }
}
