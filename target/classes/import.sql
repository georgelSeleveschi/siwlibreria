insert into book (id, title,isbn,categoria,  url_image) values(nextval('hibernate_sequence'), 'Harry Potter','1','fantasy', 'https://www.ibs.it/images/9788831003421_0_536_0_75.jpg');
insert into book (id, title,isbn,categoria,  url_image) values(nextval('hibernate_sequence'), 'Il signore degli anelli','2','fantasy','https://www.tolkien.it/wp-content/uploads/2017/05/Il-Signore-degli-Anelli-Vol.-3-Il-ritorno-del-Re.jpg');
insert into book (id, title,isbn,categoria,  url_image) values(nextval('hibernate_sequence'), 'Il grande Gatsby','3','romazo','https://pad.mymovies.it/filmclub/2010/11/083/locandina.jpg');

insert into author (id, nome, cognome, date_of_birth) values(nextval('hibernate_sequence'), 'Francis ', 'Scott Fitzgerald', '1896-12-21');
insert into author (id, nome, cognome, date_of_birth) values(nextval('hibernate_sequence'), 'John Ronald Reuel', 'Tolkien', '1892-01-03');
insert into author (id, nome, cognome, date_of_birth) values(nextval('hibernate_sequence'), 'Joanne ', 'Rowling', '1965-07-31');

insert into student(matricola, nome, cognome,CF, date_of_birth) values(nextval('hibernate_sequence'), 'Andrea ','a', 'Franzin', '2000-01-31');
insert into student(matricola, nome, cognome,CF, date_of_birth) values(nextval('hibernate_sequence'), 'Mario ','b', 'Rossi', '2000-01-27');