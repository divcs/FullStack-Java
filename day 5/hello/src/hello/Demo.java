package hello;
@FunctionalInterface
interface MyFI{
	void sayHello();
}
public class Demo{
public static void main(String []args) {
	MyFI fi = () -> System.out.println("Hi there");
	fi.sayHello();
	}
}
