package ua.hillel.java.elementary1.arrays;

public class StatsCalculator {

    private static double[] stats(double[] data) {
        // 0 - min
        // 1 - max
        // 2 - avg
        // 3 - stg
        // 4 - median
        //Math.min()
        //Math.max()
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
            min = Math.min(min, data[i]);
            max = Math.max(max, data[i]);
        }
        double avg =
          data.length > 0 ? sum / data.length : 0;
        double st = 0;
        for (double v : data) {
            st += Math.pow(v - avg, 2);
        }
        st = data.length > 1 ?
                st / (data.length - 1) : 0;
        st = Math.sqrt(st);
        //
        return new double[] {
                min, max, avg, st
        };
    }

    public static void main(String[] args) {

    }
}
