package app.forms.loginform;

//Java Custom Packages
import app.App;
import app.api.Client;
import app.forms.Form;


public class LogInForm implements Form {
    //The application API:
    private Client client;

    /**
     * Constructs a login form object.
     * @param client the application API object.
     */
    public LogInForm(Client client) {
        this.client = client;
    }

    @Override
    public int getNextFormCode() {
        return App.NO_FORM;
    }
}
