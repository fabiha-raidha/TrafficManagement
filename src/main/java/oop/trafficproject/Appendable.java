package oop.trafficproject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;



public class Appendable extends ObjectOutputStream {


    public Appendable(OutputStream out) throws IOException {
        super(out);
    }


    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }

}
