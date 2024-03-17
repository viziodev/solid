package com.solid;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       Employe emp=new Employe(1,"Baila Wane");
       System.out.println(emp.toJson());
       //emp.saveEmploye();
       //emp.getListEmp();
    }
}
