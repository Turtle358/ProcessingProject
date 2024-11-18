import com.google.gson.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class DataLoader {
    private List<Float> duration = new ArrayList<>();
    private List<Float> speed = new ArrayList<>();
    private List<Float> distance = new ArrayList<>();
    private List<Integer> stepCount = new ArrayList<>();

    public void loadData(String dirPath) {
        Gson gson = new Gson();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath));
            for (Path entry : stream) {
                try {
                    String content = new String(Files.readAllBytes(entry));
                    JsonObject data = gson.fromJson(content, JsonObject.class);

                    // Extract stepCount
                    try {
                        int stepValue = data.getAsJsonArray("aggregate")
                                .get(2).getAsJsonObject()
                                .get("intValue").getAsInt();
                        stepCount.add(stepValue);
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        continue;
                    }

                    // Extract speed
                    try {
                        float speedValue = data.getAsJsonArray("aggregate")
                                .get(4).getAsJsonObject()
                                .get("floatValue").getAsFloat();
                        speed.add(speedValue);
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        continue;
                    }

                    // Extract duration
                    String durationStr = data.get("duration").getAsString();
                    duration.add(Float.parseFloat(durationStr.substring(0, durationStr.length() - 1)));

                    // Extract distance
                    float distanceValue = data.getAsJsonArray("aggregate")
                            .get(3).getAsJsonObject()
                            .get("floatValue").getAsFloat();
                    distance.add(distanceValue);

                } catch (IOException | JsonSyntaxException e) {
                    System.err.println("Error processing file: " + entry.getFileName() + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing directory: " + e.getMessage());
        }
    }

    public List<Float> getDuration() {
        return duration;
    }

    public List<Float> getSpeed() {
        return speed;
    }

    public List<Float> getDistance() {
        return distance;
    }

    public List<Integer> getStepCount() {
        return stepCount;
    }
}
