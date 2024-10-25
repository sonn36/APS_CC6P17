package com.example.demo2.model;

import com.example.demo2.controller.MessageController;
import com.example.demo2.statics.Statics;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Serial implements Runnable{

    private MessageController controller;

    public Serial(MessageController controller){
        this.controller = controller;
    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {
        String leitura = "";
        SerialPort comPort = SerialPort.getCommPort("COM4");
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream is = comPort.getInputStream();
        PrintWriter pw = new PrintWriter(comPort.getOutputStream());
        while (Statics.command != 0) {
            try {
                if (leitura.equals("Type: ")){
                    int value = Statics.command;
                    Thread.sleep(1000);
                    if (value != -1) {
                        pw.println(Statics.command);
                        pw.flush();
                        Statics.command = -1;
                        leitura = "";

                    }

                }
                else if (!leitura.isEmpty() && leitura.charAt(leitura.length()-1) == '\n'){
                    System.out.println(leitura);
                    controller.sendMessage(leitura);
                    leitura = "";

                }else {
                    leitura += (char) is.read();
                }



            } catch (IOException | InterruptedException e) {
                Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, e);
            }


        }

        comPort.closePort();
    }
}
