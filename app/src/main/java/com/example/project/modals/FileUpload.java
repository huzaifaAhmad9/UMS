package com.example.project.modals;

public class FileUpload {

    private String fileName;
    private String fileUrl;

    // Required empty constructor for Firebase
    public FileUpload() {
    }

    public FileUpload(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

}
