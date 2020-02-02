package com.JackHolm.DayCounter;
import org.junit.Test;
import static org.junit.Assert.*;

public class CounterTest
{
    @Test
    public void TestCount()
    {
        Counter counter = new Counter("testing", 2019, 10, 30);

        assertEquals(0, counter.Count());
    }
}
