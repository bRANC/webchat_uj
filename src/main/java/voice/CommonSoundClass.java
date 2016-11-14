// $Id: Queue.java,v 1.1 2001/05/04 21:22:05 mito Exp $
package voice;

import java.util.*;
import sun.util.calendar.CalendarUtils;

public class CommonSoundClass {

    public Vector vec = new Vector();
    boolean lock = true;
    private byte b[];

    public CommonSoundClass() {
    }

    public int level = 0;

    public double amplification = 1.0;

    public byte[] set_mic_level_in(byte[] be) {
        long tot = 0;
        for (int i = 0; i < be.length; i++) {
            be[i] *= amplification;
            tot += Math.abs(be[i]);
        }
        tot *= 2.5;
        tot /= be.length;
        level = (int) tot;
        return be;
    }

    synchronized public Object readbyte() {
        try {
            while (vec.isEmpty()) {
                wait();
            }
        } catch (InterruptedException ie) {
            System.err.println("Error: CommonSoundClass readbyte interrupted");
        }

        if (!vec.isEmpty()) {
            b = (byte[]) vec.remove(0);
            return b;
        } else {
            byte[] b = new byte[5];
            return b;
        }

    }

    synchronized public void writebyte(Object e) {
        vec.addElement(e);

        lock = false;
        notifyAll();
    }
}
