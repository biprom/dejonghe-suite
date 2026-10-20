package com.adverto.dejonghe.common.entities.images;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentBundle {
    @Id
    private String id;

    Boolean document;
    Boolean image;
    LocalDate date;
    List<String> idDocumentList;
    List<String> idMachine;
    String comment;
}
