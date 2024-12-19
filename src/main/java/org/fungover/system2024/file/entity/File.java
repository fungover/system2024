package org.fungover.system2024.file.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
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

    @NotNull
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    @FullTextField(analyzer = "text_analyzer")
    private String name;

    @NotNull
    @Size(max = 2000)
    @Column(name = "file_url", nullable = false)
    private String fileUrl;
}