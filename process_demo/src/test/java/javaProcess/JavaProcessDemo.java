package javaProcess;

public class JavaProcessDemo {
	public static void main(String[] args) {
		System.out.println("OUTPUT one");
		System.out.println("OUTPUT two");
		System.err.println("ERROR 1");
		System.err.println("ERROR 2");
		for (int i = 0; i < args.length; i++) {
			System.out.printf("args[%d] = %s.", i, args[i]);
		}
	}
}