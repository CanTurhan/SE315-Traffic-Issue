package traficsim;

import java.util.ArrayList;
import java.util.List;

public class LaneRamps {
    List<Ramp> ramps = new ArrayList<Ramp>();

    public LaneRamps(int rNum) {
        this.ramps = new ArrayList<Ramp>(rNum);
        for (int i = 0; i < rNum; i++) {
            this.ramps.add(i, new Ramp("R" + i));

        }

    }

    public Thread[] createThreadPool() {
        final Thread[] allRamps = new Thread[this.ramps.size()];
        for (final Ramp ramp : this.ramps) {
            final Thread tj = new Thread(ramp, ramp.getRampID());
            final int idx = this.ramps.indexOf(ramp);
            allRamps[idx] = tj;
        }
        return allRamps;
    }

    public List<Ramp> getRamps() {
        return this.ramps;
    }

    public void setLane(Lane l) {
        for (final Ramp ramp : this.ramps) {
            ramp.setLane(l);
        }
    }
}