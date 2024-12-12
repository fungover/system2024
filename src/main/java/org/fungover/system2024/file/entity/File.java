package org.fungover.system2024.file.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Table(name = "file", schema = "system24db")
@Indexed
@Setter
@Getter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ColumnDefault("0")
    @Column(name = "owner")
    private Integer owner;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    @FullTextField(analyzer = "text_analyzer")
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "stored_filename", nullable = false)
    private String storedFilename;

//    @ColumnDefault("0")
    @Column(name = "soft_delete")
    private Boolean softDelete = false;

}
