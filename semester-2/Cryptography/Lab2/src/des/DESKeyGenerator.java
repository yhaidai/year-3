package des;

import java.io.File;
import java.io.FileWriter;

public class DESKeyGenerator {

    private final File file = new File("DESKey.txt");
    private final int KEY_LENGTH = 16;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public DESKeyGenerator() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            for (int i = 0 ; i < KEY_LENGTH ; i++) {
                double random = Math.random();
                int index = (int) (random * 16);
                fileWriter.append(hexArray[index]);
                fileWriter.flush();
            }
            fileWriter.close();
        } catch(Exception exp) {
            exp.printStackTrace();
        }
    }

    public String getRandomKey() {
        StringBuilder key = new StringBuilder();

        for (int i = 0 ; i < KEY_LENGTH ; i++) {
            double random = Math.random();
            int index = (int) (random * 16);
            key.append(hexArray[index]);
        }

        return String.valueOf(key);
    }

    public static void main(String[] args) {
        new DESKeyGenerator();
    }
}