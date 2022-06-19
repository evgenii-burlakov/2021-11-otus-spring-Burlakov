package ru.otus.migration.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.migration.mongoModel.AuthorMongo;
import ru.otus.migration.mongoModel.BookMongo;
import ru.otus.migration.mongoModel.CommentMongo;
import ru.otus.migration.mongoModel.GenreMongo;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private AuthorMongo authorMongo1;
    private AuthorMongo authorMongo2;
    private GenreMongo genreMongo1;
    private GenreMongo genreMongo2;
    private BookMongo bookMongo;

    @ChangeSet(order = "000", id = "dropDB", author = "esburlakov", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "esburlakov", runAlways = true)
    public void initGenres(MongockTemplate template){
        genreMongo1 = template.save(new GenreMongo(null, "POEM"));
        genreMongo2 = template.save(new GenreMongo(null, "NOVEL"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "esburlakov", runAlways = true)
    public void initAuthors(MongockTemplate template){
        authorMongo1 = template.save(new AuthorMongo(null, "PUSHKIN"));
        authorMongo2 = template.save(new AuthorMongo(null, "MONTGOMERY"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "esburlakov", runAlways = true)
    public void initBooks(MongockTemplate template){
        bookMongo = template.save(new BookMongo(null, "EVGENII ONEGIN", authorMongo1, genreMongo1));
        template.save(new BookMongo(null, "ANNE OF GREEN GABLES", authorMongo2, genreMongo2));
    }

    @ChangeSet(order = "004", id = "initComments", author = "esburlakov", runAlways = true)
    public void initComments(MongockTemplate template){
        template.save(new CommentMongo(null, "Хорошая книга", bookMongo));
        template.save(new CommentMongo(null, "Огонь", bookMongo));
    }
}
