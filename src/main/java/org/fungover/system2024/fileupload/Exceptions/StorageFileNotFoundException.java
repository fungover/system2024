package org.fungover.system2024.fileupload.Exceptions;

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String msg) {
        super(msg);
    }
    public StorageFileNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
