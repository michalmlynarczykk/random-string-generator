package michalmlynarczyk.recruitmenttask.task;

import lombok.SneakyThrows;
import michalmlynarczyk.recruitmenttask.entity.UserSpecification;
import michalmlynarczyk.recruitmenttask.utils.Writer;
import michalmlynarczyk.recruitmenttask.utils.WriterImpl;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomStringsTask implements Runnable {
    private final UserSpecification userSpecification;
    private final StringBuilder sb = new StringBuilder();
    private final Writer writer = new WriterImpl();

    public GenerateRandomStringsTask(UserSpecification userSpecification) {
        this.userSpecification = userSpecification;
    }

    @Override
    @SneakyThrows
    public void run() {
        Set<String> strings = generateRandomStrings();
        writer.write(strings, userSpecification.getId());
    }

    private Set<String> generateRandomStrings() {
        Set<String> strings = new HashSet<>();
        userSpecification.getCharacters().forEach(sb::append);
        String charactersToChooseFrom = sb.toString();

        while (strings.size() < userSpecification.getNumberOfStrings()) {
            int randomLength = ThreadLocalRandom.current()
                    .nextInt(userSpecification.getMinLength(), userSpecification.getMaxLength() + 1);
            strings.add(RandomStringUtils.random(randomLength, charactersToChooseFrom));
        }
        return strings;
    }
}
