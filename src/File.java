import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {

    public String path;
    public String failinimi;
    public static byte[] loeBaite(String failinimi) throws IOException{
        return Files.readAllBytes(Paths.get(failinimi));
    }

    public static String loeSõnu(String failinimi) throws IOException {
        return new String(Files.readAllBytes(Paths.get(failinimi)));
    }
    public static void salvestaTulemusFaili(String checksum, String failinimi2) throws IOException{
        Files.write(Paths.get(failinimi2), checksum.getBytes());
    }
    public static boolean kasFailOnOlemas (String failinimi){
        return Files.exists(Paths.get(failinimi));///
    }
}
