import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitTesting {
    public static String testLogin(String username, String password) throws Exception {
        FileReader fr = new FileReader("src\\Data\\Login.txt");
        Scanner reader = new Scanner(fr);
        while(reader.hasNextLine()) {
            String[] parts = reader.nextLine().split("`");
            if (parts[0].contains(username) && parts[1].contains(password)) {
                if (parts[2].contains("admin")) {
                    return "admin";
                }
                if (parts[2].contains("worker")) {
                    return "worker";
                }
                if (parts[2].contains("requester")) {
                    return "requester";
                }
            }
        }
        return "null";
    }

    @Test
    void test1() throws Exception {
        assertEquals("admin",testLogin("abc","123"));
    }
    @Test
    void test2() throws Exception {
        assertEquals("worker", testLogin("qwe", "123"));
    }
    @Test
    void test3() throws Exception {
        assertEquals("requester",testLogin("zxc","123"));
    }
    @Test
    void test4() throws Exception {
        assertEquals("null",testLogin("qwerty","123"));
    }
    @Test
    void test5() throws Exception {
        assertEquals("null",testLogin("abc","1234"));
    }
}
