package dto;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HeadersTest extends TestCase {
    @Test
    public void test(){
        List<Headers> headers = new ArrayList<>();
        headers.add(new Headers("Symbol", "Date", "High(price)", "Low(price)", "closed(price)"));
    }

}