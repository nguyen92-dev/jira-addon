package ut.vn.vietinbank;

import org.junit.Test;
import vn.vietinbank.api.MyPluginComponent;
import vn.vietinbank.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}