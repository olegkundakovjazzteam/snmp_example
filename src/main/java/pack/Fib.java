package pack;

/**
 * Created by JazzTeamUser on 16.11.2015.
 */
public class Fib {
    private int sequence;
    private long value;

    public Fib(final int sequence) {
        this.sequence = sequence;
        this.value = -1;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
