package app.forms.signupform;

//Java Custom Packages
import app.App;
import app.api.Client;
import app.forms.Form;


public class SignUpForm implements Form {
    //The application API:
    private Client client;

    /**
     * Constructs a signup form object.
     * @param client the application API object.
     */
    public SignUpForm(Client client) {
        this.client = client;
    }

    @Override
    public int getNextFormCode() {
        return App.NO_FORM;
    }
}
