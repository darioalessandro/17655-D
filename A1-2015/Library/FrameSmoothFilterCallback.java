import java.util.Optional;
import java.util.List;

public interface FrameSmoothFilterCallback {
	public Optional<Frame> smoothCurrentFrame(Frame next, Frame current, Frame previous);
}