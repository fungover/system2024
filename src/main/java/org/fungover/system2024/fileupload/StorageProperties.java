package org.fungover.system2024.fileupload;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "upload-dir";
}
