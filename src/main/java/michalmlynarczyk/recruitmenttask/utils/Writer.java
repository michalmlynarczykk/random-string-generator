package michalmlynarczyk.recruitmenttask.utils;

import java.io.IOException;
import java.util.Set;

public interface Writer {
    void write(Set<String> strings, Long specificationId) throws IOException;
}
