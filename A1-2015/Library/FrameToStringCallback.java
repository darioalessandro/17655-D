import java.util.Optional;

public interface FrameToStringCallback {
    Optional<String> transform(Frame frame);
}
