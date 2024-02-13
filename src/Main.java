import ReferensKlass.Repository;
import ReferensKlass.Varor;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Repository rp = new Repository();
        int counter = rp.getVaror().stream().map(Varor::getPris).mapToInt(integer -> integer).sum();

        System.out.println(counter);
        System.out.println("Hello world!");
    }
}