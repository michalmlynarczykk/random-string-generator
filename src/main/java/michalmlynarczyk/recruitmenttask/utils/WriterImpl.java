package michalmlynarczyk.recruitmenttask.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

public class WriterImpl implements Writer {
    @Override
    public void write(Set<String> strings, Long specificationId) throws IOException {
        String pathString = String.format("src/main/resources/generatedfiles/file%d.txt", specificationId);
        Path path = Paths.get(pathString);
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);

        Iterator<String> stringsIterator = strings.iterator();
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            while (stringsIterator.hasNext()) {
                writer.append(stringsIterator.next());
                writer.append(System.lineSeparator());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
