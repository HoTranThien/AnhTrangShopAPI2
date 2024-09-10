package api.anhtrangapiv2.service.S3Storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3StorageService {
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.region}")
    private String region;
    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) throws Exception {
        if (file.getSize()<=0){
            throw new RuntimeException("File is empty");
        }
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File myFile = convertMultiPartToFile(file);
        //s3Client.putObject(bucketName, filename, myFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, myFile)
        .withCannedAcl(com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead);
        s3Client.putObject(putObjectRequest);
        myFile.delete();
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, filename);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile =
        new File(System.currentTimeMillis() + "_" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    private String getFilename(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
    public String deleteFile(String url) {
        try {
            s3Client.deleteObject(bucketName, getFilename(url));
            return "Delete success";    
        } catch (Exception e) {
            throw new RuntimeException("Delete failed");
        }
    }
}
