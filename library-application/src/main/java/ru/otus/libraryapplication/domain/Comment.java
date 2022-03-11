package ru.otus.libraryapplication.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "COMMENTS")
public class Comment {
    @Id
    private String id;

    private String comment;

    private String bookId;
}
