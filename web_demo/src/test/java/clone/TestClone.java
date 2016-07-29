package clone;

import org.junit.Test;

public class TestClone {

	@Test
	public void mainTest() throws CloneNotSupportedException {
		TestObject obj = new TestObject();
		obj.setA(1);
		obj.setB("a");
		System.out.println(String.format("print:%s, address: %s", obj.print(), obj));
		
		TestObject clone = obj.clone();
		System.out.println(String.format("print:%s, address: %s", clone.print(), clone));
	}
}

class TestObject implements Cloneable {

	private int a;
	private String b;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String print() {
		return String.format("a: %d, b: %s", a, b);
	}

	@Override
	public TestObject clone() {
		Object clone;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		
		if (clone == null) {
			return null;
		}
		
		return (TestObject)clone ;
	}

}
