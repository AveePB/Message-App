package api;

//Java I/O (Input and Output)
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;


public class ClientHandler extends Thread {
    BufferedWriter bw;
    BufferedReader br;

    public ClientHandler(InputStream in, OutputStream out) {
        this.bw = new BufferedWriter(new OutputStreamWriter(out));
        this.br = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public void run() {
        //sth here
    }
}
