package com.buyern.buyern.Services;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.PublicAccessType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileService {
    BlobServiceClient blobServiceClient;
    BlobContainerClient entitiesContainerClient;

    public FileService() {
        // Create a BlobServiceClient object which will be used to create a container client
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString("AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1")
                .buildClient();
        try {
            entitiesContainerClient = blobServiceClient.createBlobContainerWithResponse("entities_templates", null, PublicAccessType.BLOB, Context.NONE).getValue();
        } catch (Exception ex){
            entitiesContainerClient = blobServiceClient.getBlobContainerClient("entities_templates");
        }
    }

    public BlobContainerClient containerClient(String containerName) {
        try {
            return blobServiceClient.createBlobContainer(containerName);
        } catch (Exception ex) {
            return blobServiceClient.getBlobContainerClient(containerName);
        }
    }

    // Get a reference to a blob
    public BlobClient blobClient(BlobContainerClient containerClient, String fileName) {
        return containerClient.getBlobClient(fileName);
    }

    public String generateFileLink(String fileName, String[] folders) {
        StringBuilder _folder = new StringBuilder();
        for (String folder : folders) {
            _folder.append("/").append(folder);
        }
        _folder.append("/").append(fileName);
        return _folder.toString();
    }

    public String generateFileLink(String fileName) {
        return "/" + fileName;
    }

    public boolean uploadFile(BlobClient blobClient, String pathToFile) throws IOException {
        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
// Upload the blob
        blobClient.uploadFromFile(pathToFile);
        return true;
    }

    public PagedIterable<BlobItem> listBlob(BlobContainerClient containerClient) {
        return containerClient.listBlobs();
    }

    public void deleteContainer(BlobContainerClient containerClient) {
        try {
            containerClient.delete();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

//    @Autowired
//    FileService fileService;
//    @Autowired
//    UserRepository userRepository;
//    @Value("${azureBlobConnString}")
//    private String azureBlobConnString;
//
//    @GetMapping("/writeBlobFile")
//    public String writeBlobFile() throws IOException {
//
////Create a unique name for the container
//        String containerName = "entities";
//        // Create a local file in the ./data/ directory for uploading and downloading
//        String localPath = "E:\\Buyern Workspace\\temp\\";
//        String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
//        File localFile = new File(localPath + fileName);
//        localFile.createNewFile();
//// Write text to the file
//        FileWriter writer = new FileWriter(localPath + fileName, true);
//        writer.write("Fuck You!");
//        writer.close();
//
//
//        BlobContainerClient containerClient = fileService.containerClient(containerName);
//        BlobClient blobClient = fileService.blobClient(containerClient,fileName);
//        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
//        fileService.uploadFile(blobClient, localPath + fileName);
//        System.out.println("\nListing blobs...");
//
//// List the blob(s) in the container.
//        for (BlobItem blobItem : containerClient.listBlobs()) {
//            System.out.println("\t" + blobItem.getName());
//        }
//
//
//        // Download the blob to a local file
//// Append the string "DOWNLOAD" before the .txt extension so that you can see both files.
//        String downloadFileName = fileName.replace(".txt", "DOWNLOAD.txt");
//        File downloadedFile = new File(localPath + downloadFileName);
//
//        System.out.println("\nDownloading blob to\n\t " + localPath + downloadFileName);
//
//        blobClient.downloadToFile(localPath + downloadFileName);
//
//
//        return "SUCCESS";
//    }