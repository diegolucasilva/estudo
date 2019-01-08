import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
public class Teste{
    public static void main(String[] args) throws IOException{
      Files.walk(Paths.get("..").toRealPath().getParent()).
      map(p -> p.toAbsolutePath().toString()).
      peek(System.out::println).
      filter(s -> s.endsWith(".java")).
      collect(Collectors.toList()).
      forEach(System.out::println);
    }
}
