import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndTesting {

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

    }

    @Test
    public void signUpNewMember(){
        open("http://localhost:3000/");

        SelenideElement signup = $("[class='signup-button']");
        signup.click();
        SelenideElement userInput = $("input[id='username']");
        SelenideElement email = $("input[id='email']");
        SelenideElement password = $("input[id='password']");
        userInput.shouldBe(visible);
        userInput.setValue("NinNon");
        email.shouldBe(visible);
        email.setValue("ninnonelamint@gmail.com");
        password.setValue("dheSAt3CVaXMY3Y");

        SelenideElement continueButton = $("button[name='action']");
        continueButton.click();

        SelenideElement acceptButton = $("button[name='action']");
        acceptButton.click();

    }

    @Test
    public void openFitWSarahHomePage(){
        open("http://localhost:3000/");

        SelenideElement getAllPackages = $("div[id='serviceCard']");
        getAllPackages.shouldBe(visible);

    }

    @Test
    public void viewAllAppointmentInProfilePage(){
        open("http://localhost:3000/");

        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("emilegirars42@gmail.com");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Fy9u4e!6f.VTW:4");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);


        SelenideElement profileBtn = $("a[href='/profile']");
        profileBtn.click();

        SelenideElement getAllAppointments = $("div[class='appointment-card card']");
        getAllAppointments.shouldBe(visible);
    }

    

}
