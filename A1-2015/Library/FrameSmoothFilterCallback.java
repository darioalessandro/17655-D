import java.util.Optional;
import java.util.List;

public interface FrameSmoothFilterCallback {
	public Frame smoothCurrentFrame(Frame next, Frame current, Frame previous);
}