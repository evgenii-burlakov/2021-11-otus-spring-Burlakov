package ru.otus.libraryapplication.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "book")
@ToString(exclude = "book")
@Document(collection = "COMMENTS")
public class Comment {
    @Id
    private String id;

    private String comment;

    @DBRef
    private Book book;
}
