import java.io.*;
import java.net.*;

import java.lang.Math;

public class Server {

    public Server(int portnum) {
        try {
            server = new ServerSocket(portnum);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void serve() {
        try {
            while (true) {
                Socket client = server.accept();
                BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter w = new PrintWriter(client.getOutputStream(), true);
                w.println("Welcome to the Java EchoServer.  Type 'bye' to close.");
                String line;

                String[][] chatBot = {
                    //standard greetings
                    {"hi", "hello", "hola", "ola", "howdy"},
                    {"hi", "hello", "hey"},
                    //question greetings
                    {"how are you", "how r you", "how r u", "how are u"},
                    {"good", "doing well"},
                    //name
                    {"what's your name", "name", "what are you called", "what is your name"},
                    {"my name is ChatBot 3000"},
                    //java
                    {"java", "What can you tell me", "info", "javainfo"},
                    {"no", "NO", "NO!!!!!!!"},
                    //joke
                    {"joke", "tell me a joke"},
                    {"You are the only joke here..."},
                    //bye
                    {"bye", "goodbye", "later", "see ya", "so long"},
                    {"Goodbye User"},
                    //default
                    {"I'll have to look it up.", "My creator should make me learn this...", "I'm sorry, I am not familliar.", "Excuse me, I don't know.",}
                };

                do {

                    line = r.readLine();
                    line.trim();
                    line.replace("\n", "").replace("\r", "");
                    if (!line.equals("")) {

                        byte response = 0;

                        int j = 0;
                        while (response == 0) {
                            if (inArray(line.toLowerCase(), chatBot[j * 2])) {
                                response = 2;
                                int br = (int) Math.floor(Math.random() * chatBot[(j * 2) + 1].length);
                                w.println(chatBot[(j * 2) + 1][br]);
                            }
                            j++;
                            if (j * 2 == chatBot.length - 1 && response == 0) {
                                response = 1;
                            }
                        }

                        if (response == 1) {
                            int br = (int) Math.floor(Math.random() * chatBot[chatBot.length - 1].length);
                            w.println(chatBot[chatBot.length - 1][br]);
                        }

                    } else {
                        w.println("");
                    }
                } while (!line.trim().equals("bye"));
                client.close();
            }
        } catch (Exception err) {
            System.err.println(err);
        }
    }

    public boolean inArray(String in, String[] str) {
        boolean match = false;
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals(in)) {
                match = true;
            }
        }
        return match;
    }

    public static void main(String[] args) {
        Server s = new Server(9999);
        s.serve();
    }

    private ServerSocket server;
}
