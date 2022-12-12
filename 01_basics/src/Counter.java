public class Counter {
    int counter;
    public Counter () {
        this.counter = 0;
    }

    public void inc() {
        this.counter++;
    }

    public void dec() {
        this.counter--;
    }

    public void print() {
        System.out.println("Counter " +  this.counter);
    }
}
