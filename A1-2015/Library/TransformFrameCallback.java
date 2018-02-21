import java.util.Optional;
import java.util.List;

public interface TransformFrameCallback {
    public Optional<Frame> transform(Frame frame, List<Frame> lastNSamples);
    //public Frame transform(Frame frame, List<Frame> lastNSamples);
}
