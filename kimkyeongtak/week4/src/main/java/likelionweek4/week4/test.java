package likelionweek4.week4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class test {

    private Long id;
    private String test1;
    private String test2;
    private String test3;
    private String test4;

    @Builder
    public test(String test2, String test4) {
        this.test2 = test2;
        this.test4 = test4;
    }
}
