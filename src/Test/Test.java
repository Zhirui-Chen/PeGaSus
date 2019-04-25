package Test;

import java.io.*;

public class Test {
    public static void writeFile1() throws IOException {
        File fout = new File("data/out.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = 0; i < 10; i++) {
            bw.write("something");
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) {
        try{
            writeFile1();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
