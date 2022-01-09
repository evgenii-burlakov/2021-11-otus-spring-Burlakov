insert into AUTHORS (`NAME`) values ('Pushkin');
insert into GENRES (`NAME`) values ('poem');
insert into BOOKS (`NAME`, AUTHOR_ID, GENRE_ID) values ('Evgenii Onegin',
                                                        select id from AUTHORS where NAME='Pushkin',
                                                        select id from GENRES where NAME='poem');

insert into AUTHORS (`NAME`) values ('Montgomery');
insert into GENRES (`NAME`) values ('novel');
insert into BOOKS (`NAME`, AUTHOR_ID, GENRE_ID) values ('Anne of Green Gables',
                                                        select id from AUTHORS where NAME='Montgomery',
                                                        select id from GENRES where NAME='novel');