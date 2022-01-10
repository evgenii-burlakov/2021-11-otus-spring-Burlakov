INSERT INTO AUTHORS (`NAME`) VALUES ('PUSHKIN');
INSERT INTO GENRES (`NAME`) VALUES ('POEM');
INSERT INTO BOOKS (`NAME`, AUTHOR_ID, GENRE_ID) VALUES ('EVGENII ONEGIN',
                                                        SELECT ID FROM AUTHORS WHERE NAME='PUSHKIN',
                                                        SELECT ID FROM GENRES WHERE NAME='POEM');

INSERT INTO AUTHORS (`NAME`) VALUES ('MONTGOMERY');
INSERT INTO GENRES (`NAME`) VALUES ('NOVEL');
INSERT INTO BOOKS (`NAME`, AUTHOR_ID, GENRE_ID) VALUES ('ANNE OF GREEN GABLES',
                                                        SELECT ID FROM AUTHORS WHERE NAME='MONTGOMERY',
                                                        SELECT ID FROM GENRES WHERE NAME='NOVEL');


INSERT INTO BOOKS (`NAME`, AUTHOR_ID, GENRE_ID) VALUES ('ANNE OF GREEN GABLES POEM EDITION',
                                                        SELECT ID FROM AUTHORS WHERE NAME='MONTGOMERY',
                                                        SELECT ID FROM GENRES WHERE NAME='POEM');