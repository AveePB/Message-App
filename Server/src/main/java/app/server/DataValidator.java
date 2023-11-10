package app.server;

//Java Custom
import app.api.APIException;
import app.api.Request;
import app.api.StatusCode;

/**
 * This class is used as container
 * for the static function 'validate request'.
 */
public class DataValidator {

    /**
     * Validates client's request. It's assumed that requests
     * have only string values.
     * @param request the client request.
     * @param isUserLogged the boolean value.
     * @param keys the keys for field.
     * @param authRequired the authentication requirement.
     */
    public static void validateRequest(Request request, boolean isUserLogged, String[] keys, boolean authRequired) throws APIException {
        //1.USER AUTHENTICATION.
        if (isUserLogged != authRequired) throw new APIException(StatusCode.FORBIDDEN);

        //2. Request FORM validation.
        if (!(request.hasKey("TYPE") && request.hasKey("ACTION")))
            throw new APIException(StatusCode.BAD_REQUEST);

        //3.Request VALIDATION (preliminary).
        for (String key: keys) {
            if (request.hasKey(key) && request.isValueString(key)) continue;
            throw new APIException(StatusCode.BAD_REQUEST);
        }
    }
}
