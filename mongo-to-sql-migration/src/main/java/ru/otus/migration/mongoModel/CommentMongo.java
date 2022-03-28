package ru.otus.migration.mongoModel;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "bookMongo")
@ToString(exclude = "bookMongo")
@Document(collection = "COMMENTS")
public class CommentMongo {
    @Id
    private String id;

    private String comment;

    @DBRef
    private BookMongo bookMongo;
}
