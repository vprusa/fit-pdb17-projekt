DROP TABLE zona CASCADE CONSTRAINTS;
DROP TABLE vozidlo CASCADE CONSTRAINTS;
DROP TABLE parkovaci_misto CASCADE CONSTRAINTS;
DROP TABLE pobyt CASCADE CONSTRAINTS;
DROP TABLE parkovani CASCADE CONSTRAINTS;
DROP TABLE vjezd CASCADE CONSTRAINTS;
DROP TABLE vyjezd CASCADE CONSTRAINTS;

CREATE TABLE zona(
    id_zony number not null,
    nazev_zony VARCHAR(50),
    geo_zony SDO_GEOMETRY,
    CONSTRAINT pk_id_zony primary key (id_zony)
);

drop sequence zona_seq;
create sequence zona_seq start with 1 increment by 1;

CREATE TABLE vozidlo(
    spz VARCHAR(10) NOT null,
    foto ORDSYS.ORDImage,
    spz_foto ORDSYS.ORDImage,
    CONSTRAINT spz_pk primary key (spz)
);

CREATE TABLE parkovaci_misto(
    id_mista number  NOT null,
    pozn varchar(255),
    zona_id number not null,
    CONSTRAINT misto_zona_id_fk FOREIGN key (zona_id) references zona(id_zony),
    CONSTRAINT id_mista_pk primary key (id_mista)
);

drop sequence parkovaci_misto_seq;
create sequence parkovaci_misto_seq start with 1 increment by 1;

CREATE TABLE vjezd(
    id_vjezd NUMBER NOT null,
    zona_id number not null,
    CONSTRAINT vjezd_zona_id_fk FOREIGN key (zona_id) references zona(id_zony),
    CONSTRAINT pk_vjezd PRIMARY KEY(id_vjezd)
);

drop sequence vjezd_seq;
create sequence vjezd_seq start with 1 increment by 1;


CREATE TABLE vyjezd(
    id_vyjezd NUMBER NOT null,
    zona_id number not null,
    CONSTRAINT vyjezd_zona_id_fk FOREIGN key (zona_id) references zona(id_zony),
    CONSTRAINT pk_vyjezd PRIMARY KEY(id_vyjezd)
);

drop sequence vyjezd_seq;
create sequence vyjezd_seq start with 1 increment by 1;

CREATE TABLE pobyt(
    id_pobyt number  NOT null,
    vjezd date,
    vyjezd date,
    auto_id varchar(10),
    vjezd_id number,
    vyjezd_id number,    
    constraint id_pobyt_pk primary key (id_pobyt),
    CONSTRAINT auto_id_fk FOREIGN key (auto_id) references vozidlo(spz),
    CONSTRAINT vjezd_id_fk FOREIGN key (vjezd_id) references vjezd(id_vjezd),
    CONSTRAINT vyjezd_id_fk FOREIGN key (vyjezd_id) references vyjezd(id_vyjezd)
);

drop sequence pobyt_seq;
create sequence pobyt_seq start with 1 increment by 1;


CREATE TABLE parkovani(
    id_parkovani number  NOT null,
    zacatek date,
    konec date,
    pobyt_id number,
    misto_id number,
    CONSTRAINT id_parkovani_pk primary key (id_parkovani),
    CONSTRAINT pobyt_id_fk FOREIGN key (pobyt_id) references pobyt(id_pobyt),
    CONSTRAINT misto_id_fk FOREIGN key (misto_id) references parkovaci_misto(id_mista)
);

drop sequence parkovani_seq;
create sequence parkovani_seq start with 1 increment by 1;
