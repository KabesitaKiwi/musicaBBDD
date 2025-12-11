CREATE DATABASE if not exists musica;
--
use musica;
--
CREATE TABLE if NOT EXISTS cancion{
idCancion int auto_increment primary key,
titulo varchar (50) not null,
idAutor int not null,
genero varchar (50) not null,
idProductora int not null,
participantes int not null,
duracion float not null,
idioma varchar (30) not null,
valoracion int not null,
idAlbum int not null
}
--
CREATE TABLE if NOT EXISTS autor{
idAutor int auto_increment primary key,
nombreArtistico varchar (50) not null,
nombreReal varchar (50) not null,
edad int not null,
pais varchar(30) not null,
fechaPrimeraPublicacion date,
gira BOOLEAN not null;
}
--
create table if not exists productora{
idProductora int auto_increment primary key,
nombre varchar (50) not null,
localizacion varchar (50) not null,
trabajadores int not null,
fechaFundacion date,
propietario varchar (40);
}
--
create table if not exists album{
idAlbum int auto_increment primary key,
idAutor int not null,
titulo varchar (30) not null,
numeroCanciones int not null,
duracionMinutos int not null,
fechaSalida date not null,
idProductora int not null;
}
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
