package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.exception.InvalidStateException;

public class CashRefresherTest {

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void test() throws InterruptedException {
//        CashRefresher.getInstance().start();
//        Thread.sleep(11_000);
//    }

}
