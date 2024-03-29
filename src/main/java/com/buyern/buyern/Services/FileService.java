package com.buyern.buyern.Services;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.models.PublicAccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {
    @Value("${storage.container.users}")
    String usersContainerName;
    @Value("${azureBlobConnString}")
    String azureBlobConnString;
    BlobServiceClient blobServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService() {
        this.blobServiceClient = new BlobServiceClientBuilder()
//                .connectionString(azureBlobConnString)
                .connectionString("AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://194.35.120.40:10000/devstoreaccount1;QueueEndpoint=http://194.35.120.40:10001/devstoreaccount1;TableEndpoint=http://194.35.120.40:10002/devstoreaccount1")
                .buildClient();
    }

    public BlobContainerClient getEntityContainerClient() {
        try {
            return blobServiceClient.createBlobContainerWithResponse("entities", null, PublicAccessType.BLOB, Context.NONE).getValue();
        } catch (Exception ex) {
            return blobServiceClient.getBlobContainerClient("entities");
        }
    }

    public BlobContainerClient getContainerClient(String containerName) {
        //container name must be at least 3 characters long
        if (containerName.length() == 1) containerName = "00" + containerName;
        else if (containerName.length() == 2) containerName = "0" + containerName;
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public BlobContainerClient createContainerClient(String containerName) {
        //container name must be at least 3 characters long
        if (containerName.length() == 1) containerName = "00" + containerName;
        else if (containerName.length() == 2) containerName = "0" + containerName;
        try {
            return blobServiceClient.createBlobContainerWithResponse(containerName, null, PublicAccessType.CONTAINER, Context.NONE).getValue();
        } catch (Exception ex) {
            throw new RuntimeException("Can't create storage account for entity");
        }
    }

    private void deleteContainer(BlobContainerClient blobContainerClient) {
        try {
            blobContainerClient.delete();
            System.out.printf("Delete completed%n");
        } catch (BlobStorageException error) {
            if (error.getErrorCode().equals(BlobErrorCode.CONTAINER_NOT_FOUND)) {
                System.out.printf("Delete failed. Container was not found %n");
            }
        }
    }

    public String upload(BlobContainerClient containerClient, MultipartFile file, String destinationFile) {
        try {
            BlobClient blobClient = blobClient(containerClient, destinationFile);
            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("can't convert file to stream");
        } catch (Exception ex) {
            return null;
//            throw new RuntimeException("Error uploading to storage server");
        }
    }

    public String upload(BlobContainerClient containerClient, File file, String destinationFile) {
        try {
            BlobClient blobClient = blobClient(containerClient, destinationFile);
            blobClient.uploadFromFile(file.getPath(), true);
            return blobClient.getBlobUrl();
        } catch (Exception ex) {
        return null;
//            throw new RuntimeException("Error uploading to storage server");
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

    public String uploadToUsersContainer(MultipartFile file, String destination) {
        String uploadedFileUrl = upload(getContainerClient(usersContainerName), file, destination);
        if (uploadedFileUrl == null)
            return upload(createContainerClient(usersContainerName), file, destination);
        return uploadedFileUrl;
    }

    public String uploadToUsersContainer(File file, String destination) {
        String uploadedFileUrl = upload(getContainerClient(usersContainerName), file, destination);
        if (uploadedFileUrl == null)
            return upload(createContainerClient(usersContainerName), file, destination);
        return uploadedFileUrl;
    }

    public String uploadToEntityContainer(String entityId, MultipartFile file, String destination) {
        String uploadedFileUrl = upload(getContainerClient(entityId), file, destination);
        if (uploadedFileUrl == null)
            return upload(createContainerClient(entityId), file, destination);
        return uploadedFileUrl;
    }

    public String uploadToEntityContainer(String entityId, File file, String destination) {
        String uploadedFileUrl = upload(getContainerClient(entityId), file, destination);
        if (uploadedFileUrl == null)
            return upload(createContainerClient(usersContainerName), file, destination);
        return uploadedFileUrl;
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