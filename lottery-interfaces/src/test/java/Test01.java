import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Test01 {
    @Test
    public void test01() {
        Integer a = 10;
        Integer b = 10;
        log.info("a == b: {}", a == b);
        log.info("a.equals(b): {}", a.equals(b));
        log.info("a.compareTo(b): {}", a.compareTo(b));
    }

    @Test
    public void test02() {
        Integer a = 129;
        Integer b = 129;
        log.info("a == b: {}", a == b);
        log.info("a.equals(b): {}", a.equals(b));
        log.info("a.compareTo(b): {}", a.compareTo(b));
    }
}
