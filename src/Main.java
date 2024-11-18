import processing.core.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends PApplet {
    List<Float> duration;
    List<Float> speed;
    List<Float> distance;
    List<Integer> stepCount;
    float x;
    float y;
    int stepCompression;
    float maxDistance;



    public static void main(String[] args) {
        PApplet.main("Main");
    }

    @Override
    public void settings() {
        size(900, 900);
    }
    @Override
    public void setup() {
//        background(255);
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData("./src/All sessions");
        duration = dataLoader.getDuration();
        speed = dataLoader.getSpeed();
        distance = dataLoader.getDistance();
        stepCount = dataLoader.getStepCount();
        stepCompression = 160; // square = stepCompression steps
        x = 0;
        y = height;
        maxDistance = Collections.max(distance);

    }

    @Override
    public void draw() {
        randomSeed(50);
        background(255);
        fill(0);
        for(int i = 0; i < this.duration.size(); i++){
            for(int j = 0; j < stepCount.get(i)/stepCompression; j++) {
                square(x, y, 5);

                y -= 10 + (1/random(100));
            }
            y = height;
            for(int j = 0; j<stepCount.get(i)/stepCompression; j++) {
                square(x+5+(1/random(100)), y+1, 5);
                y -= 10 + (1/random(100));
            }
            rect(x, height, 10, height);
            x=  (x+10) % width;
            y = height;
        }
    }

    public static int calculateDistanceRectAndOpacity() {
        return 0;
    }
}