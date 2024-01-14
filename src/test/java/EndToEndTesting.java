import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void viewProfilePage(){
        open("http://localhost:3000/");

        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("abdursiddiqui2003@gmail.com");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Abd657@190");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);


        SelenideElement profileBtn = $("a[href='/profile']");
        profileBtn.click();

        SelenideElement viewprofile = $("div[class='service-card']");
        viewprofile.shouldBe(visible);
    }


    @Test

    public void viewAllAppointmentInProfilePage(){
        open("http://localhost:3000/");

        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("2132206@champlaincollege.qc.ca");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Habs@1290");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);


        SelenideElement profileBtn = $("a[href='/profile']");
        profileBtn.click();

        SelenideElement getAllAppointments = $("div[class='appointment-card card']");
        getAllAppointments.shouldBe(visible);

    }

    @Test
    public void viewAllAccountsAdminPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("2132206@champlaincollege.qc.ca");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Habs@1290");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);


        SelenideElement adminPanelBtn = $("a[href='/adminPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/adminAccounts']");
        profileBtn.click();

        sleep(1000);

    }

    @Test
    public void viewFitnessPackageById(){
        open("http://localhost:3000/");

        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("2132206@champlaincollege.qc.ca");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Habs@1290");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement detailsButton = $$("div.service-card .book-button").get(1);
        detailsButton.click();

        sleep(1000);

    }

    @Test
    public void viewAllAppointmentsTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("2132206@champlaincollege.qc.ca");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Habs@1290");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerAppointments']");
        profileBtn.click();

        sleep(1000);

    }

    @Test
    public void updateAppointmentStatusToCancelledTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("2132206@champlaincollege.qc.ca");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Habs@1290");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerAppointments']");
        profileBtn.click();

        sleep(1000);

        SelenideElement cancelBtn = $("button[class='cancel-button']");
        cancelBtn.click();

        sleep(1000);

        Alert alert = switchTo().alert();

        alert.accept();

        sleep(1000);

    }
    @Test
    void bookNewAppointment(){
        open("http://localhost:3000/");
        SelenideElement bookBtn = $("button[class='book-button']");
        bookBtn.click();
        sleep(1000);
        SelenideElement email = $("input[id='email']");
        SelenideElement password = $("input[id='password']");
        email.shouldBe(visible);
        sleep(1000);
        email.setValue("2132206@champlaincollege.qc.ca");
        password.setValue("Habs@1290");
        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);
        sleep(1000);
        bookBtn.click();
      //  SelenideElement appointmentSection = $("section[class='appoitnmentSection']");


    }
}
