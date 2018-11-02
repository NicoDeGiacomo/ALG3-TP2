package ejemplo;

import org.junit.Assert;
import org.junit.Test;

public class FooTests {

    @Test
    public void doFooShouldReturnFoo(){
        Foo foo = new Foo();
        String result = foo.doFoo();
        Assert.assertEquals("Foo", result);
    }

	@Test
	public void doBarShouldReturnBar(){
        Foo foo = new Foo();
        String result = foo.doBar();
        Assert.assertEquals("Bar", result);
	}

}
