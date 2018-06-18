/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import command.creator.CreatorClassEnum;
import interfaces.ActionCommand;

/**
 * 
 * @author Anton Kovalevskiy
 */

public class ActionFactory {
    public ActionCommand defineCommand(String command){
        CreatorClassEnum creatorClassEnum = CreatorClassEnum.valueOf(command.toUpperCase());
        ActionCommand curentCommand = creatorClassEnum.getActionCommand();
        return curentCommand;
    }
}
