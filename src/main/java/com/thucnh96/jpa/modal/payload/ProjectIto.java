package com.thucnh96.jpa.modal.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectIto {

    @NonNull
    private String packages;
    @NonNull
    private String entityFolder;
    @NonNull
    private String repositoryFolder;
    @NonNull
    private String serviceFolder;
    @NonNull
    private String dtoFolder;
    @NonNull
    private String restFolder;
    @NonNull
    private String sepcFolder;

    private String username;

    private String password;

    private String url;

    @NonNull
    private String type;

    private String path;

    private String projectName;

    private String tablePrefix;

    private boolean genDoc;
}
