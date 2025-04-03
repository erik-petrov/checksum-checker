import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class File extends java.io.File {
    public File(String path) throws IOException, NoSuchAlgorithmException {
        super(path);
        this.checksum = new Checksum(null, path);
    }

    public boolean verifyChecksum(String sum){
        return checksum.checkChecksum(sum);
    }

    public String getChecksumString() {
        return checksum.getHash();
    }
    private final Checksum checksum;
}
