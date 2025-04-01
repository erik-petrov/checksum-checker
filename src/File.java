import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    public static byte[] loebaite(String failinimi) throws IOException{
        return Files.readAllBytes(Paths.get(failinimi));
    }

    public static String loesõnu(String failinimi) throws IOException {
        return new String(Files.readAllBytes(Paths.get(failinimi)));
    }
    public static void salvestatulemusfaili(String checksum, String failinimi2) throws IOException{
        Files.write(Paths.get(failinimi2), checksum.getBytes());
    }
    public static boolean kasfailonolemas (String failinimi){
        return Files.exists(Paths.get(failinimi));
    }
}
