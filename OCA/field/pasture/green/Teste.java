import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.file.attribute.BasicFileAttributes.*;
import java.nio.file.attribute.BasicFileAttributeView.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
public class Teste{
    public static void main(String[] args) throws IOException{

     Path path = Paths.get("/Users/diegolucas/Desktop/"); //outputs the number of bytes in the file. java.nio.file.NoSuchFileException if the file doesnt exist

     /*The method also throws a somewhat expected IOException, as there could be a problem reading the underlying file system
     is an overloaded version of walk(Path, int) that takes a maximum directory depth integer value as the second parameter*/
     Files.walk(path).filter(p ->p.toString().endsWith(".java")).forEach(System.out::println);

    }
}
