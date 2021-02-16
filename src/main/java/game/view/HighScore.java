package game.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

class Score implements Comparable<Score>, Serializable {
    int score;
    String name;
    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Score o) {
        return this.score - o.score;
    }
}

public class HighScore
{
    private ArrayList<Score> highScore;
    private HighScoreIO highScoreIO = new HighScoreIO();

    public HighScore()
    {
        this.highScore = new ArrayList<>(10);
    }
    public void playerNameAndTime(String name, int time)
    {
        highScore.add(new Score(name, time));
    }

    public void readDataToFile(String fileName) throws IOException {
        highScoreIO.serializeTimeFile(fileName,highScore);
        Collections.sort(highScore);
    }

    public void readDataFromFile(String fileName) throws IOException, ClassNotFoundException {
        highScore.addAll(highScoreIO.deSerializeTimeFile(fileName));
        Collections.sort(highScore);
    }

    @Override
    public String toString() {

        String s = " ";

        Collections.sort(highScore);

        for (int i = 0; i < highScore.size(); i++)
        {
            s = s + highScore.get(i).name + "    " + highScore.get(i).score + "\n";

        }
        return s;

    }
}
