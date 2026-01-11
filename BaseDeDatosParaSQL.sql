CREATE DATABASE if not exists musica;
--
use musica;
--
CREATE TABLE if NOT EXISTS cancion(
idCancion int auto_increment primary key,
titulo varchar (50) not null,
idAutor int not null,
genero varchar (50) not null,
idProductora int not null,
participantes int not null,
duracion float not null,
idioma varchar (30) not null,
valoracion int not null,
idAlbum int not null,
UNIQUE (idAlbum,idAutor, titulo)
);
--
CREATE TABLE if NOT EXISTS autor(
idAutor int auto_increment primary key,
nombreArtistico varchar (50) not null  UNIQUE,
nombreReal varchar (50) not null,
edad int not null,
pais varchar(30) not null,
fechaPrimeraPublicacion date,
gira BOOLEAN not null
);
--
create table if not exists productora(
idProductora int auto_increment primary key,
nombre varchar (50) not null UNIQUE,
localizacion varchar (50) not null,
trabajadores int not null,
fechaFundacion date,
propietario varchar (40)
);
--
create table if not exists album(
idAlbum int auto_increment primary key,
idAutor int not null,
titulo varchar (30) not null,
numeroCanciones int not null,
duracionMinutos int not null,
fechaSalida date not null,
idProductora int not null,
UNIQUE (idAutor, titulo)
);
--
alter table cancion
add foreign key (idAutor) references autor(idAutor),
add foreign key (idAlbum) references album(idAlbum),
add foreign key (idProductora) references productora(idproductora);
--
alter table album
add foreign key (idAutor) references autor(idAutor),
add foreign key (idProductora) references productora(idProductora);
--
delimiter ||
create function existeAutor(f_nombreArtistico varchar(40))
returns bit
begin
    if exists (
        SELECT 1
        FROM autor
        where nombreArtistico = f_nombreArtistico
    )then
        return 1;
    end IF;

    return 0;
END; ||

DELIMITER ;
END;
--
DELIMITER ||
create function existeProductora(f_nombreProductora varchar(30))
returns bit
begin
    if exists (
        select 1
        from productora
        where nombre = f_nombreProductora
    )then
        return 1;
    end IF;

    return 0;
END; ||

DELIMITER ;
END;
--
DELIMITER ||

CREATE FUNCTION existeAlbumAutor(f_idAutor INT, f_titulo VARCHAR(30))
RETURNS BIT
BEGIN
    IF EXISTS (
        SELECT 1
        FROM album
        WHERE idAutor = f_idAutor
          AND titulo = f_titulo
    ) THEN
        RETURN 1;
    END IF;

    RETURN 0;
END; ||

DELIMITER ;
END;
--
DELIMITER ||

CREATE FUNCTION existeCancionAlbum(f_idAlbum INT, f_titulo VARCHAR(50))
RETURNS BIT
BEGIN
    IF EXISTS (
        SELECT 1
        FROM cancion
        WHERE idAlbum = f_idAlbum
          AND titulo = f_titulo
    ) THEN
        RETURN 1;
    END IF;

    RETURN 0;
END; ||

DELIMITER ;
