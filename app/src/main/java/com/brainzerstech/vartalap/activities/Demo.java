package com.brainzerstech.vartalap.activities;

    import java.util.Timer;
    import java.util.TimerTask;

    /**
     * Created by raazz on 29/03/2018.
     */

    public class Demo {
        static int time;
        public static void main(String[] args){
            Timer timer = new Timer();

            TimerTask myTask = new TimerTask() {
                @Override
                public void run() {
                    // whatever you need to do every 2 seconds
                    System.out.println(++time+" seconds passed");
                }
            };

            timer.schedule(myTask, 2000, 2000);
        }
    }
