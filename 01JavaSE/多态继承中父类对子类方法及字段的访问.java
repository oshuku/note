package demo.atzhuwang.java;

public class Demo {
	public static void main(String[] args) {
		Fu f = new Zi();
		System.out.println(f.age); // 40  父类自己的字段会被获取,无法获取子类的字段
		f.show(); // show Zi 调用了子类重写父类的方法
//		f.method();  父类无法调用子类新增的方法
//		System.out.println(f.name); 父类无法调用子类新增的字段
	}	
}

class Fu {
	public int age = 40;
	
	public Fu () {};
	
	public void show () {
		System.out.println("show Fu");
	}
}

class Zi extends Fu{
	public int age = 20;
	public String name = "son";
	
	public Zi () {};
	
	@Override
	public void show() {
		System.out.println("show Zi");
	}
	
	public void method () {
		System.out.println("show method Zi");
	}
}
