/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.creator;

import command.AddCategoryCommand;
import command.AddDishCommand;
import command.AddGroupCommand;
import command.CategoryDeleteCommand;
import command.ChangeGroupCommand;
import command.ChangeRoleCommand;
import command.DeleteFromMenuCommand;
import command.DishDeleteCommand;
import command.DoRegistrationCommand;
import command.EnterCommand;
import command.GroupDeleteCommand;
import command.MakeMenuCommand;
import command.MasterCommand;
import command.SliderCommand;
import command.UserDeleteCommand;
import interfaces.ActionCommand;

/**
 * В классе задаются и содержутся названия команд, которые используются для перехода к нужному классу-команде в проекте.
 * @author Anton Kovalevskiy
 */
public enum CreatorClassEnum {
    EMPTY{{this.command = new EnterCommand();}},
    ENTER{{this.command = new EnterCommand();}},
    MASTER{{this.command = new MasterCommand();}},
    ADDGROUP{{this.command = new AddGroupCommand();}},
    ADDCATEGORY{{this.command = new AddCategoryCommand();}},
    CHANGEROLE{{this.command = new ChangeRoleCommand();}},
    ADDUSERGROUP{{this.command = new ChangeGroupCommand();}},
    ADDISH{{this.command = new AddDishCommand();}},
    ADDMENU{{this.command = new MakeMenuCommand();}},
    USERDELETE{{this.command = new UserDeleteCommand();}},
    DELETEGROUP{{this.command = new GroupDeleteCommand();}},
    DELETECATEGORY{{this.command = new CategoryDeleteCommand();}},
    DELETEDISH{{this.command = new DishDeleteCommand();}},
    DELETEFROMMENU{{this.command = new DeleteFromMenuCommand();}},
    REGUSER{{this.command = new DoRegistrationCommand();}},
    SLIDER{{this.command = new SliderCommand();}},
    ;


    ActionCommand command;
    public ActionCommand getActionCommand(){
        return this.command;
    }
    
}
