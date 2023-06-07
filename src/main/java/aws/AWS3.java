package aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AWS3 {






    public AmazonS3 getS3Client() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("eu-north-1") // Reemplaza con tu región
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIA4UQ67ZWG67IN7QOS", "eTV7MATrMuOb2WbCKpQDefDclvRxpoxb0BBSpMmu"))) // Reemplaza con tus credenciales
                .build();
        return s3Client;
    }



}
