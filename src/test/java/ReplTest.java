import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevinzhong on 25/10/15.
 */

public class ReplTest {

    @Test
    public void TestStack() throws Exception {
        ReplStack<String> first_stack = new ReplStack<String>();
        assertEquals("Stack must be null", null, first_stack.top());
        assertEquals(null, first_stack.pop());
        first_stack.push("nabe");
        Thread.sleep(1000);
        assertEquals("nabe", first_stack.top());
        ReplStack<String> second_stack = new ReplStack<String>();
        assertEquals("nabe", second_stack.top());
        second_stack.push("urumi");
        Thread.sleep(1000);
        assertEquals("urumi", first_stack.top());
        Thread.sleep(1000);
        assertEquals("urumi", second_stack.pop());
        Thread.sleep(1000);
        assertEquals("nabe", second_stack.pop());
        Thread.sleep(1000);
        assertEquals(null, first_stack.pop());
    }

    @Test
    public void TestSet() throws Exception {
        ReplSet<String> first_set = new ReplSet<String>();
        assertEquals(true, first_set.add("albedo"));
        Thread.sleep(1000);
        assertEquals(false, first_set.add("albedo"));
        ReplSet<String> second_set = new ReplSet<String>();
        assertEquals(true, second_set.contains("albedo"));
        assertEquals(false, second_set.contains("albedos"));
        assertEquals(true, second_set.add("shalltear"));
        assertEquals(false, second_set.remove("albedoes"));
        Thread.sleep(1000);
        assertEquals(true, second_set.remove("albedo"));
        assertEquals(true, first_set.remove("shalltear"));
        Thread.sleep(1000);
        assertEquals(false, first_set.remove("shalltear"));
    }


}
