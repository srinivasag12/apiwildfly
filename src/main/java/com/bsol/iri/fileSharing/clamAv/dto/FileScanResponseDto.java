package com.bsol.iri.fileSharing.clamAv.dto;

/**
 * 
 * @author rupesh
 * This class is used to map the response from the file scan.
 */

public class FileScanResponseDto {

    private String fileName;
    private Boolean detected;
    private String fileType;
    private String errorMessage;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public Boolean getDetected() {
        return detected;
    }

    public void setDetected(Boolean detected) {
        this.detected = detected;
    }
}