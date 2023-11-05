package app.api;

public class Request {
    /* REQUEST STRUCTURE {
     * int TYPE,
     * int ACTION,
     * (...)
     * }
     */
    //Constants:
    //REQUEST TYPES
    public static final int DELETE = -11111;
    public static final int POST = -1111;
    public static final int PUT = -111;
    public static final int GET = -11;

    //ACTION TYPES
    public static final int LISTING_FRIENDS = -1234; //GET
    public static final int AUTHENTICATION = -1233; //GET
    public static final int READING_CHAT = -1232; //GET

    public static final int CHAT_CREATION = -1231; //PUT
    public static final int REGISTRATION = -1230; //PUT
    public static final int MSG_CREATION = -1229; //PUT

    public static final int NO_ACTION = 0;



}
