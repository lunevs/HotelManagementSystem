package com.ichtus.hotelmanagementsystem.model.entities;

import com.ichtus.hotelmanagementsystem.model.dictionaries.ImageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.usertype.UserType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String imageTitle;

    private ImageType imageType;

    @Lob
    private byte[] imageBogy;
}
