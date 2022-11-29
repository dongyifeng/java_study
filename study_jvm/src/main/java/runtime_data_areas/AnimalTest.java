package runtime_data_areas;


class Animal {
    public void eat() {
        System.out.println("动物进食");
    }
}

interface Huntable {
    void hunt();
}

class Dog extends Animal implements Huntable {

    @Override
    public void eat() {
        System.out.println("够吃骨头");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，多管闲事");
    }
}

class Cat extends Animal implements Huntable {


    public Cat() {
        // 指定调用父类构造器
        // 表现为：早期绑定
        super();
    }

    public Cat(String name) {
        // 指定调用自己的构造器
        // 表现为：早期绑定
        this();
    }


    @Override
    public void eat() {
        System.out.println("猫吃鱼");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，天经地义");
    }
}

public class AnimalTest {
    public void showAnimal(Animal animal) {
        // 表现为：晚期绑定
        animal.eat();
    }

    public void showHunt(Huntable h) {
        // 表现为：晚期绑定
        h.hunt();
    }

    public static void main(String[] args) {

    }
}
