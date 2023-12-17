import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndTesting {

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

    }

    @Test
    public void openFitWSarahHomePage(){
        open("http://localhost:3000/");

        SelenideElement getAllPackages = $("div[id='serviceCard']");
        getAllPackages.shouldBe(visible);

        sleep(10000);
    }

    

}
