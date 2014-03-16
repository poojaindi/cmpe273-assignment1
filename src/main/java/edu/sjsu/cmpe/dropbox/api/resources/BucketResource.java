package edu.sjsu.cmpe.dropbox.api.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;



import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

//import com.amazonaws.regions.Regions;
//import com.amazonaws.regions.Region;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;


import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.CacheControl;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sjsu.cmpe.dropbox.config.dropboxServiceConfiguration;

//import javax.ws.rs.core.Response.ResponseBuilder;
//import javax.ws.rs.core.Request;

//import javax.ws.rs.core.UriInfo;





//import com.sun.research.ws.wadl.Request;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

//import edu.sjsu.cmpe.dropbox.domain.BucketDetails;
import edu.sjsu.cmpe.dropbox.dto.*;

import java.util.ArrayList;



@Path("/v1/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BucketResource {
    @GET
    @Timed(name = "view-file")
       public Response getFile() {
       	//String access_key="AKIAJGHI3W5AFQQM2VJQ";
    	//String secret_key="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";
		//AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
		
    	//AmazonS3 s3Client = new AmazonS3Client(credentials);
    	
    	AmazonS3 s3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
    // 	Region usWest1 = Region.getRegion(Regions.US_WEST_1);
    // 	s3Client.setRegion(usWest1);
    	s3Client.setEndpoint("http://s3-us-west-1.amazonaws.com");
    	String bucketName = "cmpe273project";
    	ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName));
               
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                               "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println(); 	
		return null;
    	
    }
}

