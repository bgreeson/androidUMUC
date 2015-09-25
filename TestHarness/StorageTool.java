package testharness;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StorageTool
{
    //Be sure to specify the name of your application. If the application name is {@code null} or
    //blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
    private static final String APPLICATION_NAME = "TestHarness";
    
    //Be sure to specify the name of your storage bucket.
    private static final String bucketName = "androidsoundappproject.appspot.com";
    
    //Global instance of the JSON factory
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /***** [START get_service] *****/
    private static Storage storageService;
    
    //Returns an authenticated Storage object used to make service calls to Cloud Storage.
    private static Storage getService() throws IOException, GeneralSecurityException 
    {
        if (null == storageService) 
        {
              GoogleCredential credential = GoogleCredential.getApplicationDefault();
              // Depending on the environment that provides the default credentials (e.g. Compute Engine,
              // App Engine), the credentials may require us to specify the scopes we need explicitly.
              // Check for this case, and inject the Bigquery scope if required.
              if (credential.createScopedRequired()) 
              {
                    credential = credential.createScoped(StorageScopes.all());
              }
              HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
              storageService = new Storage.Builder(httpTransport, JSON_FACTORY, credential)
                  .setApplicationName(APPLICATION_NAME).build();
        }
        return storageService;
    }
    /***** [END get_service] *****/
    
    /***** [START list_bucket] *****/
    //Fetch a list of the objects within the given bucket.
    //@param bucketName the name of the bucket to list.
    //@return a list of the contents of the specified bucket.
    public static List<StorageObject> listBucket() throws IOException, GeneralSecurityException 
    {
        Storage client = getService();
        Storage.Objects.List listRequest = client.objects().list(bucketName);

        List<StorageObject> results = new ArrayList<>();
        Objects objects;

        // Iterate through each page of results, and add them to our results list.
        do 
        {
            objects = listRequest.execute();
            // Add the items in this page of results to the list we'll return.
            results.addAll(objects.getItems());

            // Get the next page, in the next iteration of this loop.
            listRequest.setPageToken(objects.getNextPageToken());
        } 
        while (null != objects.getNextPageToken());

        return results;
    }
    /***** [END list_bucket] *****/
    
    /***** [START get_bucket] *****/
    //Fetches the metadata for the given bucket.
    //@param bucketName the name of the bucket to get metadata about.
    //@return a Bucket containing the bucket's metadata.
    public static Bucket getBucket() throws IOException, GeneralSecurityException 
    {
        Storage client = getService();

        Storage.Buckets.Get bucketRequest = client.buckets().get(bucketName);
        // Fetch the full set of the bucket's properties (e.g. include the ACLs in the response)
        bucketRequest.setProjection("full");
        return bucketRequest.execute();
    }
    /***** [END get_bucket] *****/
    
    /***** [START upload_stream] *****/
    //Uploads data to an object in a bucket.
    //@param name the name of the destination object.
    //@param contentType the MIME type of the data.
    //@param stream the data - for instance, you can use a FileInputStream to upload a file.
    //@param bucketName the name of the bucket to create the object in.
    //public static void uploadStream(String name, String contentType, InputStream stream)
    public static void uploadStream(String fileName, String filePath)
            throws IOException, GeneralSecurityException 
    {
        String contentType = "text/plain";
        InputStream stream = new BufferedInputStream(new FileInputStream(filePath));
        InputStreamContent contentStream = new InputStreamContent(contentType, stream);
        StorageObject objectMetadata = new StorageObject()
                // Set the destination object name
                .setName(fileName)
                // Set the access control list to publicly read-only
                .setAcl(Arrays.asList(new ObjectAccessControl().setEntity("allUsers").setRole("READER")));
        
        // Do the insert
        Storage client = getService();
        Storage.Objects.Insert insertRequest = client.objects().insert(bucketName, objectMetadata, contentStream);

        insertRequest.execute();
    }
    /***** [END upload_stream] *****/

    /***** [START delete_object] *****/
    //Deletes an object in a bucket.
    //@param path the path to the object to delete.
    //@param bucketName the bucket the object is contained in.
    public static void deleteObject(String path, String bucketName) throws IOException, GeneralSecurityException 
    {
        Storage client = getService();
        client.objects().delete(bucketName, path).execute();
    }
    /***** [END delete_object] *****/
}
