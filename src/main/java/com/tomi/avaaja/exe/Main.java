/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomi.avaaja.exe;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.Map;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class Main extends NanoHTTPD {
    
    public Main() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Running! http://localhost:8080");
    }
    
    public static void main(String[] args){
        try {
            new Main();
           
        } catch (IOException ioe) {
            System.err.println("Could not start the server! :(" + ioe);
        }
    }
    
    @Override
    public Response serve(IHTTPSession session){
        
        Method method = session.getMethod();
        String uri = session.getUri();
        
        Map<String,String> params = session.getParms();
        
        String header = "<html><body><h1>AVAAJA.EXE</h1>\n";
        
        String content = "";
        
        if(params.get("url")==null){ 
            content = "<form action='?' method='GET'><p>URL TO OPEN<input type='text' name='url'></p></form>";
        } else {
            
            try {
                try {
                    Desktop.getDesktop().browse(new URI(params.get("url")));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                content = "<p>thank you</p>";
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String tail = "</body></html>";
        return newFixedLengthResponse(header + content + tail);
                
    }
}
