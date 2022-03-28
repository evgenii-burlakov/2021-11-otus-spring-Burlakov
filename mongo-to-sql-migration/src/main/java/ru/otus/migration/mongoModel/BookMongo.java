package ru.otus.migration.mongoModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "BOOKS")
public class BookMongo {
    @Id
    private String id;

    private String name;
    @DBRef
    private AuthorMongo authorMongo;
    @DBRef
    private GenreMongo genreMongo;

    public BookMongo(String id, String name, AuthorMongo authorMongo, GenreMongo genreMongo) {
        this.id = id;
        this.name = name;
        this.authorMongo = authorMongo;
        this.genreMongo = genreMongo;
    }
}
