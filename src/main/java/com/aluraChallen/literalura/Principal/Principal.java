package com.aluraChallen.literalura.Principal;

import com.aluraChallen.literalura.Models.Autor;
import com.aluraChallen.literalura.Models.BusquedasLibro;
import com.aluraChallen.literalura.Models.Libro;
import com.aluraChallen.literalura.Models.Records.DatosLibro;
import com.aluraChallen.literalura.Repository.IAutorRepository;
import com.aluraChallen.literalura.Repository.ILibroRepository;
import com.aluraChallen.literalura.Service.ConsumoApi;
import com.aluraChallen.literalura.Service.ConvertirDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConvertirDatos convertidor = new ConvertirDatos();
    private ConsumoApi consumodeApi = new ConsumoApi();
    private static String ObjecticApi = "https://gutendex.com/books/?search=";
    private ILibroRepository bookRepository;
    private IAutorRepository autorRepository;


    //constructor
    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.bookRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    //Metodo que iniciara el menu
    public void menu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                ***************************************
                *     BIENVENIDO A LA LIBRERÍA       *
                *           ALURA LITERARIA          *
                ***************************************
                
                Elija una opción:
                
                1 - Buscar libro por título
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un año específico
                5 - Listar libros por idioma
                
                0 - Salir
                
                ***************************************
                *   Por favor, ingrese una opción    *
                ***************************************
                """;

            try {
                System.out.println(menu);
                opcion = sc.nextInt();
                sc.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("****************************************");
                System.out.println("  ¡Por favor, ingrese un número válido! ");
                System.out.println("****************************************\n");
                sc.nextLine();
                continue;
            }


            switch (opcion){
                case 0:
                    opcion = 0;
                    System.out.println("|********************************|");
                    System.out.println("|    Aplicación cerrada. Bye!    |");
                    System.out.println("|********************************|\n");
                    break;
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    ListarAutoresRegistrados();
                    break;
                case 4:
                    ListarAutoresVivosEnUnDeterminadoyear();
                    break;
                case 5:
                    ListarLibrosPorIdioma();
                    break;
                default:
                    System.out.println("|*********************|");
                    System.out.println("|  Opción Incorrecta. |");
                    System.out.println("|*********************|\n");
                    System.out.println("Intente con una nueva Opción:");
                    menu();
                    break;
            }
        }
    }

    //metodo privado que devuelve informacion de un libro
    private Libro getDatosLibro(){
        System.out.println("Ingrese el nombre del libro: ");
        var libroEscogido = sc.nextLine().toLowerCase();
        var json = consumodeApi.obtenerInfo(ObjecticApi + libroEscogido.replace(" ", "%20"));
        BusquedasLibro datos = convertidor.obtenerInfoJSON(json, BusquedasLibro.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0);
            return new Libro(primerLibro);
        }
        else {
            System.out.println("Libro escogido, no existe");
            return null;
        }
    }

    //metodo si el usuario escoge la opcion 1
    private void buscarLibroPorTitulo() {

        Libro libro = getDatosLibro();

        if (libro == null){
            System.out.println("No se ha encontrado el libro");
            return;
        }

        try{
            boolean libroExists = bookRepository.existsByTitulo(libro.getTitulo());
            if (libroExists){
                System.out.println("El libro existe en la base de datos.");
            }
            else {
                bookRepository.save(libro);
                System.out.println(libro.toString());
            }
        }
        catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persisitir el libro buscado!");
        }
        System.out.println("\nPresione ENTER para continuar___");
        sc.nextLine();

    }

    //metodo si el usuario escoge la opcion 2
    private void librosRegistrados(){
        List<Libro> librosenGeneral = bookRepository.findAll();
        if (librosenGeneral.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos");
        }
        else {
            System.out.println("Libros encontrados en la base de datos: ");
            for (Libro libro : librosenGeneral) {
                System.out.println(libro.toString());
            }
        }
        System.out.println("\nPresione ENTER para continuar___");
        sc.nextLine();
    }

    //metodo si el usuario escoge la opcion 3
    private void ListarAutoresRegistrados(){
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores en la base de datos");
        }
        else {
            System.out.println("Autores encontrados en la base de datos: ");

            Map<String, Autor> autoresMap = new HashMap<>();

            for (Autor autor : autores) {
                if (!autoresMap.containsKey(autor.getNombre())) {
                    autoresMap.put(autor.getNombre(), autor);
                }
                else {
                    Autor autorPlaton = autoresMap.get(autor.getNombre());
                    autorPlaton.getLibros().addAll(autor.getLibros());
                }
            }
            for (Autor autor : autoresMap.values()) {
                System.out.println(autor.toString());
            }
        }
        System.out.println("\nPresione ENTER para continuar___");
        sc.nextLine();
    }

    //metodo si el usuario escoge la opcion 4
    private void ListarAutoresVivosEnUnDeterminadoyear(){
        System.out.println("Ingrese el año: ");

        int year;

        while (true) {
            try {
                year = sc.nextInt();
                sc.nextLine();
                if (year < 1000 || year > 9999) {
                    System.out.println("Por favor, ingrese un año válido...");
                }
                else {
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Por favor ingrese un año valido.");
                sc.nextLine();
            }
        }

        List<Autor> autores = autorRepository.findAutoresVivosEnAnio(year);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + year);
        }
        else {
            System.out.println("Autores vivos en el año " + year + ": ");
            for (Autor autor : autores) {
                System.out.println(autor.toString());
            }
            System.out.println("\nPresione ENTER para continuar___");
            sc.nextLine();
        }
    }

    //metodo si el usuario escoge la opcion 5
    private void ListarLibrosPorIdioma() {
        //opciones
        System.out.println("Opciones de idioma:");
        System.out.println("es - español");
        System.out.println("en - inglés");
        System.out.println("fr - francés");
        System.out.println("de - alemán");
        System.out.println("it - italiano");
        System.out.println("pt - portugués");

        //escoger idioma
        System.out.println("Ingrese el idioma: ");
        var idioma = sc.nextLine();

        long cantidadLibros = bookRepository.ContarIdiomasSimilares(idioma);

        if (cantidadLibros == 0) {
            System.out.println("No se encontraron libros con este idioma");
        }
        else {
            System.out.println("Cantidad de libros en el idioma '" + idioma + "': " + cantidadLibros);

            List<Libro> books = bookRepository.findByIdioma(idioma);
            System.out.println("Libros encontrados en la base de datos: ");

            for (Libro libro : books) {
                if (libro.getIdioma().equals(idioma)){
                    System.out.println(libro.toString());
                }
            }
        }
        System.out.println("\nPresione ENTER para continuar___");
        sc.nextLine();
    }
}
