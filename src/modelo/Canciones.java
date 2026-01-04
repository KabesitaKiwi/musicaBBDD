package modelo;

public class Canciones {
    int idCancion;
    String titulo;
    int album;
    int autor;
    String genero;
    int productora;
    int numeroParticipantes;
    float duracion;
    String idioma;
    int valoracion;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getProductora() {
        return productora;
    }

    public void setProductora(int productora) {
        this.productora = productora;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public Canciones(String titulo, int album, int autor, String genero, int productora, int numeroParticipantes, float duracion, String idioma, int valoracion) {
        this.idCancion=0;
        this.titulo = titulo;
        this.album = album;
        this.autor = autor;
        this.genero = genero;
        this.productora = productora;
        this.numeroParticipantes = numeroParticipantes;
        this.duracion = duracion;
        this.idioma = idioma;
        this.valoracion = valoracion;
    }

    public Canciones(int idCancion, String titulo, int album, int autor, String genero, int productora, int numeroParticipantes, float duracion, String idioma, int valoracion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.album = album;
        this.autor = autor;
        this.genero = genero;
        this.productora = productora;
        this.numeroParticipantes = numeroParticipantes;
        this.duracion = duracion;
        this.idioma = idioma;
        this.valoracion = valoracion;
    }
}
