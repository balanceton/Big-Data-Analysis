import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        System.out.println("Hadoop is alive!");

        Configuration conf = new Configuration();

        try {
            FileSystem fs = FileSystem.get(new URI("hdfs://masterNode:9000"), conf);

        
            String localFilePath = "jobs.csv";

            Path hdfsFilePath = new Path("/data2/jobs.csv");

            try (InputStream in = Files.newInputStream(Paths.get(localFilePath))) {
                if (!fs.exists(hdfsFilePath)) {
                    fs.createNewFile(hdfsFilePath);
                }
                try (org.apache.hadoop.fs.FSDataOutputStream out = fs.create(hdfsFilePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
                System.out.println("Veri başarıyla HDFS'e kopyalandı.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
    }
}
