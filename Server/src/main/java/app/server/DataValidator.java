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
     * @param currentUserId the current user id.
     * @param keys the keys for field.
     * @param authRequired the authentication requirement.
     */
    public static void validateRequest(Request request, Integer currentUserId, String[] keys, boolean authRequired) throws APIException {
        // 1.USER AUTHENTICATION.
        if (!authRequired && currentUserId != null) throw new APIException(StatusCode.FORBIDDEN);
        if (authRequired && currentUserId == null) throw new APIException(StatusCode.FORBIDDEN);

        // 2.Request VALIDATION (preliminary).
        for (String key: keys) {
            if (request.hasKey(key) && request.isValueString(key)) continue;
            throw new APIException(StatusCode.BAD_REQUEST);
        }
    }
}
