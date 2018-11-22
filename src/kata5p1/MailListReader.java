package kata5p1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MailListReader {
    private final String fileName;

    public MailListReader(String fileName) {
        this.fileName = fileName;
    }
    
    public List<String> read(){
        List<String> list = new LinkedList<>();
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(email->{
                if (pattern.matcher(email).matches()){
                    list.add(email);
                }
            });
}       catch (IOException ex) {
            Logger.getLogger(MailListReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

}
