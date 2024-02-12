import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
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
    public void viewProfilePage()  {
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

        SelenideElement viewProfile = $("div[class='profile-page-container']");
        viewProfile.shouldBe(visible);


        sleep(1000);
    }


    @Test
    public void viewProfileSettings() {
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

        SelenideElement viewProfile = $("div[class='profile-page-container']");
        viewProfile.shouldBe(visible);
        SelenideElement settingsBtn = $("a[name='Setting']");
        settingsBtn.click();

        SelenideElement viewSettings = $("div[class='account-container']");
        viewSettings.shouldBe(visible);


        SelenideElement username = $("#formUsername");
        username.setValue("Abs");


        SelenideElement email = $("#formEmail");
        email.setValue("John17@gmail.com");


        SelenideElement cityInput = $("#formCity");
        cityInput.setValue("Toronto");

        SelenideElement updateBtn = $("button[class='btn btn-primary']");
        updateBtn.click();

        sleep(1000);
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

        SelenideElement getAllAppointments = $("div[class='profile-page-container']");
        getAllAppointments.shouldBe(visible);

    }

    @Test
    public void viewAllAccountsAdminPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("admin@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

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
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

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
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

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

        SelenideElement cancelBtn = $("button[class='cancelButton']");
        cancelBtn.click();

        sleep(1000);

        Alert alert = switchTo().alert();

        alert.accept();

        sleep(1000);

    }
    @Test
    void bookNewAppointmentPage(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();
        sleep(1000);
        SelenideElement email = $("input[id='username']");
        SelenideElement password = $("input[id='password']");
        email.shouldBe(visible);
        sleep(1000);
        email.setValue("2132206@champlaincollege.qc.ca");
        password.setValue("Habs@1290");
        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);
        sleep(1000);
        SelenideElement bookBtn = $("button[class='book-button']");
        bookBtn.click();
        bookBtn.click();
        SelenideElement locationDropbar = $("select[name='location']");
        locationDropbar.shouldBe(visible);
    }

    @Test
    public void updateAppointmentDetailsTrainerPanel() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");
        sleep(1000);
        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

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
        SelenideElement editButton = $("button[class='saveButton']");
        editButton.click();
        /*
        sleep(1000);
        $("select[name='status']").selectOption("CANCELLED");
         */
        sleep(1000);
        $("input[name='location']").setValue("New Location");
        $("input[name='firstName']").setValue("New FirstName");
        $("input[name='lastName']").setValue("New LastName");
        $("input[name='phoneNum']").setValue("1234567890");

        /*
        SelenideElement datePickerInput = $("input[name='date']");
        datePickerInput.click();
        sleep(1000);

        executeJavaScript("document.querySelectorAll('.MuiPickersDay-day')[15].click();");
        sleep(1000);

         */

        SelenideElement saveButton = $("button[class='saveButton']");
        saveButton.click();

        sleep(1000);
    }

    @Test
    public void viewCoachNotesInProfilePage() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("email@email.com");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        SelenideElement profileBtn = $("a[href='/profile']");
        profileBtn.click();

        SelenideElement viewProfile = $("div[class='profile-page-container']");
        viewProfile.shouldBe(visible);

        SelenideElement viewNotes = $("a[name='Notes']");
        viewNotes.click();

        SelenideElement coachNotes = $("div[class='notes-container']");
        coachNotes.shouldBe(visible);

        SelenideElement note = $("div[class='note-card']");
        note.shouldBe(visible);

        sleep(1000);
    }



    @Test
    public void viewInvoicesInProfilePage() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("Habs@1980");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Local@12");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        SelenideElement profileBtn = $("a[href='/profile']");
        profileBtn.click();

        SelenideElement viewProfile = $("div[class='profile-page-container']");
        viewProfile.shouldBe(visible);

        SelenideElement viewInvoices = $("a[name='invoices']");
        viewInvoices.click();

        SelenideElement invoices = $("div[class='invoices-container']");
        invoices.shouldBe(visible);

        SelenideElement invoices1= $("div[class='invoices-table']");
        invoices1.shouldBe(visible);

        sleep(1000);
    }




    @Test
    public void handleAppointmentRequestTrainerPanel(){
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

        SelenideElement cancelBtn = $("button[class='acceptButton']");
        cancelBtn.click();

        sleep(1000);

        Alert alert = switchTo().alert();

        alert.accept();

        sleep(1000);

    }
    @Test
    public void addFitnessPackage() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        SelenideElement switchBtn = $("label[class='switch']");
        switchBtn.click();

        SelenideElement addBtn = $("button[class='add-button']");
        addBtn.shouldBe(visible);
        addBtn.click();

        SelenideElement titleInput = $("input[name='title']");
        titleInput.setValue("Fitness Training Online");

        SelenideElement durationInput = $("input[name='duration']");
        durationInput.setValue("30");

        SelenideElement priceInput = $("input[name='price']");
        priceInput.setValue(String.valueOf(22.2));

        SelenideElement descInput = $("textarea[name='description']");
        descInput.setValue("this is a desc");

        SelenideElement otherInput = $("textarea[name='otherInformation']");
        otherInput.setValue("this is other info");

        SelenideElement addNewServiceBtn = $("button[id='newBtn']");
        addNewServiceBtn.click();
        addNewServiceBtn.shouldBe(visible);
        sleep(1000);
    }

    @Test
    public void viewAllInvoicesTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("admin@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);


        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerInvoices']");
        profileBtn.click();

        sleep(1000);

    }

    @Test
    public void addInvoiceTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("admin@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerInvoices']");
        profileBtn.click();

        sleep(1000);

        SelenideElement pileBtn = $("a[href='/trainerCreateInvoices']");
        pileBtn.click();

        sleep(1000);

        SelenideElement usernameDropdown = $("select[name='username']");
        usernameDropdown.selectOption("desired_username");

        SelenideElement statusDropdown = $("select[name='status']");
        statusDropdown.selectOption("PENDING");

        SelenideElement paymentTypeInput = $("input[name='paymentType']");
        paymentTypeInput.setValue("desired_payment_type");

        SelenideElement priceInput = $("input[name='price']");
        priceInput.setValue("123.45");

        SelenideElement createInvoiceButton = $("button[type='submit']");
        createInvoiceButton.click();

        sleep(1000);
    }

    @Test
    public void RemoveInvoicesByInvoiceIdTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("admin@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);


        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerInvoices']");
        profileBtn.click();

        sleep(1000);

        SelenideElement deleteInvoiceButton = $("button[class='button delete-button']");
        deleteInvoiceButton.click();

        sleep(1000);
    }

    @Test
    public void UpdateFitnessPackage() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        SelenideElement switchBtn = $("label[class='switch']");
        switchBtn.click();

        SelenideElement addBtn = $("button[class='edit-button']");
        addBtn.shouldBe(visible);
        addBtn.click();

        SelenideElement titleInput = $("input[name='title_EN']");
        titleInput.setValue("Fitness Training Online");

        SelenideElement titlefrInput = $("input[name='title_FR']");
        titlefrInput.setValue("Entraînement Personnel en Ligne");


        SelenideElement descInput = $("textarea[name='description_EN']");
        descInput.setValue("this is a desc");

        SelenideElement desxEng = $("textarea[name='description_FR']");
        desxEng.setValue("this is other info");
        SelenideElement otherInput = $("textarea[name='otherInformation']");
        otherInput.setValue("this is other info");

        SelenideElement priceInput = $("input[name='price']");
        priceInput.setValue(String.valueOf(22.2));
        SelenideElement updateNewServiceBtn = $("button[class='btn btn-primary']");
        updateNewServiceBtn.click();
        updateNewServiceBtn.shouldBe(visible);
        sleep(1000);
    }

    @Test
    public void viewFeedbackPage(){
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


        SelenideElement contactBtn = $("a[href='/contactMe']");
        contactBtn.click();

        sleep(1000);

        SelenideElement content = $("textarea[id='content']");
        content.shouldBe(visible);
    }

    @Test
    public void AllFeedbackPage(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);


        SelenideElement contactBtn = $("a[href='/trainerPanel']");
        contactBtn.click();

        sleep(1000);
        SelenideElement feedbackBtn = $("a[href='/trainerFeedback']");
        feedbackBtn.shouldBe(visible);
        feedbackBtn.click();


    }


    @Test
    public void viewAllCoachNotesTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerCoachNotes']");
        profileBtn.click();

        sleep(1000);

    }


    @Test
    public void addCoachNoteTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("admin@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerCoachNotes']");
        profileBtn.click();

        sleep(1000);

        SelenideElement pileBtn = $("a[href='/TrainerCreateCoachNotes']");
        pileBtn.click();

        sleep(1000);

        SelenideElement usernameDropdown = $("select[name='username']");
        usernameDropdown.selectOption("desired_username");

        SelenideElement paymentTypeInput = $("input[name='content_EN']");
        paymentTypeInput.setValue("content");

        SelenideElement paymentTypeInpute = $("input[name='content_FR']");
        paymentTypeInput.setValue("contenu");

        SelenideElement createInvoiceButton = $("button[type='submit']");
        createInvoiceButton.click();

        sleep(1000);
    }

    @Test
    public void updateCoachNoteByIdTrainerPanel(){
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);

        SelenideElement adminPanelBtn = $("a[href='/trainerPanel']");
        adminPanelBtn.click();

        sleep(1000);

        SelenideElement profileBtn = $("a[href='/trainerCoachNotes']");
        profileBtn.click();

        sleep(1000);

        SelenideElement editBtn = $("button[class='blueButton']");
        editBtn.click();

        SelenideElement saveBtn = $("button[class='blueButton']");
        saveBtn.click();

        sleep(1000);

    }

    @Test
    public void FilterFeedback() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);


        SelenideElement contactBtn = $("a[href='/trainerPanel']");
        contactBtn.click();

        sleep(1000);
        SelenideElement feedbackBtn = $("a[href='/trainerFeedback']");
        feedbackBtn.shouldBe(visible);
        feedbackBtn.click();

        SelenideElement filterBtn = $("button[class='filter-popup-button']");
        filterBtn.shouldBe(visible);
    }
    @Test
    public void AllAvailabilities() {
        open("http://localhost:3000/");
        SelenideElement loginBtn = $("button[class='login-button']");
        loginBtn.click();

        sleep(1000);
        SelenideElement emailInput = $("input[name='username']");
        emailInput.setValue("pt@admin.com");

        sleep(1000);

        SelenideElement passwordInput = $("input[name='password']");
        passwordInput.setValue("Password1");

        sleep(1000);

        SelenideElement continueButton = $("button[name='action']");
        executeJavaScript("arguments[0].click();", continueButton);

        sleep(1000);


        SelenideElement trainerBtn = $("a[href='/trainerPanel']");
        trainerBtn.click();

        sleep(1000);
        SelenideElement availabilitiesBtn = $("a[href='/availabilities']");
        availabilitiesBtn.shouldBe(visible);
        availabilitiesBtn.click();

    }
}
