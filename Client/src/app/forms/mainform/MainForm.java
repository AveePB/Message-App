package app.forms.mainform;

//Java Custom Packages
import app.App;
import app.api.Client;
import app.forms.Form;


public class MainForm implements Form {
    //The application API:
    private Client client;

    /**
     * Constructs a main form object.
     * @param client the application API object.
     */
    public MainForm(Client client) {
        this.client = client;
    }

    @Override
    public int getNextFormCode() {
        return App.NO_FORM;
    }
}
