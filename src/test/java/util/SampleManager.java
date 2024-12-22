package util;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SampleManager<B> {

    private final List<B> samples;

    public SampleManager(List<B> samples) {
        this.samples = samples;
    }

    public <T extends B> void addChangedSample(
            Supplier<T> getSample,
            Consumer<T> changeSample
    ) {
        T object = getSample.get();
        changeSample.accept(object);

        samples.add(object);
    }
}
