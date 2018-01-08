import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class WithoutFinally {
    public static void main(String[] args) {
        String name = "myFile.js";
        try (BufferedReader file = new BufferedReader(new FileReader(name));)
        {
            String line = file.readLine();
            System.out.println(line);
            //handle FileNotFoundException
        } catch (
                FileNotFoundException e)

        {
            System.out.println("File not found: " + name);
            //handle IOException
        } catch (
                IOException e)

        {
            System.out.println("Problem: " + e);
        }
    }
}