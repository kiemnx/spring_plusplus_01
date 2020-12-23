package vn.plusplus.spring.springbootdemo.threading;

public class Activity16 {
    public static void main(String[] args) {
        int numThread = 2;
        Number number = new Number();
        System.out.println("Start: " + System.currentTimeMillis());
        for(int i=0; i<numThread; i++){
            MyThread myThread = new MyThread(number);
            Thread thread = new Thread(myThread);
            thread.setName("Thread-" + (i+1));
            thread.start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Max: " + number.getSoCoUocMax() + ", so uoc: " + number.getSoUocMax());
    }
}

class MyThread implements Runnable{

    Number number;

    public MyThread(Number number) {
        this.number = number;
    }

    @Override
    public void run() {
        while (true){
            Boolean next = number.calculate();
            if(next == false){
                Thread.currentThread().stop();
            }
        }
    }
}

class Number{
    //Kiem tra tu 1 toi 1.000.000
    private Integer soUocMax=0;
    private Integer soCoUocMax=1;
    private Integer number = 1;

    public Integer getSoUocMax() {
        return soUocMax;
    }

    public void setSoUocMax(Integer soUocMax) {
        this.soUocMax = soUocMax;
    }

    public Integer getSoCoUocMax() {
        return soCoUocMax;
    }

    public void setSoCoUocMax(Integer soCoUocMax) {
        this.soCoUocMax = soCoUocMax;
    }

    //1. Duyet tung so
    //2. Voi moi so tim so luong uoc => x
    //3. So sanh x voi bien soUocMax xem x co lon hon soUocMax khong,
    // neu lon hon thi cap nhat soUocMax va soCoUocMax

    // 001: soUocMax = 3
    // 002: Thread 1 tim ra x la 5 nhay vao so sanh voi soUocMax
    // 002: Thread 2 tim ra x la 4 nhay vao so sanh voi soUocMax
    // 003: Thread 1 cap nhat soUocMax = 5
    // 003': Thread 2 cap nhat soUocMax = 4

    public boolean calculate(){
        Integer number = getNumber();
        if(number >= 100000){
            return false;
        }
        Integer soUoc = timSoUoc(number);
        updateResult(soUoc, number);
        return true;
    }

    private synchronized Integer getNumber(){
        number = number +1;
        return number;
    }

    private Integer timSoUoc(Integer number){
        int counter = 0;
        for (int i = 1 ; i <= number ; i++) {
            if (number % i == 0) {
                counter++;
            }
        }
        return counter;
    }

    private synchronized void updateResult(Integer soUoc, Integer number){
        if(soUoc > soUocMax){
            soUocMax = soUoc;
            soCoUocMax = number;
            if(soUoc == 128 && number==83160){
                System.out.println(Thread.currentThread().getName()+ " end: " + System.currentTimeMillis());
            }
        }
    }

}
