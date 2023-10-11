package app.forms;


public interface Form {
    /**
     * Returns one of the const form codes.
     * @return App.LOGIN_FORM, App.MAIN_FORM, App.SIGN_UP_FORM, App.NO_FORM.
     */
    int getNextFormCode();
}
