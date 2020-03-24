/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

/**
 *
 * @author ALEX
 */
public class htmlTemplate {
    private String head;
    private String body;
    private String footer;
    private String defineTag;
    public htmlTemplate(){
        this.defineTag = "";
        this.head = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>Page Title</title>\n" +
                    "</head>\n" +
                    "<body>";
        
        this.footer = " \n \n"
                    + "© 2019-2020 HelpDesk.\n</body>\n" + "</html>";
    }
    
    public void difineTag(String tag){
        this.defineTag = this.defineTag + tag + "\n";
    }
    
    public String RenderHTML(){
        return this.head + this.defineTag + this.footer;
    }
    
}
