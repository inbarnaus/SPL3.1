import java.io.*;
class FirstLine {
    public static void main(String[] args){
        String name = "myFile.json";
        BufferedReader file = null;
        try{
            file = new BufferedReader(new FileReader(name));
            String line = file.readLine();
            System.out.println(line);
 
        //handle FileNotFoundException 
        }catch (FileNotFoundException e) {
            System.out.println("File not found: "+ name);
 
        //handle IOException
        }catch (IOException e) {
            System.out.println("Problem: " + e);
        }finally{
            if (file != null) { 
                System.out.println("Closing the file:" + name);
                try {
                    file.close(); // May throw an exception by itself
                } catch (IOException ignore) {}
            } else { 
                System.out.println("Unable to open file:" + name);
            } 
        } 
    }
}