package ru.otus.libraryapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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
