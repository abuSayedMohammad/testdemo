package net.contal.demo;

import java.util.Random;

public abstract class AccountNumberUtil {


    /**
     * this function should generate random integer number and return
     * @return random integer
     */
    public static int generateAccountNumber(){
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }

}
