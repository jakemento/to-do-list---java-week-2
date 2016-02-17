import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {
  private String mDescription;
  private LocalDateTime mCreatedAt;
  private boolean mCompleted;
  private int mId;

  public Task(String description) {
    mDescription = description;
  }

  public String getDescription() {
    return mDescription;
  }

  public boolean isCompleted() {
    return mCompleted;
  }
}
