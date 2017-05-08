package com.dataloom.neuron;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FakeTests {

    private static Boolean init = false;

    @BeforeClass
    public static void initialize() {

        init = true;
    }

    @Test
    public void test() {

        Assert.assertTrue( init );
    }
}
