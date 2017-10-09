package com.nlt;

import java.util.Arrays;

public class PerfMsr {

    public static long getTime(){
        return System.nanoTime();
        //long estimatedTime = System.nanoTime() - trd.time;

        //System.out.println("Ridden:"+ Arrays.toString(trd.sym)+trd.ts+" time="+estimatedTime);
    }

    public static String toSeconds(long nanos){
        return String.format("%9.3f" , (nanos/1000000000.0));
    }
}
