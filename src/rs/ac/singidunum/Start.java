package rs.ac.singidunum;

public class Start {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread1 t1 = new Thread1(counter);
        Thread2 t2 = new Thread2(counter);
        t1.start();
        t2.start();
    }
}

class Counter {
    int value;
    boolean flag1 = false;  // nit 1 hoce da udje
    boolean flag2 = false; // nit 2 hoce da udje
    int turn = 1;  // ciji je red, da li nit1 ili nit2

    public void add(int value){
        this.value += value;
    }

    public int returnValue(){
        return this.value;
    }

}

class Thread1 extends Thread{

    Counter counter = null;

    public Thread1(Counter counter){
        this.counter = counter;
    }

    public void run() {

        for (int i = 0; i < 10; i++) {
            // KOD PRE KITICNE SEKCIJE
            counter.flag1 = true; // hocu da udjem
            if (counter.turn == 2) { //ako nije na njega red
                counter.flag1 = false; // slucajno cekanje
                while (counter.turn == 2) ;  //cekaj sve dok je red na drugu nit
                counter.flag1 = true; // sada ipak hocu da udjem

            }


            counter.add(i); // kriticna sekcija
            System.out.println("Nit 1 je izvrsila KS u iteraciji: " + i + " i vrednost je: " + counter.returnValue());

            // KOD POSLE KRITICNE SEKCIJE
            counter.turn = 2; // daj prednost drugoj niti
            counter.flag1 = false; // necu da udjem

        }
    }
}
class Thread2 extends Thread{

    Counter counter = null;

    public Thread2(Counter counter){
        this.counter = counter;
    }

    public void run() {

        for (int i = 0; i < 10; i++) {
            // KOD PRE KRITICNE SEKCIJE
            counter.flag2 = true; // hocu da udjem

            while (counter.flag1) { //ako je flag2 == false, idi u KS
                if (counter.turn == 1) { //ako nije na njega red
                    counter.flag2 = false; // slucajno cekanje
                    while (counter.turn == 1) ;  //cekaj sve dok je red na drugu nit
                    counter.flag2 = true; // sada ipak hocu da udjem

                }

            }
            counter.add(i); // kriticna sekcija
            System.out.println("Nit 2 je izvrsila KS u iteraciji: " + i + " i vrednost je: " + counter.returnValue());

            // KOD POSLE KRITICNE SEKCIJE
            counter.turn = 2; // daj prednost prvoj niti
            counter.flag2 = false; // necu da udjem

        }
    }
}

