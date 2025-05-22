import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Checksum extends Algorithm {
    private final String hash;
    Checksum(String hash, String filepath) throws IOException, NoSuchAlgorithmException {
        this.hash = hash == null || hash.isEmpty() ? getChecksumString(filepath) : hash;
        this.type = identifyChecksumType(hash);
    }

    byte[] getChecksum(String filepath) throws IOException, NoSuchAlgorithmException {
        InputStream fis =  new FileInputStream(filepath);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance(type != null ? type.name() : String.valueOf(identifyChecksumType(hash)));
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();

        return complete.digest();
    }
    String getChecksumString(String filepath) throws IOException, NoSuchAlgorithmException {
        byte[] b = getChecksum(filepath);
        StringBuilder result = new StringBuilder();

        for (byte value : b) {
            result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
    boolean checkChecksum(String sum) {
        return hash.equals(sum);
    }

    static ChecksumType identifyChecksumType(String checksum) {
        if (checksum == null) return ChecksumType.SHA256; //default to sha256 cuz most common

        String trimmed = checksum.trim().toLowerCase();

        if (trimmed.matches("[a-f0-9]{40}")) {
            return ChecksumType.SHA1;
        } else if (trimmed.matches("[a-f0-9]{64}")) {
            return ChecksumType.SHA256;
        }

        return ChecksumType.SHA256;
    }

    String getHash(){
        return hash;
    }
}

