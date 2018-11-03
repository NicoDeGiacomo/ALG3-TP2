package ejemplo;

import org.junit.Assert;
import org.junit.Test;

public class AlgoEmpiresTests {

    @Test
    public void doFooShouldReturnFoo(){
        AlgoEmpires algoEmpires = new AlgoEmpires();

        Assert.assertEquals("AlgoEmpires", algoEmpires.doFoo());
    }

}
