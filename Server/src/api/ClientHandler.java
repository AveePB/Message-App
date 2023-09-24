package api;

//Java I/O (Input and Output)
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

//Java Custom Packages
import log.Logger;


public class ClientHandler extends Thread {
    private BufferedReader br;
    private PrintWriter pw;
    private Logger log;

    public ClientHandler(InputStream in, OutputStream out, Logger logger) {
        this.br = new BufferedReader(new InputStreamReader(in));
        this.pw = new PrintWriter(out);
        this.log = logger;
    }

    @Override
    public void run() {
        //handle client!!!
    }
}
