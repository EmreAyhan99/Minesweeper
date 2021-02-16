
package game.view;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoreIO
{
    public static void serializeTimeFile(String fileName, ArrayList<Score> topTenHighscore)  throws IOException
    {
        System.out.println("Game has won and now i will save gametime in file");
        ObjectOutputStream out = null;

        try
        {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(topTenHighscore);

        }
        finally
        {
            if(out != null)
            {
                out.close();
            }
        }
    }

    public static ArrayList<Score> deSerializeTimeFile(String filename) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            ArrayList<Score> timeHighScore = (ArrayList<Score>) in.readObject();
            return timeHighScore;

        }finally
        {
            if(in != null)
            {
                in.close();
            }
        }

    }

}
