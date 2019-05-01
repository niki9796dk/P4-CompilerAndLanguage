package CodeGeneration.Execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class Parallel {

    // Fields:
    private static Parallel runner = new Parallel();
    private final int maxCallables; //How many callables can exist.
    private ThreadPoolExecutor threadPoolExecutor;

    // Constant:
    public final static int MINIMUM_MATRIX_ENTRIES_FOR_PARALLEL = 100;

    private Parallel() {
        this.maxCallables = Runtime.getRuntime().availableProcessors() * 2; //The number the of Java Execution Engines capable of running code
        this.threadPoolExecutor = new ThreadPoolExecutorSimple(this.maxCallables);
    }

    public static void forIteration(final int start, final int end, final boolean parallelCondition, final IterationInstructions code) {
        Parallel.runner.iteration(start, end, parallelCondition, code);
    }

    public static void forIteration(final int start, final int end, final IterationInstructions code) {
        Parallel.runner.iteration(start, end, code);
    }

    public static void forIteration(final int end, final IterationInstructions code) {
        Parallel.runner.iteration(0, end, code);
    }

    public static void forIteration(final int end, final boolean parallelCondition, final IterationInstructions code) {
        Parallel.runner.iteration(0, end, parallelCondition, code);
    }

    private void iteration(final int start, final int end, boolean parallelCondition, final IterationInstructions code) {
        if (parallelCondition) {
            this.iteration(start, end, code);
        } else {
            for (int j = start; j < end; j++) {
                code.step(j);
            }
        }
    }

    private void iteration(final int start, final int end, final IterationInstructions code) {
        final int maxSteps = (end - start);

        final int iterationsPerCallable = (int) Math.ceil(((maxSteps) / (double) this.maxCallables));

        List<Callable<Void>> callables = new ArrayList<>(this.maxCallables);

        for (int i = 0; i < this.maxCallables; i++) {

            //Starting value for current callable. Starts where last portion stops.
            final int startVal = start + i * iterationsPerCallable;

            //If the starting value is above the end value, do not make a new task!
            if (startVal >= end)
                break;

            //Final value for current callable. Does not allow going over the total end limit.
            final int endVal = Math.min(startVal + iterationsPerCallable, end);

            callables.add(
                    () -> {
                        for (int j = startVal; j < endVal; j++) {
                            code.step(j);
                        }
                        return null;
                    });
        }

        this.run(callables);
    }

    private void run(final List<Callable<Void>> callables) {
        try {
            this.threadPoolExecutor.invokeAll(callables).forEach(f -> {
                try {
                    f.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Remember to not use this on any dynamic list!");
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Remember to not use this on any dynamic list!");
        }
    }

}
