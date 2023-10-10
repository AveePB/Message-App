package app;

//Java Custom Packages
import app.api.Client;
import app.forms.Form;
import app.forms.loginform.LogInForm;
import app.forms.mainform.MainForm;
import app.forms.signupform.SignUpForm;

public class App {
    //Application const form codes:
    public static final int LOGIN_FORM = 1;
    public static final int MAIN_FORM = 2;
    public static final int SIGN_UP_FORM = 3;
    public static final int NO_FORM = 4;

    //The application forms:
    Form logInForm;
    Form mainForm;
    Form signUpForm;

    //The application's API:
    Client client;

    /**
     * Constructs an app object.
     */
    public App() {
        /*
        initializing client object.
        initializing log in form.
        initializing main form.
        initializing sign up form.
        */
    }

    /**
     * Runs an application.
     */
    public void run() {
        int openedWindow = App.LOGIN_FORM;

        while (true) {
            if (openedWindow == App.LOGIN_FORM) {
                //openedWindow = this.logInForm.getNextFormCode();
            }
            else if (openedWindow == App.MAIN_FORM) {
                //openedWindow = this.mainForm.getNextFormCode();
            }
            else if (openedWindow == App.SIGN_UP_FORM) {
                //openedWindow = this.signUpForm.getNextFormCode();
            }
            else {
                break;
            }
        }
    }
}
