import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.file.attribute.BasicFileAttributes.*;
import java.nio.file.attribute.BasicFileAttributeView.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
public class Teste{
  	public static void main(String[] args) throws IOException{
  	    System.out.println(Files.exists(Paths.get("/ostrich/feathers.png"))); //false
  	    System.out.println(Files.exists(Paths.get("/ostrich")));  //false
  	    System.out.println();

  	    /*
        Let's assume that all of the files in the following examples exist within the file system and that cobra is
        a symbolic link to the snake file.
        This isSameFile () method does not compare the contents of the file. For example, two files may have
        identical content and attributes, but if they are in different locations, then this method will return false*/
        System.out.println(Files.isSameFile(Paths.get("/user/home/SeForIgualNemOlhaSeRealmenteExiste"),Paths.get("/user/home/SeForIgualNemOlhaSeRealmenteExiste"))); //true
        //System.out.println(Files.isSameFile(Paths.get("/user/home/cobra"),Paths.get("/user/home/snake"))); //true
        //System.out.println(Files.isSameFile(Paths.get("/user/tree/../monkey"), Paths.get("/user/monkey"))); //true  - the ..cancels out the tree path of the path
        //System.out.println(Files.isSameFile(Paths.get("/leaves/./giraffe.exe"),Paths.get("/leaves/giraffe.exe"))); //true  - the symbol . leaves the path unmodified
        //System.out.println(Files.isSameFile(Paths.get("/flamingo/tait.data"),Paths.get("/cardinal/tait.data"))); //false

        Files.createDirectory(Paths.get("/home/field")); //creates a new directory, ˜field˜, in the directory /home, assuming /home exists; or else an exception is thrown
        //Files.createDirectory(Paths.get("/home/field")); //java.nio.file.FileAlreadyExistsException!!
        Files.createDirectories(Paths.get("/Users/diegolucas/Desktop/java testes/field/pasture/green")); //creates  directories field/pastures/green
        Files.createDirectories(Paths.get("/Users/diegolucas/Desktop/java testes/field/pasture/green")); //ja foi criado anteriomente, nao acontece nada

        /*The copy () method throws the checked java.nio.file.NoSuchFileException,
        such as when the file or directory does not exist or cannot be read.

        */
        //Files.copy(Paths.get("java testes"), Paths.get("java testes_new")) //nao funcionou;
        //Files.copy(Paths.get("zoo.txt"), Paths.get("java testes_new/zoo_copy.txt");

        try (InputStream is= new FileInputStream("source-data.txt");
                OutputStream out= new FileOutputStream("output-data.txt")) {
                Files.copy(is, Paths.get("c:\\mammats\\wotf.txt")); //It reads the contents from the stream and writes the output to a file represented by a Path object
                Files.copy(Paths.get("c:\\fish\\ctown.xst"), out); //It reads the contents of the file and writes the output to the stream
        } catch (IOException e) {}

        /*moves or renames a file or directory within the file system
            By default, the move () method will follow links, throw an exception if the file already exists, and not perform an atomic move(cMoveNotSupportedException)*/
          //Files.move(Paths.get("zoo.log"), Paths.get("zoo-renamed.log"));
         // Files.move(Paths.get("zoo.txt"),Paths.get("field/zoo.txt"));

          /*If the target of the path is a symbol link, then the symbolic link will be deleted, not the target of the link*/
          Files.deleteIfExists(Paths.get("/home/field")); //Not throw an exception if the file or directory does not exist, but if directory assuming it is empty. If the does not exist,throw an DirectoryNotEmptyException.
          //Files.delete(Paths.get("/home/field")); //deletes the features. txt file in the vulture directory, and it throws a NoSuchFi leExcepti on if the file or directory does not exist


      //moves or renames a file or directory within the file system
        /Path path= Paths.get("zoo-renamed.log");
        Path path2= Paths.get("gorilla.txt");  //creates a new file if dont exists
            try (BufferedReader reader=Files.newBufferedReader(path, Charset.forName("US-ASCII"));
                 BufferedWriter writer=Files.newBufferedWriter(path2, Charset.forName("US-ASCII"))) {
                // Read from the stream
                String currentline = null;
                while((currentline = reader.readLine()) != null)
                    System.out.println(currentline);

                writer.write("Hello World!!!!"); //creates a new file if dont exists
            } catch (IOException e) {}

          /*Therefore, if the file is significantly large, you may encounter an OutOfMemoryError*/
         final List<String> lines= Files.readAllLines(path); //reads all of the lines of a text file and returns the results as an ordered Li st of String values
         lines.stream().forEach(System.out::println);

         /*isDirectory(),isRegularFile(),isSymboliclink(),isHidden() isReadable(),isEecutable()
           do not throw an exception if the path does not exist  */
        System.out.println(Files.isDirectory(Paths.get("/Users/diegolucas/Desktop/java testes"))); //returns true if is a directory or a symbolic link to a directory and false otherwise
        System.out.println(Files.isRegularFile(Paths.get("/Users/diegolucas/Desktop/java testes/gorilla.txt"))); //if is a regular file or alternatively a symbolic link that points to a regular file
        System.out.println(Files.isSymbolicLink(Paths.get("/Users/diegolucas/Desktop/java testes/gorilla.txt"))); //is a symbolic link, regardless of whether the file or directory it points to exists.
          System.out.println();

        System.out.println(Files.isHidden(Paths.get("zoo2.txt"))) ; //If the file is available and hidden within the file system, this method will return true.
        System.out.println();

        //based on the permission rules of the underlying file system. Do not throw exceptions if the file does not exist but instead return false
        System.out.println(Files.isReadable(Paths.get("zoo2.txt"))); //true //returns true if the file exists and its contents are readable
        System.out.println(Files.isExecutable(Paths.get("zoo2.txt"))); //false //return true if the file is marked executable within the file system
        System.out.println();

        System.out.println(Files.size(Paths.get("/zoo/c/animals.txt")));

        //The Files.size() method is defined only on files. Calling Files.size() on a directory is system dependent and undefined
        System.out.println(Files.size(Paths.get("zoo2.txt"))); //outputs the number of bytes in the file. java.nio.file.NoSuchFileException if the file doesnt exist

        //java.nio.file.NoSuchFileException
        System.out.println(Files.getLastModifiedTime(path).toMillis()); //340 reads and outputs the last-modified time
        Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));//1520778507000 //sets a last-modified date/time using the current time value
        System.out.println(Files.getLastModifiedTime(path).toMillis());

        /* Both the getOwner () and setOwner () methods can throw the checked exception IOException in case of any issues accessing or modifying the file.*/
        System.out.println(Files.getOwner(path).getName());
        UserPrincipal owner= path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("diegolucas");
        Files.setOwner(path, owner);
        System.out.println(Files.getOwner(path).getName());

        /*Views
           Is a group of related attributes for a particular file system type
           These methods can throw a checked IOException, such as when the view class type is unsupported.**/
        BasicFileAttributes data=Files.readAttributes(path, BasicFileAttributes.class);

        System.out.println("Is path a directory? "+data.isDirectory()); //true
        System.out.println("Is path a regular file? "+data.isRegularFile()); //false
        System.out.println("Is path a symbolic link? "+data.isSymbolicLink()); //false
        System.out.println("Path not a file, directory, nor symbolic link?"+ data.isOther()); //false // is used to check for paths that are not files, directories, or symbolic links, such as paths that refer to resources or devices in some file systems
        System.out.println("Size (in bytes): "+data.size()); //340
        System.out.println("Creation date/time: "+data.creationTime()); //2018-02-25T13:55:57Z
        System.out.println("Last modified date/time: "+data.lastModifiedTime()); //2018-03-10T19:35:40Z
        System.out.println("Last accessed date/time: "+data.lastAccessTime()); //2018-03-11T15:05:34Z
        System.out.println("Unique file identifier (if available): "+ data.fileKey()); //(dev=1000004,ino=2494947) //returns a file system value that represents a unique identifier for the file within the file system or null if it is not supported by the file system.

        /*Notice that although we called Files.getFileAttributeView(), we were still able to retrieve a BasicFileAttributes
        object by calling readAttributes() on the resulting view. Since there is only one update method,
        setTimes() in the BasicFileAttributeView class,we need to pass three values to the method.*/
        BasicFileAttributeView view= Files.getFileAttributeView(path,BasicFileAttributeView.class);
        BasicFileAttributes data2= view.readAttributes();
        FileTime lastModifiedTime = FileTime.fromMillis(data2.lastModifiedTime().toMillis()+10_000);
        view.setTimes(lastModifiedTime,null,null);  //pass null for anydate/time value that we do not wish to modify


             Path path = Paths.get("/Users/diegolucas/Desktop/"); //outputs the number of bytes in the file. java.nio.file.NoSuchFileException if the file doesnt exist
             ///Users/diegolucas/Desktop/java testes/field/pasture/green/Teste.java
            ///Users/diegolucas/Desktop/java testes/Teste.java

            /*The method also throws a somewhat expected IOException, as there could be a problem reading the underlying file system
            is an overloaded version of walk(Path, int) that takes a maximum directory depth integer value as the second parameter;
            A value of 0 indicates the current path record itself;
            will not traverse symbolic links by default. Worse yet, symbolic links could lead to a cycle.
            A cycle is an infinite circular dependency in which an entry in a directory is an ancestor of the directory.
            offers the FOLLOW_LINKS option as a vararg to the walk() method -FileSystemloopException if a cycle is detected*/
             Files.walk(path).filter(p ->p.toString().endsWith(".java")).forEach(System.out::println);
             System.out.println();

             long dateFilter = 14288784888881l;
             //Files.find (Path, int, Bi Predicate(T, U) -> return boolean)
             Stream<Path> stream= Files.find(path, 10, (p,a) -> p.toString().endsWith(".java") && a.lastModifiedTime().toMillis()>dateFilter);
             stream.forEach(System.out::println);

            /*Contrast this method with the Files.walk() method, which traverses all subdirectories. For the exam,
            you should be aware that Files.list () searches one level deep and is analogous to java.io.File.listFiles(),
            except that it relies on streams.*/
            Files.list(path).filter(p -> !Files.isDirectory(p)).map(p -> p.toAbsolutePath()).forEach(System.out::println);
            System.out.println();
            /* /Users/diegolucas/Desktop/.DS_Store
               /Users/diegolucas/Desktop/.localized
               /Users/diegolucas/Desktop/eCertificate.pdf
               /Users/diegolucas/Desktop/Episode 10 - The Pineapple Incident.srt
               /Users/diegolucas/Desktop/OCP_Oracle_Certified_Professional_Java_SE_8_Programmer_II_Study_Guide_Exam_1Z0-809.pdf
            */

            /*Files.lines (Path) method that returns a Stream<String> object and does not suffer from this same issue.
            The contents of the file are read and processed lazily, which means that only a small portion of the file is
            stored in memory at any given time
            if is directory:Exception in thread "main" java.io.UncheckedIOException: java.io.IOException: Is a directory*/
            Path path2 = Paths.get("/Users/diegolucas/Desktop/java testes/zoo2.txt");
            Files.lines(path2).forEach(System.out::println); //reads the lines lazily and prints them as they are being read
            Files.readAllLines(path2).forEach(System.out::println);//reads the entire file into memory
            System.out.println();
    }
}
