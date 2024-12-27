//package org.fungover.system2024.file.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
//
//import java.util.zip.ZipEntry;
//
//@Entity
//@Table(name = "file", schema = "system24db")
//@Indexed
//@Setter
//@Getter
//public class File {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @ManyToOne
////    @JoinColumn(name = "owner", foreignKey = @ForeignKey(name = "fk_owner_id"))
//    @JoinColumn(name = "owner", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_owner_id"))
//    @ColumnDefault("0")
////    @Column(name = "owner")
//    private Integer owner;
//
//    @NotNull
//    @Size(max = 255)
//    @Column(name = "original_filename", nullable = false)
//    @FullTextField(analyzer = "text_analyzer")
//    private String originalFilename;
//
//    @Size(max = 255)
//    @NotNull
//    @Column(name = "stored_filename", nullable = false)
//    private String storedFilename;
//
////    @ColumnDefault("0")
//    @Column(name = "soft_delete")
//    private Boolean softDelete = false;
//
//
//}


package org.fungover.system2024.file.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.fungover.system2024.user.entity.User;
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

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_owner_id"))
    private User owner; // Changed from Integer to User

    @NotNull
    @Size(max = 255)
    @Column(name = "original_filename", nullable = false)
    @FullTextField(analyzer = "text_analyzer")
    private String originalFilename;

    @Size(max = 255)
    @NotNull
    @Column(name = "stored_filename", nullable = false)
    private String storedFilename;

    @ColumnDefault("0")
    @Column(name = "soft_delete")
    private Boolean softDelete = false;
}
