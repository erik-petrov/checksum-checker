import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner s = new Scanner(System.in);
        System.out.println("File path: ");
        String p = s.nextLine();
        if (!Files.exists(Paths.get(p))){
            throw new FileNotFoundException();
        }
        System.out.println("Checksum to control: ");
        String c = s.nextLine();
        File f = new File(p);
        if (c.isEmpty()){
            System.out.println("Checksum: "+f.getChecksumString());
            return;
        }
        System.out.println(f.verifyChecksum(c));
    }
}